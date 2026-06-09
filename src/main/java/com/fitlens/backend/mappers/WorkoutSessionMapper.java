package com.fitlens.backend.mappers;

import com.fitlens.backend.dto.workout_session.WorkoutSessionResponse;
import com.fitlens.backend.entities.WorkoutSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.Duration;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface WorkoutSessionMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "planId", source = "workoutPlan.id")
    @Mapping(target = "dayId", source = "workoutDay.id")
    @Mapping(target = "durationMinutes", expression = "java(calculateDuration(session))")
    WorkoutSessionResponse toResponse(WorkoutSession session);

    default Long calculateDuration(WorkoutSession session) {
        if (session.getStartedAt() == null || session.getCompletedAt() == null) {
            return 0L;
        }
        return Duration.between(session.getStartedAt(), session.getCompletedAt()).toMinutes();
    }
}
