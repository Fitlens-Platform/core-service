package com.fitlens.backend.dto.exercise;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to add exercise")
public class CreateExerciseRequest {

    @NotBlank(message = "Exercise name is required")
    @Size(min = 3, max = 100, message = "Exercise name must be between 3 and 100 characters")
    @Schema(description = "Official name of the exercise", example = "Barbell Bench Press")
    private String name;

    @NotBlank(message = "Short description is required")
    @Size(max = 255, message = "Short description cannot exceed 255 characters")
    @Schema(description = "A concise one-line summary of the exercise", example = "A classic compound movement for chest development.")
    private String shortDescription;

    @NotBlank(message = "Category is required")
    @Schema(description = "The type of training category", example = "WEIGHTLIFTING",allowableValues = { "WEIGHTLIFTING", "BODYWEIGHT", "STRETCHING"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String category;

    @NotBlank(message = "Difficulty level is required")
    @Schema(description = "Target skill level for this exercise", example = "INTERMEDIATE",allowableValues = {"BEGINNER", "INTERMEDIATE", "ADVANCED"},
    requiredMode = Schema.RequiredMode.REQUIRED)
    private String difficultyLevel;

    @NotBlank(message = "Target muscle is required")
    @Schema(description = "The primary muscle group targeted by this exercise", example = "Chest")
    private String targetMuscle;

    @Schema(description = "List of assistant muscles involved in the movement", example = "[\"Triceps\", \"Front Deltoids\"]")
    private List<String> secondaryMuscles;

    @Schema(description = "List of equipment needed to perform the exercise", example = "[\"Barbell\", \"Flat Bench\"]")
    private List<String> requiredEquipments;

    @Schema(description = "Safety tips and common mistakes to avoid",
            example = "Keep your feet flat on the floor and avoid arching your back excessively.")
    private String tipsAndCautions;

    @Schema(description = "The movement type (Compound or Isolation)", example = "COMPOUND",allowableValues = { "COMPOUND", "ISOLATION"},
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String mechanic;

}
