package com.fitlens.backend.dto.exercise;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Exercise filter criteria for customer history")
public class ExerciseFilter {

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


    @Size(max = 100, message = "Search keyword must not exceed 100 characters")
    @Schema(description = "A keyword for global search across exercise names, categories, target muscles, mechanics, and short descriptions. It matches partial text and is case-insensitive.",
            example = "Bench Press")
    private String search;


}
