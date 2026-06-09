package com.fitlens.backend.dto.workout_exercise_result;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to add workout exercise result")
public class WorkoutExerciseResultRequest {
    @NotNull(message = "Session ID is required")
    @Positive(message = "Session ID must be a positive number")
    @Schema(description = "The ID of the active workout session", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sessionId;

    @NotNull(message = "Workout Day Exercise ID is required")
    @Positive(message = "Workout Day Exercise ID must be a positive number")
    @Schema(description = "The ID of the specific exercise scheduled for this workout day", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long workoutDayExerciseId;

    @NotEmpty(message = "Reps count list cannot be empty")
    @Schema(description = "List of total repetitions performed per set", example = "[12, 10, 8]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Integer> repsCount;

    @NotEmpty(message = "Correct reps list cannot be empty")
    @Schema(description = "List of correctly performed repetitions per set (evaluated by AI)", example = "[10, 9, 8]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Integer> correctReps;

    @NotEmpty(message = "Duration seconds list cannot be empty")
    @Schema(description = "Time taken to complete each set in seconds", example = "[45, 40, 35]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Integer> durationSeconds;

    @NotEmpty(message = "Weight used list cannot be empty")
    @Schema(description = "Weight lifted per set in kilograms", example = "[60.0, 65.0, 70.0]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Double> weightUsed;

    @Schema(description = "AI-generated feedback and technique corrections per set",
            example = "[\"Your eccentric phase is a bit fast, try to control the weight more on the way down.\", \"Great job, your technique improved significantly in this set.\", \"Excellent performance and perfect form, champion!\"]")
    private List<String> aiFeedback;
}