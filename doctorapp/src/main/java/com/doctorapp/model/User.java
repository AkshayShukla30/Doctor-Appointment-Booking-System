package com.doctorapp.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Enter a valid email address")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "Password is required")
	@JsonIgnore
	private String password;

	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Enter a valid 10 digit mobile number")
	@Column(unique = true)
	private String phone;

	@Enumerated(EnumType.STRING)
	private Role role;

	private LocalDateTime createdOn;

	private boolean enabled = true;
}
