package com.doctorapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doctorapp.exception.BadRequestException;
import com.doctorapp.exception.ResourceNotFoundException;
import com.doctorapp.exception.UnauthorizedException;
import com.doctorapp.model.Appointment;
import com.doctorapp.model.AppointmentStatus;
import com.doctorapp.model.BookAppointmentRequest;
import com.doctorapp.model.DoctorSlot;
import com.doctorapp.model.User;
import com.doctorapp.repository.AppointmentRepository;
import com.doctorapp.repository.DoctorSlotRepository;
import com.doctorapp.security.CurrentUserProvider;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorSlotRepository doctorSlotRepository;

	@Autowired
	private CurrentUserProvider currentUserProvider;

	@Autowired
	private EmailService emailService;

	/**
	 * Inventory-Locking pattern applied to appointment slots: the DoctorSlot has a
	 * @Version column, so if two patients race to book the same slot, the second
	 * save() here throws ObjectOptimisticLockingFailureException instead of silently
	 * double-booking - handled centrally by GlobalExceptionHandler as a 409 Conflict.
	 */
	@Override
	@Transactional
	public Appointment bookAppointment(BookAppointmentRequest request) {
		User patient = currentUserProvider.getCurrentUser();

		DoctorSlot slot = doctorSlotRepository.findById(request.getSlotId())
				.orElseThrow(() -> new ResourceNotFoundException("No slot exists with id " + request.getSlotId()));

		if (slot.isBooked()) {
			throw new BadRequestException("This slot is already booked, please choose another one");
		}

		slot.setBooked(true);
		doctorSlotRepository.save(slot);

		Appointment appointment = new Appointment();
		appointment.setPatient(patient);
		appointment.setDoctor(slot.getDoctor());
		appointment.setSlot(slot);
		appointment.setSymptoms(request.getSymptoms());
		appointment.setStatus(AppointmentStatus.PENDING);
		appointment.setConsultationFee(slot.getDoctor().getConsultationFee());
		appointment.setCreatedOn(LocalDateTime.now());

		appointment = appointmentRepository.save(appointment);

		emailService.sendAppointmentConfirmation(appointment);

		return appointment;
	}

	@Override
	public List<Appointment> getMyAppointmentsAsPatient() {
		User patient = currentUserProvider.getCurrentUser();
		return appointmentRepository.findByPatient_Id(patient.getId());
	}

	@Autowired
	private com.doctorapp.repository.DoctorProfileRepository doctorProfileRepository;

	@Override
	public List<Appointment> getMyAppointmentsAsDoctor() {
		User doctorUser = currentUserProvider.getCurrentUser();
		com.doctorapp.model.DoctorProfile profile = doctorProfileRepository.findByUser_Id(doctorUser.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Doctor profile not found for this account"));
		return appointmentRepository.findByDoctor_Id(profile.getId());
	}

	@Override
	@Transactional
	public Appointment cancelAppointment(Integer appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new ResourceNotFoundException("No appointment exists with id " + appointmentId));

		User currentUser = currentUserProvider.getCurrentUser();
		boolean isOwner = appointment.getPatient().getId().equals(currentUser.getId());
		if (!isOwner) {
			throw new UnauthorizedException("You can only cancel your own appointments");
		}

		if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
			throw new BadRequestException("Appointment is already cancelled");
		}

		appointment.setStatus(AppointmentStatus.CANCELLED);
		DoctorSlot slot = appointment.getSlot();
		slot.setBooked(false);
		doctorSlotRepository.save(slot);

		appointment = appointmentRepository.save(appointment);
		emailService.sendAppointmentCancellation(appointment);

		return appointment;
	}

	@Override
	public Appointment markCompleted(Integer appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new ResourceNotFoundException("No appointment exists with id " + appointmentId));
		appointment.setStatus(AppointmentStatus.COMPLETED);
		return appointmentRepository.save(appointment);
	}
}
