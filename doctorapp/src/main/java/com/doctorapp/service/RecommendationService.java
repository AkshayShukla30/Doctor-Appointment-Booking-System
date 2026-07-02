package com.doctorapp.service;

import java.util.List;

import com.doctorapp.model.DoctorProfile;

public interface RecommendationService {

	// Given free-text symptoms, recommend the most relevant specialization and top-rated doctors in it
	List<DoctorProfile> recommendDoctorsForSymptoms(String symptomsText);
}
