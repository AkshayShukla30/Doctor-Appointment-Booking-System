package com.doctorapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorapp.model.Review;
import com.doctorapp.model.ReviewRequest;
import com.doctorapp.service.ReviewService;

import jakarta.validation.Valid;

@RestController
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@PostMapping("/reviews")
	public ResponseEntity<Review> addReviewHandler(@Valid @RequestBody ReviewRequest request) {
		return new ResponseEntity<>(reviewService.addReview(request), HttpStatus.CREATED);
	}

	@GetMapping("/doctors/{doctorId}/reviews")
	public ResponseEntity<List<Review>> getReviewsHandler(@PathVariable Integer doctorId) {
		return new ResponseEntity<>(reviewService.getReviewsForDoctor(doctorId), HttpStatus.OK);
	}
}
