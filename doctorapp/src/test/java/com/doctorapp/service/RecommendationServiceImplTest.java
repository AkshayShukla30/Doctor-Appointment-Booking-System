package com.doctorapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.Specialization;
import com.doctorapp.repository.DoctorProfileRepository;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

	@Mock
	private DoctorProfileRepository doctorProfileRepository;

	@InjectMocks
	private RecommendationServiceImpl recommendationService;

	private DoctorProfile cardiologist;

	@BeforeEach
	void setUp() {
		cardiologist = new DoctorProfile();
		cardiologist.setId(1);
		cardiologist.setSpecialization(Specialization.CARDIOLOGY);
		cardiologist.setAvgRating(4.5);
	}

	@Test
	void recommendsCardiologyForChestPainSymptoms() {
		when(doctorProfileRepository.findBySpecialization(Specialization.CARDIOLOGY))
				.thenReturn(List.of(cardiologist));

		List<DoctorProfile> result = recommendationService.recommendDoctorsForSymptoms("I have severe chest pain and palpitation");

		assertEquals(1, result.size());
		assertEquals(Specialization.CARDIOLOGY, result.get(0).getSpecialization());
	}

	@Test
	void fallsBackToGeneralPhysicianWhenNoKeywordMatches() {
		when(doctorProfileRepository.findBySpecialization(Specialization.GENERAL_PHYSICIAN))
				.thenReturn(List.of());

		List<DoctorProfile> result = recommendationService.recommendDoctorsForSymptoms("just feeling a bit off today");

		assertEquals(0, result.size());
	}
}
