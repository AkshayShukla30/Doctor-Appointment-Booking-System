package com.doctorapp.service;

import java.util.List;

import com.doctorapp.model.Review;
import com.doctorapp.model.ReviewRequest;

public interface ReviewService {

	Review addReview(ReviewRequest request);

	List<Review> getReviewsForDoctor(Integer doctorId);
}
