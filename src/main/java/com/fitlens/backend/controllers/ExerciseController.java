package com.fitlens.backend.controllers;

import com.fitlens.backend.dto.PagedResponse;
import com.fitlens.backend.dto.exercise.CreateExerciseRequest;
import com.fitlens.backend.dto.exercise.ExerciseFilter;
import com.fitlens.backend.dto.exercise.ExerciseResponse;
import com.fitlens.backend.services.ExerciseService;
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

@RestController
@Slf4j
@RequestMapping("/workout/exercise")
@RequiredArgsConstructor
@Tag(name = "Exercise Management",
        description = "APIs for managing fitness exercises, including instructional content, and advanced filtering by equipment or muscle groups.")
@SecurityRequirement(name = "bearerAuth")
public class ExerciseController {

    private final ExerciseService exerciseService;


    @Operation(summary = "Search and Retrieve Exercises",
            description = "Fetches a paginated list of exercises. Supports global search and advanced filtering by category, difficulty, muscle group, and mechanics.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercises retrieved successfully"),
            @ApiResponse(description = "Bad Request - Invalid filter parameters", responseCode = "400"),
            @ApiResponse(description = "Internal Server Error", responseCode = "500")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<ExerciseResponse>> getAllExercises(@Valid @ModelAttribute ExerciseFilter filter){

        log.info("Request to fetch paged exercises with filter {}", filter);

        var response = exerciseService.getAllExercises(filter);

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Create a New Exercise",
            description = "Adds a new exercise to the database. Includes details for AI pose tracking, instructions, and muscle targeting. Name must be unique.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exercise created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Validation failed (e.g., missing required fields or invalid enum values)"),
            @ApiResponse(responseCode = "409", description = "Conflict - An exercise with the same name already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<ExerciseResponse> addExercise(@Valid @RequestBody CreateExerciseRequest request){

        log.info("Request to add new exercise with name: {}", request.getName());

        var response = exerciseService.addExercise(request);

        return ResponseEntity.ok(response);
    }
}
