package com.fitlens.backend.controllers;

import com.fitlens.backend.dto.workout_day_exercise.CreateWorkoutDayExerciseRequest;
import com.fitlens.backend.dto.workout_day_exercise.WorkoutDayExerciseResponse;
import com.fitlens.backend.services.WorkoutDayExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/workout/days/{dayId}/exercises")
@RequiredArgsConstructor
@Tag(name = "Workout Day Exercises",
        description = "APIs for managing and organizing exercises within specific workout days.")
@SecurityRequirement(name = "bearerAuth")
public class WorkoutDayExerciseController {

    private final WorkoutDayExerciseService workoutDayExerciseService;

    @Operation(
            summary = "Add Exercise to Workout Day",
            description = "Assigns a specific exercise from the global library to a targeted workout day with prescribed parameters (sets, reps, rest)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exercise successfully assigned to the workout day"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Validation failed (e.g., negative sets/reps or invalid input data)"),
            @ApiResponse(responseCode = "404", description = "Not Found - Either the Workout Day or the Exercise does not exist in the database"),
            @ApiResponse(responseCode = "409", description = "Conflict - The exercise might already be assigned to this day (if you enforce unique assignments)"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
    })
    @PostMapping
    public ResponseEntity<WorkoutDayExerciseResponse> addExerciseToDay(
            @PathVariable Long dayId,
            @Valid @RequestBody CreateWorkoutDayExerciseRequest request) {

        log.info("REST request to add exercise ID: {} to workout day ID: {}",
                request.getExerciseId(), dayId);

        var response = workoutDayExerciseService.addExerciseToDay(dayId, request);

        log.info("Successfully added exercise to day ID: {}. Assignment ID: {}",
                dayId, response.getId());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get All Exercises for a Workout Day",
            description = "Retrieves an ordered list of all exercises assigned to a specific workout day. The results are sorted by their execution sequence (orderInDay)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercises retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found - The specified Workout Day does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
    })
    @GetMapping
    public ResponseEntity<List<WorkoutDayExerciseResponse>> getExercisesByDay(
            @PathVariable Long dayId) {

        log.info("REST request to get all exercises for workout day ID: {}", dayId);

        var response = workoutDayExerciseService.getExercisesByDay(dayId);

        log.info("Returning {} exercises for workout day ID: {}", response.size(), dayId);

        return ResponseEntity.ok(response);
    }
}
