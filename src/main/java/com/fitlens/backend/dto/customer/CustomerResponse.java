package com.fitlens.backend.dto.customer;

import com.fitlens.backend.entities.enums.FitnessLevel;
import com.fitlens.backend.entities.enums.UserGoal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer response DTO")
public class CustomerResponse {

	@Schema(description = "Customer ID", example = "1")
	private Long id;

	@Schema(description = "Customer's first name", example = "Mahmoud")
	private String firstName;

	@Schema(description = "Customer's last name", example = "Samir")
	private String lastName;

	@Schema(description = "Customer's email address", example = "mahmoud.samir@example.com")
	private String email;

	@Schema(description = "Customer's gender", example = "MALE")
	private String gender;

	@Schema(description = "Customer's birth date", example = "2003-12-1")
	private LocalDate birthDate;

	@Schema(description = "Customer's height in centimeters", example = "183.5")
	private BigDecimal heightCm;

	@Schema(description = "Customer's weight in kilograms", example = "85.0")
	private BigDecimal weightKg;

	@Schema(description = "Customer's current fitness goal", example = "BUILD_MUSCLE")
	private UserGoal goal;

	@Schema(description = "Customer's current fitness experience level", example = "INTERMEDIATE")
	private FitnessLevel fitnessLevel;

	@Schema(description = "Number of planned training days per week", example = "5")
	private int trainingDays;

}
