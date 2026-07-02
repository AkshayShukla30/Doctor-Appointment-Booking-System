package com.doctorapp.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAppointmentRequest {

	@NotNull
	private Integer slotId;

	// Free-text symptoms; also used by the AI recommendation feature
	private String symptoms;
}
