package com.fitlens.backend.dto.workout_exercise_result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExerciseResultDetailsResponse {
    @Schema(description = "The unique identifier of the saved exercise result", example = "15")
    private Long resultId;

    @Schema(description = "The ID of the session this exercise belongs to", example = "2")
    private Long sessionId;

    @Schema(description = "The official name of the exercise performed", example = "Barbell Bench Press")
    private String exerciseName;

    @Schema(description = "The calculated average accuracy score across all sets", example = "91.11")
    private Double avgAccuracyScore;

    @Schema(description = "List of total repetitions performed per set", example = "[12, 10, 8]")
    private List<Integer> repsCount;

    @Schema(description = "List of correctly performed repetitions per set (evaluated by AI)", example = "[10, 9, 8]")
    private List<Integer> correctReps;

    @Schema(description = "Time taken to complete each set in seconds", example = "[45, 40, 35]")
    private List<Integer> durationSeconds;

    @Schema(description = "Weight lifted per set in kilograms", example = "[60.0, 65.0, 70.0]")
    private List<Double> weightUsed;

    @Schema(description = "Calculated accuracy percentage for each individual set", example = "[83.33, 90.0, 100.0]")
    private List<Double> accuracyScoresPerSet;

    @Schema(description = "AI-generated feedback and technique corrections per set",
            example = "[\"Control the descent.\", \"Good posture.\", \"Perfect form.\"]")
    private List<String> aiFeedback;
}
