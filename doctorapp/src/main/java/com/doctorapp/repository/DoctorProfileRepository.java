package com.doctorapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.Specialization;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Integer> {

	Optional<DoctorProfile> findByUser_Id(Integer userId);

	List<DoctorProfile> findBySpecialization(Specialization specialization);
}
