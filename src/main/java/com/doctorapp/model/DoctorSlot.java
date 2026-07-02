package com.doctorapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Inventory-Locking-style concurrency control, applied to appointment slots:
// @Version prevents two patients from double-booking the same slot under concurrent requests.
@Entity
@Table(name = "doctor_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	@JsonIgnoreProperties({"user", "about"})
	private DoctorProfile doctor;

	private LocalDate slotDate;

	private LocalTime startTime;

	private LocalTime endTime;

	private boolean booked = false;

	@Version
	private Integer version;
}
