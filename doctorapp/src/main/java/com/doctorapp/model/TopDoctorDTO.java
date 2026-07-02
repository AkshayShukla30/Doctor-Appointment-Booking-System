package com.doctorapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopDoctorDTO {

	private Integer doctorId;
	private String doctorName;
	private Long totalAppointments;
	private Double avgRating;
}
