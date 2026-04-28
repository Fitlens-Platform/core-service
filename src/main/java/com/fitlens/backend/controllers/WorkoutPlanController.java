package com.fitlens.backend.controllers;


import com.fitlens.backend.dto.PagedResponse;
import com.fitlens.backend.dto.workoutplan.WorkoutPlanFilter;
import com.fitlens.backend.dto.workoutplan.CreateWorkoutPlanRequest;
import com.fitlens.backend.dto.workoutplan.WorkoutPlanResponse;
import com.fitlens.backend.services.WorkoutPlanService;
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
@RequestMapping("/workout/plan")
@RequiredArgsConstructor
@Tag(name = "Workout Plans",
        description = "APIs for workout plan discovery and management. ")
@SecurityRequirement(name = "bearerAuth")
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    @Operation(
            summary = "Retrieve a paged list of workout plans",
            description = "Fetches a paginated list of workout plans with advanced filtering options (search by name/description, number of days, etc.)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout plans retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid filter or pagination parameters"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Access token is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<WorkoutPlanResponse>> getAllWorkoutPlans(WorkoutPlanFilter filter){
        log.info("Request to get a page of WorkoutPlans with filter: {}", filter);
        var response = workoutPlanService.getAllPlans(filter);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a New Workout Plan",
            description = "Registers a new workout plan in the system. These plans can then be discovered and assigned to customers. Ensure the plan name is unique to avoid conflicts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Workout plan created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Validation failed (e.g., missing required fields, invalid difficulty level)"),
            @ApiResponse(responseCode = "409", description = "Conflict - A workout plan with this name already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
    })
    @PostMapping
    public ResponseEntity<WorkoutPlanResponse> addWorkoutPlan(@Valid @RequestBody CreateWorkoutPlanRequest createWorkoutPlanRequest){
        log.info("REST request to add a new workout plan: {}", createWorkoutPlanRequest.getName());
        var response = workoutPlanService.addWorkoutPlan(createWorkoutPlanRequest);
        return ResponseEntity.ok(response);
    }


}
