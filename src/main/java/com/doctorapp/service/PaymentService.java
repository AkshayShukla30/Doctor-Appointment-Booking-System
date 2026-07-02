package com.doctorapp.service;

import com.doctorapp.model.PaymentOrderResponse;
import com.doctorapp.model.PaymentVerificationRequest;

public interface PaymentService {

	PaymentOrderResponse createPaymentOrder(Integer appointmentId) throws Exception;

	boolean verifyAndUpdatePayment(PaymentVerificationRequest request);
}
