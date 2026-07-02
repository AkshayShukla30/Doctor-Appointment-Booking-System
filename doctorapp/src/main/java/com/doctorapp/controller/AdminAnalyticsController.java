package com.doctorapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doctorapp.model.DashboardStatsDTO;
import com.doctorapp.service.AnalyticsService;

@RestController
public class AdminAnalyticsController {

	@Autowired
	private AnalyticsService analyticsService;

	@GetMapping("/admin/dashboard")
	public ResponseEntity<DashboardStatsDTO> getDashboardHandler() {
		return new ResponseEntity<>(analyticsService.getDashboardStats(), HttpStatus.OK);
	}
}
