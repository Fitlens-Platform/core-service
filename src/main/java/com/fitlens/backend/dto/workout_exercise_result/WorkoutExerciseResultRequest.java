package com.fitlens.backend.dto.workout_exercise_result;

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
@Schema(description = "Request to add workout exercise result")
public class WorkoutExerciseResultRequest {
    private  Long sessionId;
    private Long workoutDayExerciseId;
    private List<Integer> repsCount;
    private List<Integer> correctReps;
    private List<Integer> durationSeconds;
    private List<Double> weightUsed;
    private List<String> aiFeedback;
}
