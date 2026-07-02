package com.doctorapp.service;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doctorapp.model.DoctorProfile;
import com.doctorapp.model.Specialization;
import com.doctorapp.repository.DoctorProfileRepository;

/**
 * AI Doctor Recommendation.
 *
 * Ships as a transparent, explainable keyword-matching rule engine rather than a
 * black-box ML model, so it works out of the box with zero training data or GPU
 * requirements, while still solving the real problem: "which specialist should I see?"
 *
 * Each Specialization is mapped to a set of symptom keywords. The patient's free-text
 * symptoms are scored against every specialization, the best match is picked, and
 * doctors within that specialization are returned ranked by rating.
 *
 * This can later be swapped for an embeddings/LLM-based classifier behind the same
 * RecommendationService interface without touching the controller.
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {

	@Autowired
	private DoctorProfileRepository doctorProfileRepository;

	private static final Map<Specialization, List<String>> SYMPTOM_KEYWORDS = new EnumMap<>(Specialization.class);

	static {
		SYMPTOM_KEYWORDS.put(Specialization.CARDIOLOGY, List.of(
				"chest pain", "heart", "palpitation", "breathless", "high blood pressure", "cholesterol"));
		SYMPTOM_KEYWORDS.put(Specialization.DERMATOLOGY, List.of(
				"skin", "rash", "acne", "itching", "eczema", "hair fall", "allergy"));
		SYMPTOM_KEYWORDS.put(Specialization.NEUROLOGY, List.of(
				"headache", "migraine", "seizure", "numbness", "memory loss", "dizziness", "tremor"));
		SYMPTOM_KEYWORDS.put(Specialization.ORTHOPEDIC, List.of(
				"joint pain", "back pain", "fracture", "bone", "knee pain", "sprain", "arthritis"));
		SYMPTOM_KEYWORDS.put(Specialization.PEDIATRICS, List.of(
				"child", "infant", "baby", "vaccination", "growth"));
		SYMPTOM_KEYWORDS.put(Specialization.DENTIST, List.of(
				"tooth", "teeth", "gum", "cavity", "toothache"));
		SYMPTOM_KEYWORDS.put(Specialization.ENT, List.of(
				"ear pain", "throat", "sinus", "hearing loss", "nose bleed", "tonsil"));
		SYMPTOM_KEYWORDS.put(Specialization.GYNECOLOGY, List.of(
				"pregnancy", "menstrual", "period pain", "pcos"));
		SYMPTOM_KEYWORDS.put(Specialization.PSYCHIATRY, List.of(
				"anxiety", "depression", "stress", "insomnia", "panic attack", "mood"));
		SYMPTOM_KEYWORDS.put(Specialization.OPHTHALMOLOGY, List.of(
				"eye pain", "blurred vision", "vision loss", "red eye"));
		SYMPTOM_KEYWORDS.put(Specialization.GENERAL_PHYSICIAN, List.of(
				"fever", "cold", "cough", "body pain", "fatigue", "vomiting", "diarrhea"));
	}

	@Override
	public List<DoctorProfile> recommendDoctorsForSymptoms(String symptomsText) {
		String text = symptomsText == null ? "" : symptomsText.toLowerCase();

		Specialization bestMatch = Specialization.GENERAL_PHYSICIAN;
		int bestScore = 0;

		for (Map.Entry<Specialization, List<String>> entry : SYMPTOM_KEYWORDS.entrySet()) {
			int score = 0;
			for (String keyword : entry.getValue()) {
				if (text.contains(keyword)) {
					score++;
				}
			}
			if (score > bestScore) {
				bestScore = score;
				bestMatch = entry.getKey();
			}
		}

		return doctorProfileRepository.findBySpecialization(bestMatch).stream()
				.sorted(Comparator.comparing(DoctorProfile::getAvgRating).reversed())
				.toList();
	}
}
