package com.doctorapp.service;

import java.time.LocalDate;
import java.util.List;

import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.DoctorSlot;
import com.doctorapp.model.Specialization;

public interface DoctorService {

	List<DoctorProfile> getAllDoctors();

	List<DoctorProfile> getDoctorsBySpecialization(Specialization specialization);

	DoctorProfile getDoctorById(Integer doctorId);

	DoctorSlot addSlot(LocalDate slotDate, java.time.LocalTime startTime, java.time.LocalTime endTime);

	List<DoctorSlot> getAvailableSlots(Integer doctorId);

	List<DoctorSlot> getMySlots();
}
