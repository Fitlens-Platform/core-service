package com.fitlens.backend.controllers;

import com.fitlens.backend.dto.workout_exercise_result.ExerciseResultDetailsResponse;
import com.fitlens.backend.dto.workout_exercise_result.ExerciseResultSummaryResponse;
import com.fitlens.backend.dto.workout_exercise_result.WorkoutExerciseResultRequest;
import com.fitlens.backend.services.WorkoutExerciseResultService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/workout/session")
@RequiredArgsConstructor
@Tag(name = "Workout Sessions", description = "APIs for managing training sessions lifecycle")
@SecurityRequirement(name = "bearerAuth")
public class WorkoutExerciseResultController {

    private final WorkoutExerciseResultService workoutExerciseResultService;

    @PostMapping
    public ResponseEntity<ExerciseResultSummaryResponse> submitExerciseResult(@RequestBody @Valid WorkoutExerciseResultRequest request) {
        log.info("Received request to save workout result for session: {}", request.getSessionId());
        var response = workoutExerciseResultService.submitExerciseResult(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<ExerciseResultSummaryResponse>> getSessionSummary(@PathVariable Long sessionId) {
        log.info("Fetching exercise summary for session: {}", sessionId);
        var response = workoutExerciseResultService.getSessionSummary(sessionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{resultId}")
    public ResponseEntity<ExerciseResultDetailsResponse> getExerciseDetails(@PathVariable Long resultId) {
        log.info("Fetching details for exercise result ID: {}", resultId);
        var response = workoutExerciseResultService.getExerciseDetails(resultId);
        return ResponseEntity.ok(response);
    }
    
}

