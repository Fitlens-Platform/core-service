package com.fitlens.backend.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Update customer plan request")
public class UpdateUserPlanRequest {

    @NotNull(message = "Plan ID is required")
    @Positive(message = "Plan ID must be a positive number")
    @Schema(description = "The unique identifier (ID) of the workout plan to be assigned to the user",
            example = "1")
    private Long planId;

}
