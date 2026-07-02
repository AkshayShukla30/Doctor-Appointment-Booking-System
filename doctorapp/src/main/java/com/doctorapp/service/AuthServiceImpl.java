package com.doctorapp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doctorapp.exception.BadRequestException;
import com.doctorapp.model.AuthResponse;
import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.LoginRequest;
import com.doctorapp.model.RegisterRequest;
import com.doctorapp.model.Role;
import com.doctorapp.model.User;
import com.doctorapp.repository.DoctorProfileRepository;
import com.doctorapp.repository.UserRepository;
import com.doctorapp.security.JwtUtil;
import com.doctorapp.security.UserPrincipal;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DoctorProfileRepository doctorProfileRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EmailService emailService;

	@Override
	public AuthResponse register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new BadRequestException("An account already exists with this email");
		}

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPhone(request.getPhone());
		user.setRole(request.getRole());
		user.setCreatedOn(LocalDateTime.now());
		user.setEnabled(true);

		user = userRepository.save(user);

		if (request.getRole() == Role.DOCTOR) {
			if (request.getSpecialization() == null || request.getConsultationFee() == null) {
				throw new BadRequestException("specialization and consultationFee are required for doctor registration");
			}
			DoctorProfile profile = new DoctorProfile();
			profile.setUser(user);
			profile.setSpecialization(request.getSpecialization());
			profile.setQualification(request.getQualification());
			profile.setExperienceYears(request.getExperienceYears());
			profile.setConsultationFee(request.getConsultationFee());
			profile.setAbout(request.getAbout());
			doctorProfileRepository.save(profile);
		}

		emailService.sendWelcomeEmail(user);

		String token = jwtUtil.generateToken(new UserPrincipal(user));
		return new AuthResponse(token, user.getId(), user.getName(), user.getRole());
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new BadRequestException("Invalid email or password"));

		String token = jwtUtil.generateToken(new UserPrincipal(user));
		return new AuthResponse(token, user.getId(), user.getName(), user.getRole());
	}
}
