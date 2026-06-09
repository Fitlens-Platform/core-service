package com.fitlens.backend.dto.exercise;

import com.fitlens.backend.entities.enums.DifficultyLevel;
import com.fitlens.backend.entities.enums.ExerciseCategory;
import com.fitlens.backend.entities.enums.ExerciseMechanic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Exercise response DTO")
public class ExerciseResponse {

    @Schema(description = "Exercise unique identifier", example = "1")
    private Long id;

    @Schema(description = "Official name of the exercise", example = "Barbell Bench Press")
    private String name;

    @Schema(description = "A concise summary describing the exercise movement",
            example = "A classic compound movement for chest development and upper body strength.")
    private String shortDescription;

    @Schema(description = "The training category of the exercise", example = "WEIGHTLIFTING")
    private ExerciseCategory category;

    @Schema(description = "Target skill level required to perform the exercise safely", example = "INTERMEDIATE")
    private DifficultyLevel difficultyLevel;

    @Schema(description = "The primary muscle group targeted by this exercise", example = "Chest")
    private String targetMuscle;

    @Schema(description = "List of secondary or assistant muscles involved in the movement",
            example = "[\"Triceps\", \"Front Deltoids\"]")
    private List<String> secondaryMuscles;

    @Schema(description = "List of tools or machines required to perform the exercise",
            example = "[\"Barbell\", \"Flat Bench\"]")
    private List<String> requiredEquipments;

    @Schema(description = "Direct URL to an instructional video or animation of the exercise",
            example = "https://fitlens.com/videos/bench-press.mp4")
    private String videoUrl;

    @Schema(description = "Crucial safety advice and common form mistakes to avoid",
            example = "Keep your feet flat on the floor and avoid bouncing the bar off your chest.")
    private String tipsAndCautions;

    @Schema(description = "The mechanical classification of the movement (Compound or Isolation)",
            example = "COMPOUND")
    private ExerciseMechanic mechanic;

    @Schema(description = "Indicates whether the exercise involves explosive or plyometric movements (e.g., Box Jumps)",
            example = "false")
    private boolean isExplosive;
}
