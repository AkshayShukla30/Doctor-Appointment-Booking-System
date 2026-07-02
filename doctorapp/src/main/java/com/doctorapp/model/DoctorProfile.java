package com.doctorapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctor_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
	private User user;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Specialization specialization;

	private String qualification;

	private Integer experienceYears;

	@NotNull
	private Double consultationFee;

	@Column(length = 1000)
	private String about;

	private Double avgRating = 0.0;

	private Integer totalReviews = 0;

	private boolean verified = false;
}
