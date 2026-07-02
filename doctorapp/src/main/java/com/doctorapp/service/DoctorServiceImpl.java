package com.doctorapp.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapp.exception.BadRequestException;
import com.doctorapp.exception.ResourceNotFoundException;
import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.DoctorSlot;
import com.doctorapp.model.Specialization;
import com.doctorapp.model.User;
import com.doctorapp.repository.DoctorProfileRepository;
import com.doctorapp.repository.DoctorSlotRepository;
import com.doctorapp.security.CurrentUserProvider;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private DoctorProfileRepository doctorProfileRepository;

	@Autowired
	private DoctorSlotRepository doctorSlotRepository;

	@Autowired
	private CurrentUserProvider currentUserProvider;

	@Override
	public List<DoctorProfile> getAllDoctors() {
		return doctorProfileRepository.findAll();
	}

	@Override
	public List<DoctorProfile> getDoctorsBySpecialization(Specialization specialization) {
		return doctorProfileRepository.findBySpecialization(specialization);
	}

	@Override
	public DoctorProfile getDoctorById(Integer doctorId) {
		return doctorProfileRepository.findById(doctorId)
				.orElseThrow(() -> new ResourceNotFoundException("No doctor exists with id " + doctorId));
	}

	private DoctorProfile getLoggedInDoctorProfile() {
		User user = currentUserProvider.getCurrentUser();
		return doctorProfileRepository.findByUser_Id(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Doctor profile not found for this account"));
	}

	@Override
	public DoctorSlot addSlot(LocalDate slotDate, LocalTime startTime, LocalTime endTime) {
		if (!endTime.isAfter(startTime)) {
			throw new BadRequestException("endTime must be after startTime");
		}
		DoctorProfile doctor = getLoggedInDoctorProfile();

		DoctorSlot slot = new DoctorSlot();
		slot.setDoctor(doctor);
		slot.setSlotDate(slotDate);
		slot.setStartTime(startTime);
		slot.setEndTime(endTime);
		slot.setBooked(false);

		return doctorSlotRepository.save(slot);
	}

	@Override
	public List<DoctorSlot> getAvailableSlots(Integer doctorId) {
		return doctorSlotRepository.findByDoctor_IdAndBookedFalseAndSlotDateGreaterThanEqual(doctorId, LocalDate.now());
	}

	@Override
	public List<DoctorSlot> getMySlots() {
		DoctorProfile doctor = getLoggedInDoctorProfile();
		return doctorSlotRepository.findByDoctor_IdAndBookedFalseAndSlotDateGreaterThanEqual(doctor.getId(), LocalDate.now());
	}
}
