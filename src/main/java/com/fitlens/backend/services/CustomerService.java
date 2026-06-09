package com.fitlens.backend.services;

import com.fitlens.backend.dto.customer.CustomerResponse;
import com.fitlens.backend.dto.customer.CustomerUpdateRequest;
import com.fitlens.backend.dto.customer.UpdateUserPlanRequest;
import com.fitlens.backend.entities.User;
import com.fitlens.backend.mappers.CustomerMapper;
import com.fitlens.backend.repositories.UserRepository;
import com.fitlens.backend.repositories.WorkoutPlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {

	private final UserRepository userRepository;

	private final CustomerMapper customerMapper;

	private final CustomerHistoryService customerHistoryService;

	private final WorkoutPlanRepository workoutPlanRepository;

	public CustomerResponse getCustomerProfile(Long customerId) {

		var customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("User not found"));

		return customerMapper.toResponse(customer);
	}

	@Transactional
	public CustomerResponse updateProfile(Long customerId, CustomerUpdateRequest request) {

		if (request == null) {
			throw new IllegalArgumentException("Update request body cannot be null");
		}

		var customer = findByIdOrThrow(customerId);
		boolean isPhysicalUpdate = isPhysicalDataSent(request);

		customerMapper.updateCustomer(request, customer);

		if (isPhysicalUpdate) {
			log.info("Physical metrics updated. Recalculating calories and taking snapshot for customer ID: {}", customerId);

			customer.setDailyCalories(calculateCalories(customer));

			customerHistoryService.saveCustomerHistorySnapshot(customer);
		}

		var savedCustomer = userRepository.save(customer);

		return customerMapper.toResponse(savedCustomer);
	}

	private boolean isPhysicalDataSent(CustomerUpdateRequest request) {
		return request.getWeightKg() != null ||
				request.getHeightCm() != null ||
				request.getBirthDate() != null ||
				request.getGender() != null ||
				request.getGoal() != null ||
				request.getFitnessLevel() != null ||
				request.getTrainingDays() != null;
	}

	@Transactional
	public User findByIdOrThrow(Long customerId) {
		return userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("User not found"));
	}

	/**
	 * Calculates the daily caloric intake requirement using the Mifflin-St Jeor Equation.
	 * * Formula:
	 * BMR = (10 * weight_kg) + (6.25 * height_cm) - (5 * age_years) + s
	 * Where 's' is +5 for males and -161 for females.
	 * * TDEE = BMR * Activity_Factor
	 * * Goal Adjustments:
	 * - LOSE_WEIGHT: TDEE - 500 kcal
	 * - GAIN_WEIGHT: TDEE + 500 kcal
	 * - BUILD_MUSCLE: TDEE + 300 kcal (Lean surplus for muscle synthesis)
	 * * @param user The user entity containing physical metrics, gender, and goal
	 * @return Total recommended daily calories, or null if required data is missing
	 */
	public Integer calculateCalories(User user) {
		if (user.getWeightKg() == null || user.getHeightCm() == null ||
				user.getBirthDate() == null || user.getGender() == null || user.getGoal() == null) {
			log.warn("Missing required metrics for calorie calculation");
			return null;
		}

		// Formula Constants
		final double WEIGHT_MULTIPLIER = 10.0;
		final double HEIGHT_MULTIPLIER = 6.25;
		final double AGE_MULTIPLIER = 5.0;
		final int MALE_OFFSET = 5;
		final int FEMALE_OFFSET = -161;

		final int WEIGHT_CHANGE_ADJUSTMENT = 500;
		final int MUSCLE_BUILD_ADJUSTMENT = 300;


		int age = Period.between(user.getBirthDate(), LocalDate.now()).getYears();

		int genderOffset = user.getGender().equalsIgnoreCase("FEMALE") ? FEMALE_OFFSET : MALE_OFFSET;

		// Basal Metabolic Rate (BMR)
		double bmr = (WEIGHT_MULTIPLIER * user.getWeightKg().doubleValue())
				+ (HEIGHT_MULTIPLIER * user.getHeightCm().doubleValue())
				- (AGE_MULTIPLIER * age)
				+ genderOffset;

		// Total Daily Energy Expenditure (TDEE) based on Activity Factor
		double activityFactor = switch (user.getTrainingDays()) {
			case 0 -> 1.2;          // Sedentary
			case 1, 2, 3 -> 1.375;  // Lightly active
			case 4, 5 -> 1.55;      // Moderately active
			default -> 1.725;       // Very active
		};

		double tdee = bmr * activityFactor;

		String userGoal = user.getGoal().toString().toUpperCase();

		return switch (userGoal) {
			case "LOSE_WEIGHT" -> (int) (tdee - WEIGHT_CHANGE_ADJUSTMENT);
			case "GAIN_WEIGHT" -> (int) (tdee + WEIGHT_CHANGE_ADJUSTMENT);
			case "BUILD_MUSCLE" -> (int) (tdee + MUSCLE_BUILD_ADJUSTMENT);
			default -> (int) tdee; // Fallback to Maintenance
		};
	}

	public CustomerResponse updatePlan(Long customerId,UpdateUserPlanRequest request){

		log.info("Updating plan for customer ID: {} to new plan ID: {}",
				customerId, request.getPlanId());

		var customer = findByIdOrThrow(customerId);
		var workoutPlan = workoutPlanRepository.findById(request.getPlanId())
				.orElseThrow(() -> new RuntimeException("Workout Plan not found with id: " + request.getPlanId()));

		customer.setWorkoutPlan(workoutPlan);

		var savedCustomer = userRepository.save(customer);

		log.info("successfully updated the plan");
		return customerMapper.toResponse(savedCustomer);
	}

}
