package com.doctorapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorapp.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	List<Appointment> findByPatient_Id(Integer patientId);

	List<Appointment> findByDoctor_Id(Integer doctorId);
}
