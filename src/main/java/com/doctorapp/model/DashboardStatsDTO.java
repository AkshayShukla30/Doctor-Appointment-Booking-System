package com.doctorapp.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {

	private Long totalPatients;
	private Long totalDoctors;
	private Long totalAppointments;
	private Double totalRevenue;
	private Map<String, Long> appointmentsByStatus;
	private List<TopDoctorDTO> topDoctors;
}
