package com.doctorapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doctorapp.model.DoctorProfile;
import com.doctorapp.service.RecommendationService;

@RestController
public class RecommendationController {

	@Autowired
	private RecommendationService recommendationService;

	// e.g. GET /recommend/doctors?symptoms=I have chest pain and breathlessness
	@GetMapping("/recommend/doctors")
	public ResponseEntity<List<DoctorProfile>> recommendHandler(@RequestParam("symptoms") String symptoms) {
		return new ResponseEntity<>(recommendationService.recommendDoctorsForSymptoms(symptoms), HttpStatus.OK);
	}
}
