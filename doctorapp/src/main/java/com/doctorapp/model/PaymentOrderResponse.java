package com.doctorapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderResponse {

	private String razorpayOrderId;
	private Double amount;
	private String currency;
	private String razorpayKeyId;
}
