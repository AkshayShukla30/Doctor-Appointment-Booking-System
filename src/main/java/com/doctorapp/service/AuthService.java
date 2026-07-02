package com.doctorapp.service;

import com.doctorapp.model.AuthResponse;
import com.doctorapp.model.LoginRequest;
import com.doctorapp.model.RegisterRequest;

public interface AuthService {

	AuthResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);
}
