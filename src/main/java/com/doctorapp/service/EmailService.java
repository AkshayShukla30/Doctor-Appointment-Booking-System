package com.doctorapp.service;

import com.doctorapp.model.Appointment;
import com.doctorapp.model.User;

public interface EmailService {

	void sendWelcomeEmail(User user);

	void sendAppointmentConfirmation(Appointment appointment);

	void sendAppointmentCancellation(Appointment appointment);
}
