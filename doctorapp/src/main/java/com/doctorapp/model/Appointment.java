package com.doctorapp.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	@JsonIgnoreProperties({"password"})
	private User patient;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private DoctorProfile doctor;

	@OneToOne
	@JoinColumn(name = "slot_id")
	private DoctorSlot slot;

	@Column(length = 1000)
	private String symptoms;

	@Enumerated(EnumType.STRING)
	private AppointmentStatus status = AppointmentStatus.PENDING;

	private Double consultationFee;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

	private String razorpayOrderId;

	private String razorpayPaymentId;

	private LocalDateTime createdOn;
}
