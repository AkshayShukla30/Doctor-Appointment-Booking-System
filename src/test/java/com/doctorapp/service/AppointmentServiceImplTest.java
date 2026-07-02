package com.doctorapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.doctorapp.exception.BadRequestException;
import com.doctorapp.model.BookAppointmentRequest;
import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.DoctorSlot;
import com.doctorapp.model.User;
import com.doctorapp.repository.AppointmentRepository;
import com.doctorapp.repository.DoctorSlotRepository;
import com.doctorapp.security.CurrentUserProvider;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

	@Mock
	private AppointmentRepository appointmentRepository;

	@Mock
	private DoctorSlotRepository doctorSlotRepository;

	@Mock
	private CurrentUserProvider currentUserProvider;

	@Mock
	private EmailService emailService;

	@InjectMocks
	private AppointmentServiceImpl appointmentService;

	@BeforeEach
	void setUp() {
		User patient = new User();
		patient.setId(1);
		when(currentUserProvider.getCurrentUser()).thenReturn(patient);
	}

	// Verifies the Inventory-Locking safeguard: a slot that is already booked cannot be booked again
	@Test
	void bookingAnAlreadyBookedSlotThrowsBadRequest() {
		DoctorSlot slot = new DoctorSlot();
		slot.setId(10);
		slot.setBooked(true);
		slot.setDoctor(new DoctorProfile());

		when(doctorSlotRepository.findById(10)).thenReturn(Optional.of(slot));

		BookAppointmentRequest request = new BookAppointmentRequest(10, "fever");

		assertThrows(BadRequestException.class, () -> appointmentService.bookAppointment(request));
	}
}
