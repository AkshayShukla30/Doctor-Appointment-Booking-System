package com.doctorapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorapp.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByDoctor_Id(Integer doctorId);

	Optional<Review> findByAppointment_Id(Integer appointmentId);
}
