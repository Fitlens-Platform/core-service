package com.fitlens.backend.mappers;

import com.fitlens.backend.dto.workout_day_exercise.CreateWorkoutDayExerciseRequest;
import com.fitlens.backend.dto.workout_day_exercise.WorkoutDayExerciseResponse;
import com.fitlens.backend.entities.WorkoutDayExercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkoutDayExerciseMapper {

    @Mapping(target = "workoutDay", ignore = true)
    @Mapping(target = "exercise", ignore = true)
    WorkoutDayExercise toEntity(CreateWorkoutDayExerciseRequest request);

    @Mapping(target = "workoutDayLabel", source = "workoutDay.dayLabel")
    @Mapping(target = "exerciseName", source = "exercise.name")
    WorkoutDayExerciseResponse toResponse(WorkoutDayExercise exercise);

}
