package com.doctorapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.doctorapp.model.Appointment;
import com.doctorapp.model.User;

/**
 * Sends transactional emails. Failures are logged, not thrown, so that a
 * misconfigured/unreachable SMTP server never breaks booking or registration.
 */
@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username:noreply@doctorapp.com}")
	private String fromAddress;

	private void send(String to, String subject, String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromAddress);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			mailSender.send(message);
		} catch (Exception e) {
			logger.error("Failed to send email to {}: {}", to, e.getMessage());
		}
	}

	@Override
	public void sendWelcomeEmail(User user) {
		send(user.getEmail(), "Welcome to Doctor Appointment System",
				"Hi " + user.getName() + ",\n\nYour account has been created successfully as a "
						+ user.getRole() + ".\n\nRegards,\nDoctor Appointment Team");
	}

	@Override
	public void sendAppointmentConfirmation(Appointment appointment) {
		send(appointment.getPatient().getEmail(), "Appointment Confirmed - #" + appointment.getId(),
				"Hi " + appointment.getPatient().getName() + ",\n\n"
						+ "Your appointment with Dr. " + appointment.getDoctor().getUser().getName()
						+ " on " + appointment.getSlot().getSlotDate() + " at " + appointment.getSlot().getStartTime()
						+ " is confirmed.\n\nConsultation Fee: " + appointment.getConsultationFee()
						+ "\n\nRegards,\nDoctor Appointment Team");
	}

	@Override
	public void sendAppointmentCancellation(Appointment appointment) {
		send(appointment.getPatient().getEmail(), "Appointment Cancelled - #" + appointment.getId(),
				"Hi " + appointment.getPatient().getName() + ",\n\n"
						+ "Your appointment #" + appointment.getId() + " has been cancelled.\n\n"
						+ "Regards,\nDoctor Appointment Team");
	}
}
