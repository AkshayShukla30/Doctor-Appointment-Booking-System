package com.doctorapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.doctorapp.model.AuthResponse;
import com.doctorapp.model.LoginRequest;
import com.doctorapp.model.RegisterRequest;
import com.doctorapp.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/auth/register")
	public ResponseEntity<AuthResponse> registerHandler(@Valid @RequestBody RegisterRequest request) {
		return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
	}

	@PostMapping("/auth/login")
	public ResponseEntity<AuthResponse> loginHandler(@Valid @RequestBody LoginRequest request) {
		return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
	}
}
