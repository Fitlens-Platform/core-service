package com.fitlens.backend.dto.customer;

import com.fitlens.backend.entities.enums.FitnessLevel;
import com.fitlens.backend.entities.enums.UserGoal;
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
@Schema(description = "Update customer profile request")
public class CustomerUpdateRequest {

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Schema(description = "Customer's first name", example = "Mahmoud")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Schema(description = "Customer's last name", example = "Samir")
    private String lastName;

    @Email(message = "Please provide a valid email address")
    @Schema(description = "Customer's email address", example = "mahmoud.samir@example.com")
    private String email;

    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
    @Schema(description = "Customer's gender", example = "MALE")
    private String gender;

    @Past(message = "Birth date must be in the past")
    @Schema(description = "Customer's birth date", example = "2003-12-01")
    private LocalDate birthDate;

    @DecimalMin(value = "50.0", message = "Height must be at least 50 cm")
    @DecimalMax(value = "250.0", message = "Height must be at most 250 cm")
    @Positive(message = "Height must be a positive number")
    @Schema(description = "Customer's height in centimeters", example = "183.5")
    private BigDecimal heightCm;

    @DecimalMin(value = "20.0", message = "Weight must be at least 20 kg")
    @DecimalMax(value = "300.0", message = "Weight must be at most 300 kg")
    @Positive(message = "Weight must be a positive number")
    @Schema(description = "Customer's weight in kilograms", example = "85.0")
    private BigDecimal weightKg;

    @Schema(description = "Customer's current fitness goal", example = "BUILD_MUSCLE")
    private UserGoal goal;

    @Schema(description = "Customer's current fitness experience level", example = "INTERMEDIATE")
    private FitnessLevel fitnessLevel;

    @Min(value = 0, message = "Training days cannot be less than 0")
    @Max(value = 7, message = "Training days cannot be more than 7")
    @Schema(description = "Number of planned training days per week", example = "5")
    private Integer trainingDays;
}
