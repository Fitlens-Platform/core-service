package com.fitlens.backend.dto.workout_day_exercise;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for linking an exercise from the library to a specific workout day with training parameters.")
public class CreateWorkoutDayExerciseRequest {

    @Schema(description = "The unique identifier of the exercise being assigned to the day", example = "45")
    private Long exerciseId;

    @Schema(description = "The sequence or position of the exercise within the daily workout routine", example = "1")
    private Integer orderInDay;

    @Schema(description = "The target number of sets to be performed for this exercise", example = "3")
    private Integer sets;

    @Schema(description = "The target number of repetitions per set", example = "12")
    private Integer reps;

    @Schema(description = "The rest duration in seconds between sets for recovery", example = "60")
    private Integer restSeconds;

}
