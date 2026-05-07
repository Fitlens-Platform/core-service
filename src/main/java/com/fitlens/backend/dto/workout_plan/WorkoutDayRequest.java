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
@Schema(description = "Request to add workout day")
public class WorkoutDayRequest {

    @Schema(description = "The sequential order of the day within the workout plan (e.g., 1 for Day 1)",
            example = "1")
    private Integer dayNumber;

    @Schema(description = "A descriptive label for the workout day, indicating the muscle groups or training focus",
            example = "Push Day (Chest, Shoulders & Triceps)")
    private String dayLabel;

}
