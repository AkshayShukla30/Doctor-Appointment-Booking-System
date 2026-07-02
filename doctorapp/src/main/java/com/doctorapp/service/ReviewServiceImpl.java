package com.doctorapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapp.exception.BadRequestException;
import com.doctorapp.exception.ResourceNotFoundException;
import com.doctorapp.exception.UnauthorizedException;
import com.doctorapp.model.Appointment;
import com.doctorapp.model.AppointmentStatus;
import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.Review;
import com.doctorapp.model.ReviewRequest;
import com.doctorapp.model.User;
import com.doctorapp.repository.AppointmentRepository;
import com.doctorapp.repository.DoctorProfileRepository;
import com.doctorapp.repository.ReviewRepository;
import com.doctorapp.security.CurrentUserProvider;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorProfileRepository doctorProfileRepository;

	@Autowired
	private CurrentUserProvider currentUserProvider;

	@Override
	public Review addReview(ReviewRequest request) {
		User patient = currentUserProvider.getCurrentUser();

		Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
				.orElseThrow(() -> new ResourceNotFoundException("No appointment exists with id " + request.getAppointmentId()));

		if (!appointment.getPatient().getId().equals(patient.getId())) {
			throw new UnauthorizedException("You can only review your own appointments");
		}
		if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
			throw new BadRequestException("You can only review a completed appointment");
		}
		if (reviewRepository.findByAppointment_Id(appointment.getId()).isPresent()) {
			throw new BadRequestException("You have already reviewed this appointment");
		}

		Review review = new Review();
		review.setAppointment(appointment);
		review.setPatient(patient);
		review.setDoctor(appointment.getDoctor());
		review.setRating(request.getRating());
		review.setComment(request.getComment());
		review.setCreatedOn(LocalDateTime.now());

		review = reviewRepository.save(review);

		// Recalculate the doctor's running average rating
		DoctorProfile doctor = appointment.getDoctor();
		List<Review> allReviews = reviewRepository.findByDoctor_Id(doctor.getId());
		double avg = allReviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
		doctor.setAvgRating(Math.round(avg * 10.0) / 10.0);
		doctor.setTotalReviews(allReviews.size());
		doctorProfileRepository.save(doctor);

		return review;
	}

	@Override
	public List<Review> getReviewsForDoctor(Integer doctorId) {
		return reviewRepository.findByDoctor_Id(doctorId);
	}
}
