package com.doctorapp.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.DoctorSlot;
import com.doctorapp.model.SlotRequest;
import com.doctorapp.model.Specialization;
import com.doctorapp.service.DoctorService;

import jakarta.validation.Valid;

@RestController
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@GetMapping("/doctors")
	public ResponseEntity<List<DoctorProfile>> getAllDoctorsHandler() {
		return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
	}

	@GetMapping("/doctors/specialization/{specialization}")
	public ResponseEntity<List<DoctorProfile>> getBySpecializationHandler(@PathVariable Specialization specialization) {
		return new ResponseEntity<>(doctorService.getDoctorsBySpecialization(specialization), HttpStatus.OK);
	}

	@GetMapping("/doctors/{doctorId}")
	public ResponseEntity<DoctorProfile> getDoctorByIdHandler(@PathVariable Integer doctorId) {
		return new ResponseEntity<>(doctorService.getDoctorById(doctorId), HttpStatus.OK);
	}

	@GetMapping("/doctors/{doctorId}/slots")
	public ResponseEntity<List<DoctorSlot>> getAvailableSlotsHandler(@PathVariable Integer doctorId) {
		return new ResponseEntity<>(doctorService.getAvailableSlots(doctorId), HttpStatus.OK);
	}

	// Doctor-only: publish a new available slot
	@PostMapping("/doctor/slots")
	public ResponseEntity<DoctorSlot> addSlotHandler(@Valid @RequestBody SlotRequest request) {
		DoctorSlot slot = doctorService.addSlot(request.getSlotDate(), request.getStartTime(), request.getEndTime());
		return new ResponseEntity<>(slot, HttpStatus.CREATED);
	}

	@GetMapping("/doctor/slots/mine")
	public ResponseEntity<List<DoctorSlot>> getMySlotsHandler() {
		return new ResponseEntity<>(doctorService.getMySlots(), HttpStatus.OK);
	}
}
