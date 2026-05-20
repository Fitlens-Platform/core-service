package com.fitlens.backend.dto.workout_session;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Filter criteria for workout session")
public class WorkoutSessionFilter {

    @Schema(description = "Filter by creation date from (inclusive). Must be in UTC (ISO-8601 with 'Z' suffix)",
            example = "2026-01-01T00:00:00Z")
    private Instant createdFrom;

    @Schema(description = "Filter by creation date to (inclusive). Must be in UTC (ISO-8601 with 'Z' suffix)",
            example = "2027-12-31T23:59:59Z")
    private Instant createdTo;

    @Schema(description = "Sort by field name", example = "createdAt")
    @Builder.Default
    private String sortBy = "createdAt";

    @Schema(description = "Sort direction", example = "DESC", allowableValues = { "ASC", "DESC" })
    @Builder.Default
    private String sortDirection = "DESC";

    @Schema(description = "Page number (0-indexed)", example = "0")
    @Min(value = 0, message = "Page number must be 0 or greater")
    @Builder.Default
    private int page = 0;

    @Schema(description = "Page size (1-100)", example = "20")
    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    @Builder.Default
    private int size = 20;

}
