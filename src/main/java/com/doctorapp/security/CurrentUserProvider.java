package com.doctorapp.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.doctorapp.model.User;

// Small helper to pull the logged-in User entity out of the Spring Security context
// wherever a service needs "who is calling this endpoint right now".
@Component
public class CurrentUserProvider {

	public User getCurrentUser() {
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal.getUser();
	}
}
