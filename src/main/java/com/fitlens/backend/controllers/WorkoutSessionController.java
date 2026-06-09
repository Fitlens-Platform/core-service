package com.fitlens.backend.controllers;

import com.fitlens.backend.dto.PagedResponse;
import com.fitlens.backend.dto.workout_session.CompleteSessionRequest;
import com.fitlens.backend.dto.workout_session.StartSessionRequest;
import com.fitlens.backend.dto.workout_session.WorkoutSessionFilter;
import com.fitlens.backend.dto.workout_session.WorkoutSessionResponse;
import com.fitlens.backend.services.WorkoutSessionService;
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
@RequestMapping("/workout/session")
@RequiredArgsConstructor
@Tag(name = "Workout Sessions", description = "APIs for managing training sessions lifecycle")
@SecurityRequirement(name = "bearerAuth")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    @Operation(summary = "Start a new workout session", description = "Initializes a session, sets status to IN_PROGRESS and records the start time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Session started successfully"),
            @ApiResponse(responseCode = "400", description = "User already has an active session")
    })
    @PostMapping("/start")
    public ResponseEntity<WorkoutSessionResponse> startSession(
            @RequestHeader("X-User-Id") Long customerId,
            @Valid @RequestBody StartSessionRequest request) {

        log.info("REST request to start a new session for user: {} with plan: {}",
                customerId, request.getPlanId());

        var response = workoutSessionService.startSession(customerId, request);

        log.info("Session started successfully. Session ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Complete an active session", description = "Finalizes the session, records completion time, and saves performance score.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session completed successfully"),
            @ApiResponse(responseCode = "404", description = "Active session not found")
    })
    @PatchMapping("/{id}/complete")
    public ResponseEntity<WorkoutSessionResponse> completeSession(
            @PathVariable Long id,
            @Valid @RequestBody CompleteSessionRequest request) {

        log.info("REST request to complete session ID: {}", id);

        var response = workoutSessionService.completeSession(id, request);

        log.info("Session ID: {} completed and saved.", id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get user workout history (Paginated)")
    @GetMapping("/history")
    public ResponseEntity<PagedResponse<WorkoutSessionResponse>> getUserHistory(
            @RequestHeader("X-User-Id") Long customerId,
            @Valid @ModelAttribute WorkoutSessionFilter filter) {

        log.info("REST request to get history for user: {}, Page: {}, Size: {}", customerId, filter.getPage(), filter.getSize());

        var response = workoutSessionService.getUserHistory(customerId, filter);
        return ResponseEntity.ok(response);
    }

}
