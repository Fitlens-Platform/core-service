package com.fitlens.backend.dto.workout_exercise_result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseResultSummaryResponse {
    @Schema(description = "The unique identifier of the saved exercise result", example = "15")
    private Long resultId;

    @Schema(description = "The official name of the exercise performed", example = "Barbell Bench Press")
    private String exerciseName;

    @Schema(description = "Total number of sets actually completed by the user for this exercise", example = "3")
    private Integer setsCount;

    @Schema(description = "The calculated average accuracy score across all completed sets", example = "91.11")
    private Double avgAccuracyScore;
}