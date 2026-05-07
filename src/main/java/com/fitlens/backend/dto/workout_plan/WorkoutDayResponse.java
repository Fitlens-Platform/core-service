package com.fitlens.backend.dto.workout_plan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Workout day response DTO")
public class WorkoutDayResponse {
    @Schema(description = "The sequence of the day within the training cycle (e.g., 1 for the first day)",
            example = "1")
    private Integer dayNumber;

    @Schema(description = "A descriptive name for the workout day, typically indicating the muscle groups or split focus",
            example = "Push Day (Chest, Shoulders & Triceps)")
    private String dayLabel;
}
