package com.fitlens.backend.dto.auth;

import com.fitlens.backend.entities.enums.UserGoal;
import com.fitlens.backend.utils.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
@Schema(description = "Request to sigun up a customer")
public class SignUpRequest {

	@NotBlank(message = "First name is required")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
	@Pattern(regexp = ValidationConstants.NAME_PATTERN, message = ValidationConstants.NAME_MESSAGE)
	@Schema(description = "Customer's first name", example = "Mahmoud", requiredMode = Schema.RequiredMode.REQUIRED)
	private String firstName;

	@NotBlank(message = "Last name is required")
	@Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
	@Pattern(regexp = ValidationConstants.NAME_PATTERN, message = ValidationConstants.NAME_MESSAGE)
	@Schema(description = "Customer's last name", example = "Samir", requiredMode = Schema.RequiredMode.REQUIRED)
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Please provide a valid email address")
	@Schema(description = "Customer's email address", example = "mahmoud.samir@example.com",
			requiredMode = Schema.RequiredMode.REQUIRED)
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
			message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
	@Schema(description = "User's account password", example = "StrongP@ss123",
			requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.WRITE_ONLY)
	private String password;

	@NotBlank(message = "Gender is required")
	@Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
	@Schema(description = "User's gender", example = "MALE", requiredMode = Schema.RequiredMode.REQUIRED)
	private String gender;

	@NotNull(message = "Birth date is required")
	@Past(message = "Birth date must be in the past")
	@Schema(description = "User's date of birth", example = "1998-05-20", requiredMode = Schema.RequiredMode.REQUIRED)
	private LocalDate birthDate;

	@NotNull(message = "Height is required")
	@DecimalMin(value = "50.0", message = "Height must be at least 50 cm")
	@DecimalMax(value = "250.0", message = "Height must be at most 250 cm")
	@Schema(description = "User's height in centimeters", example = "175.5",
			requiredMode = Schema.RequiredMode.REQUIRED)
	private BigDecimal heightCm;

	@NotNull(message = "Weight is required")
	@DecimalMin(value = "30.0", message = "Weight must be at least 30 kg")
	@DecimalMax(value = "300.0", message = "Weight must be at most 300 kg")
	@Schema(description = "User's weight in kilograms", example = "80.0", requiredMode = Schema.RequiredMode.REQUIRED)
	private BigDecimal weightKg;

	@NotNull(message = "Fitness goal is required")
	@Schema(description = "User's fitness goal", example = "BUILD_MUSCLE", requiredMode = Schema.RequiredMode.REQUIRED)
	private UserGoal goal;

	@NotNull(message = "Training days are required")
	@Min(value = 1, message = "Training days must be at least 1")
	@Max(value = 7, message = "Training days cannot exceed 7")
	@Schema(description = "Number of training days per week", example = "4",
			requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer trainingDays;

	@Schema(description = "Whether customer accepts terms and conditions", example = "true")
	private boolean acceptTerms;

	// public String getFullName(String firstName, String lastName){
	// return firstName + " " + lastName;
	// }

}
