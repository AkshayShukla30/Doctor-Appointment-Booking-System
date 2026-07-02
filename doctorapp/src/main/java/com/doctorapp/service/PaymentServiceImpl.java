package com.doctorapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.doctorapp.exception.PaymentException;
import com.doctorapp.exception.ResourceNotFoundException;
import com.doctorapp.model.Appointment;
import com.doctorapp.model.PaymentOrderResponse;
import com.doctorapp.model.PaymentStatus;
import com.doctorapp.model.PaymentVerificationRequest;
import com.doctorapp.repository.AppointmentRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

/**
 * Razorpay integration for consultation fee payments.
 * Set razorpay.key.id / razorpay.key.secret (env vars RAZORPAY_KEY_ID / RAZORPAY_KEY_SECRET)
 * with real keys from https://dashboard.razorpay.com/app/keys before going live.
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public PaymentOrderResponse createPaymentOrder(Integer appointmentId) throws Exception {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new ResourceNotFoundException("No appointment exists with id " + appointmentId));

		try {
			RazorpayClient client = new RazorpayClient(keyId, keySecret);

			org.json.JSONObject orderRequest = new org.json.JSONObject();
			orderRequest.put("amount", Math.round(appointment.getConsultationFee() * 100));
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "appointment_" + appointmentId);

			com.razorpay.Order razorpayOrder = client.orders.create(orderRequest);

			appointment.setRazorpayOrderId(razorpayOrder.get("id"));
			appointmentRepository.save(appointment);

			return new PaymentOrderResponse(razorpayOrder.get("id"), appointment.getConsultationFee(), "INR", keyId);
		} catch (Exception e) {
			throw new PaymentException("Unable to create payment order: " + e.getMessage());
		}
	}

	@Override
	public boolean verifyAndUpdatePayment(PaymentVerificationRequest request) {
		try {
			org.json.JSONObject options = new org.json.JSONObject();
			options.put("razorpay_order_id", request.getRazorpayOrderId());
			options.put("razorpay_payment_id", request.getRazorpayPaymentId());
			options.put("razorpay_signature", request.getRazorpaySignature());

			boolean isValid = Utils.verifyPaymentSignature(options, keySecret);

			Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
					.orElseThrow(() -> new ResourceNotFoundException("No appointment exists with id " + request.getAppointmentId()));

			if (isValid) {
				appointment.setRazorpayPaymentId(request.getRazorpayPaymentId());
				appointment.setPaymentStatus(PaymentStatus.PAID);
			} else {
				appointment.setPaymentStatus(PaymentStatus.FAILED);
			}
			appointmentRepository.save(appointment);

			return isValid;
		} catch (Exception e) {
			throw new PaymentException("Payment verification failed: " + e.getMessage());
		}
	}
}
