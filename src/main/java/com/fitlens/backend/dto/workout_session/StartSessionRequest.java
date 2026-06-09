package com.fitlens.backend.dto.workout_session;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartSessionRequest {

    @NotNull(message = "Plan ID is required")
    @Positive(message = "Plan ID must be a positive number")
    @Schema(description = "The unique identifier of the workout plan",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long planId;

    @NotNull(message = "Day ID is required")
    @Positive(message = "Day ID must be a positive number")
    @Schema(description = "The unique identifier of the specific workout day within the plan",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long dayId;
}