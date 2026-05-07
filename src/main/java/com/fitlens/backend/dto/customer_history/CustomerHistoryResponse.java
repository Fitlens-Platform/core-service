package com.fitlens.backend.dto.customer_history;

import com.fitlens.backend.entities.enums.FitnessLevel;
import com.fitlens.backend.entities.enums.UserGoal;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Customer history response DTO")
public class CustomerHistoryResponse {

	@Schema(description = "Customer ID", example = "1")
	private Long customerId;

	@Schema(description = "Customer's current fitness experience level", example = "INTERMEDIATE")
	private FitnessLevel fitnessLevel;

	@Schema(description = "Customer's height in centimeters", example = "183.5")
	private BigDecimal heightCm;

	@Schema(description = "Customer's weight in kilograms", example = "85.0")
	private BigDecimal weightKg;

	@Schema(description = "Customer's current fitness goal", example = "BUILD_MUSCLE")
	private UserGoal goal;

	@Schema(description = "Number of planned training days per week", example = "5")
	private int trainingDays;

}
