package com.fitlens.backend.mappers;

import com.fitlens.backend.dto.exercise.CreateExerciseRequest;
import com.fitlens.backend.dto.exercise.ExerciseResponse;
import com.fitlens.backend.entities.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExerciseMapper {

    ExerciseResponse toResponse(Exercise exercise);

    Exercise toEntity(CreateExerciseRequest request);
}
