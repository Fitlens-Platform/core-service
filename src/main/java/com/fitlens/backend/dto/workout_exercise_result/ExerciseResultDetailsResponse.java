package com.fitlens.backend.dto.workout_exercise_result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExerciseResultDetailsResponse {
    private Long resultId;
    private Long sessionId;
    private String exerciseName;
    private Double avgAccuracyScore;
    private List<Integer> repsCount;
    private List<Integer> correctReps;
    private List<Integer> durationSeconds;
    private List<Double> weightUsed;
    private List<Double> accuracyScoresPerSet;
    private List<String> aiFeedback;
}
