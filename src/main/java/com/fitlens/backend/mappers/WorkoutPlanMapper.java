package com.fitlens.backend.mappers;

import com.fitlens.backend.dto.workoutplan.WorkoutDayRequest;
import com.fitlens.backend.dto.workoutplan.WorkoutDayResponse;
import com.fitlens.backend.dto.workoutplan.CreateWorkoutPlanRequest;
import com.fitlens.backend.dto.workoutplan.WorkoutPlanResponse;
import com.fitlens.backend.entities.WorkoutDay;
import com.fitlens.backend.entities.WorkoutPlan;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

public interface WorkoutPlanMapper {


    WorkoutPlanResponse toPlanResponse(WorkoutPlan workoutPlan);

    WorkoutDayResponse toDayResponse(WorkoutDay workoutDay);

    WorkoutPlan toPlanEntity(CreateWorkoutPlanRequest createWorkoutPlanRequest);

    WorkoutDay toDayEntity(WorkoutDayRequest workoutDayRequest);

}
