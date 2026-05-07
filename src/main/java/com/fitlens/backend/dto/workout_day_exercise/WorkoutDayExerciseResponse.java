package com.fitlens.backend.dto.workout_day_exercise;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing complete details of an exercise assigned to a specific workout day.")
public class WorkoutDayExerciseResponse {

    @Schema(description = "The unique internal ID of the day-exercise assignment record.", example = "500")
    private Long id;

    @Schema(description = "Human-readable label of the workout day (e.g., Day 1 - Pull Session).", example = "Chest & Triceps")
    private String workoutDayLabel;

    @Schema(description = "The official name of the exercise retrieved from the master library.", example = "Incline Dumbbell Press")
    private String exerciseName;

    @Schema(description = "The assigned execution order for this exercise in the current day's routine.", example = "1")
    private Integer orderInDay;

    @Schema(description = "The number of sets prescribed for this session.", example = "4")
    private Integer sets;

    @Schema(description = "The target number of repetitions per set.", example = "10")
    private Integer reps;

    @Schema(description = "The designated rest time in seconds between sets.", example = "90")
    private Integer restSeconds;

}
