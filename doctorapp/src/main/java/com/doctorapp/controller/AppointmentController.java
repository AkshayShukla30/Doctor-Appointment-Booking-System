package com.doctorapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorapp.model.Appointment;
import com.doctorapp.model.BookAppointmentRequest;
import com.doctorapp.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@PostMapping("/appointments/book")
	public ResponseEntity<Appointment> bookAppointmentHandler(@Valid @RequestBody BookAppointmentRequest request) {
		return new ResponseEntity<>(appointmentService.bookAppointment(request), HttpStatus.CREATED);
	}

	@GetMapping("/appointments/mine")
	public ResponseEntity<List<Appointment>> getMyAppointmentsAsPatientHandler() {
		return new ResponseEntity<>(appointmentService.getMyAppointmentsAsPatient(), HttpStatus.OK);
	}

	@GetMapping("/doctor/appointments")
	public ResponseEntity<List<Appointment>> getMyAppointmentsAsDoctorHandler() {
		return new ResponseEntity<>(appointmentService.getMyAppointmentsAsDoctor(), HttpStatus.OK);
	}

	@PutMapping("/appointments/{appointmentId}/cancel")
	public ResponseEntity<Appointment> cancelAppointmentHandler(@PathVariable Integer appointmentId) {
		return new ResponseEntity<>(appointmentService.cancelAppointment(appointmentId), HttpStatus.OK);
	}

	@PutMapping("/doctor/appointments/{appointmentId}/complete")
	public ResponseEntity<Appointment> markCompletedHandler(@PathVariable Integer appointmentId) {
		return new ResponseEntity<>(appointmentService.markCompleted(appointmentId), HttpStatus.OK);
	}
}
