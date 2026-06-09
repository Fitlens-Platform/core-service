package com.fitlens.backend.dto.workout_session;

import com.fitlens.backend.entities.enums.SessionStatus;
import com.fitlens.backend.entities.enums.SessionThoughts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionResponse {
    @Schema(description = "The unique identifier of the workout session", example = "2")
    private Long id;

    @Schema(description = "The ID of the user who owns this session", example = "1")
    private Long userId;

    @Schema(description = "The ID of the workout plan associated with this session", example = "1")
    private Long planId;

    @Schema(description = "The ID of the specific workout day", example = "1")
    private Long dayId;

    @Schema(description = "The current status of the workout session", example = "COMPLETED",
            allowableValues = {"IN_PROGRESS", "COMPLETED", "CANCELLED","PAUSED"})
    private SessionStatus status;

    @Schema(description = "The exact UTC timestamp when the session was started", example = "2026-05-31T17:00:00Z")
    private Instant startedAt;

    @Schema(description = "The exact UTC timestamp when the session was completed (null if still in progress)", example = "2026-05-31T18:15:00Z")
    private Instant completedAt;

    @Schema(description = "The total duration of the completed session in minutes (calculated automatically)", example = "75")
    private Long durationMinutes;

    @Schema(description = "The overall performance score calculated from exercise accuracies", example = "91.11")
    private Float performanceScore;

    @Schema(description = "The user's physical or mental feeling recorded at the end of the session", example = "FEELING_GREAT",
            allowableValues = {"FEELING_GREAT", "TIRED", "EXHAUSTED", "SICK", "INJURED"})
    private SessionThoughts thoughts;
}