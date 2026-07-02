package com.doctorapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	@NotBlank
	private String name;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String password;

	private String phone;

	@NotNull
	private Role role;

	// Only required when role == DOCTOR
	private Specialization specialization;
	private String qualification;
	private Integer experienceYears;
	private Double consultationFee;
	private String about;
}
