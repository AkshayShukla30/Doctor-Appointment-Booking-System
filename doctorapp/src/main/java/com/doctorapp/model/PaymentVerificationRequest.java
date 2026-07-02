package com.doctorapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVerificationRequest {

	private Integer appointmentId;
	private String razorpayOrderId;
	private String razorpayPaymentId;
	private String razorpaySignature;
}
