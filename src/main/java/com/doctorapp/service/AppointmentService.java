package com.doctorapp.service;

import java.util.List;

import com.doctorapp.model.Appointment;
import com.doctorapp.model.BookAppointmentRequest;

public interface AppointmentService {

	Appointment bookAppointment(BookAppointmentRequest request);

	List<Appointment> getMyAppointmentsAsPatient();

	List<Appointment> getMyAppointmentsAsDoctor();

	Appointment cancelAppointment(Integer appointmentId);

	Appointment markCompleted(Integer appointmentId);
}
