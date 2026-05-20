package com.fitlens.backend.mappers;

import com.fitlens.backend.dto.workout_exercise_result.ExerciseResultDetailsResponse;
import com.fitlens.backend.dto.workout_exercise_result.ExerciseResultSummaryResponse;
import com.fitlens.backend.dto.workout_exercise_result.WorkoutExerciseResultRequest;
import com.fitlens.backend.entities.WorkoutExerciseResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WorkoutExerciseResultMapper {

    @Mapping(target = "workoutSession", ignore = true)
    @Mapping(target = "workoutDayExercise", ignore = true)
    @Mapping(target = "avgAccuracyScore", ignore = true)
    @Mapping(target = "accuracyScoresPerSet", ignore = true)
    WorkoutExerciseResult toEntity(WorkoutExerciseResultRequest request);

    @Mapping(target = "resultId", source = "id")
    @Mapping(target = "sessionId", source = "workoutSession.id")
    @Mapping(target = "exerciseName", source = "workoutDayExercise.exercise.name")
    ExerciseResultDetailsResponse toDetailsResponse(WorkoutExerciseResult entity);

    @Mapping(target = "resultId", source = "id")
    @Mapping(target = "exerciseName", source = "workoutDayExercise.exercise.name")
    @Mapping(target = "setsCount", ignore = true)
    ExerciseResultSummaryResponse toSummaryResponse(WorkoutExerciseResult entity);
}