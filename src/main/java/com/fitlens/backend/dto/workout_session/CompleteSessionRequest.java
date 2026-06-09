package com.fitlens.backend.dto.workout_session;

import com.fitlens.backend.entities.enums.SessionThoughts;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "The user's physical or mental feeling after completing the session",
            example = "FEELING_GREAT",
            allowableValues = {"FEELING_GREAT", "TIRED", "EXHAUSTED", "SICK", "INJURED"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private SessionThoughts thoughts;}