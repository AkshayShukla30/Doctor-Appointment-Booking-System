package com.doctorapp.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "appointment_id", unique = true)
	private Appointment appointment;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	@JsonIgnoreProperties({"password"})
	private User patient;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private DoctorProfile doctor;

	@Min(1)
	@Max(5)
	private Integer rating;

	@Column(length = 1000)
	private String comment;

	private LocalDateTime createdOn;
}
