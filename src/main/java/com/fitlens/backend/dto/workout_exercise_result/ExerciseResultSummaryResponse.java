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
    private Long resultId;
    private String exerciseName;
    private Integer setsCount;
    private Double avgAccuracyScore;
}
