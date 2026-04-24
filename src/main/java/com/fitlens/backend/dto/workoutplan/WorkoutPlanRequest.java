package com.fitlens.backend.dto.workoutplan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to add workout plan")
public class WorkoutPlanRequest {


    @Schema(description = "The official title of the training system", example = "Push Pull Legs (PPL)")
    private String name;

    @Schema(description = "Detailed overview explaining the goals, methodology, and target audience of the plan",
            example = "A high-intensity 6-day split designed for progressive overload and muscle hypertrophy.")
    private String description;

    @Schema(description = "Training frequency: The total number of workout sessions per week", example = "6")
    private Integer numberOfDays;

    @Schema(description = "A detailed collection of individual training days and their specific muscle group focus")
    private List<WorkoutDayRequest> days;
}
