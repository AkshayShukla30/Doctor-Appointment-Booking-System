package com.doctorapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotRequest {

	@NotNull
	private LocalDate slotDate;

	@NotNull
	private LocalTime startTime;

	@NotNull
	private LocalTime endTime;
}
