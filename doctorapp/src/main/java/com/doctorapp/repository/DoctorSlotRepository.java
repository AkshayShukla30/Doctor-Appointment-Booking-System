package com.doctorapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorapp.model.DoctorSlot;

public interface DoctorSlotRepository extends JpaRepository<DoctorSlot, Integer> {

	List<DoctorSlot> findByDoctor_IdAndBookedFalseAndSlotDateGreaterThanEqual(Integer doctorId, LocalDate fromDate);
}
