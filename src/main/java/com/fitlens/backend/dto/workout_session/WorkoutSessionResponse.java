package com.fitlens.backend.dto.workout_session;

import com.fitlens.backend.entities.enums.SessionStatus;
import com.fitlens.backend.entities.enums.SessionThoughts;
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
    private Long id;
    private Long userId;
    private Long planId;
    private Long dayId;
    private SessionStatus status;
    private Instant startedAt;
    private Instant completedAt;
    private Long durationMinutes;
    private Float performanceScore;
    private SessionThoughts thoughts;
}