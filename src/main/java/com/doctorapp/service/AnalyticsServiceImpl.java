package com.doctorapp.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapp.model.Appointment;
import com.doctorapp.model.AppointmentStatus;
import com.doctorapp.model.DashboardStatsDTO;
import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.PaymentStatus;
import com.doctorapp.model.Role;
import com.doctorapp.model.TopDoctorDTO;
import com.doctorapp.repository.AppointmentRepository;
import com.doctorapp.repository.DoctorProfileRepository;
import com.doctorapp.repository.UserRepository;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorProfileRepository doctorProfileRepository;

	@Override
	public DashboardStatsDTO getDashboardStats() {
		List<Appointment> allAppointments = appointmentRepository.findAll();

		Double totalRevenue = allAppointments.stream()
				.filter(a -> a.getPaymentStatus() == PaymentStatus.PAID)
				.mapToDouble(a -> a.getConsultationFee() != null ? a.getConsultationFee() : 0.0)
				.sum();

		Map<String, Long> byStatus = allAppointments.stream()
				.collect(Collectors.groupingBy(a -> a.getStatus().name(), Collectors.counting()));

		Map<Integer, Long> countByDoctorId = allAppointments.stream()
				.collect(Collectors.groupingBy(a -> a.getDoctor().getId(), Collectors.counting()));

		List<TopDoctorDTO> topDoctors = countByDoctorId.entrySet().stream()
				.sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
				.limit(5)
				.map(e -> {
					DoctorProfile d = doctorProfileRepository.findById(e.getKey()).orElse(null);
					String name = d != null ? d.getUser().getName() : "Unknown";
					Double rating = d != null ? d.getAvgRating() : 0.0;
					return new TopDoctorDTO(e.getKey(), name, e.getValue(), rating);
				})
				.collect(Collectors.toList());

		DashboardStatsDTO stats = new DashboardStatsDTO();
		stats.setTotalPatients(userRepository.countByRole(Role.PATIENT));
		stats.setTotalDoctors(userRepository.countByRole(Role.DOCTOR));
		stats.setTotalAppointments((long) allAppointments.size());
		stats.setTotalRevenue(totalRevenue);
		stats.setAppointmentsByStatus(byStatus);
		stats.setTopDoctors(topDoctors);

		return stats;
	}
}
