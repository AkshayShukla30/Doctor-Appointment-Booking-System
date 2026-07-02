package com.doctorapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.doctorapp.model.PaymentOrderResponse;
import com.doctorapp.model.PaymentVerificationRequest;
import com.doctorapp.service.PaymentService;

@RestController
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/payment/create-order/{appointmentId}")
	public ResponseEntity<PaymentOrderResponse> createOrderHandler(@PathVariable Integer appointmentId) throws Exception {
		return new ResponseEntity<>(paymentService.createPaymentOrder(appointmentId), HttpStatus.CREATED);
	}

	@PostMapping("/payment/verify")
	public ResponseEntity<Boolean> verifyPaymentHandler(@RequestBody PaymentVerificationRequest request) {
		return new ResponseEntity<>(paymentService.verifyAndUpdatePayment(request), HttpStatus.OK);
	}
}
