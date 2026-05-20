package com.fitlens.backend.dto.workout_session;

import com.fitlens.backend.entities.enums.SessionThoughts;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteSessionRequest {
    @NotNull(message = "Performance score is required")
    @Min(0) @Max(100)
    private Float performanceScore;

    @NotNull(message = "Thoughts are required")
    private SessionThoughts thoughts;
}