package com.doctorapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorapp.model.Role;
import com.doctorapp.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	long countByRole(Role role);
}
