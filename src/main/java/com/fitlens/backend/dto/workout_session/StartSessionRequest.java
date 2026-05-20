package com.fitlens.backend.dto.workout_session;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartSessionRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    private Long planId;
    private Long dayId;
}