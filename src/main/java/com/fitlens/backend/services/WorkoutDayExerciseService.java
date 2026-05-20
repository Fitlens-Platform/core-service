package com.fitlens.backend.services;

import com.fitlens.backend.dto.workout_day_exercise.CreateWorkoutDayExerciseRequest;
import com.fitlens.backend.dto.workout_day_exercise.WorkoutDayExerciseResponse;
import com.fitlens.backend.entities.WorkoutDayExercise;
import com.fitlens.backend.mappers.WorkoutDayExerciseMapper;
import com.fitlens.backend.repositories.ExerciseRepository;
import com.fitlens.backend.repositories.WorkoutDayExerciseRepository;
import com.fitlens.backend.repositories.WorkoutDayRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WorkoutDayExerciseService {

    private final WorkoutDayExerciseRepository workoutDayExerciseRepository;

    private final WorkoutDayExerciseMapper workoutDayExerciseMapper;

    private final WorkoutDayRepository workoutDayRepository;

    private final ExerciseRepository exerciseRepository;

    public WorkoutDayExerciseResponse addExerciseToDay(
                    Long dayId,
                    CreateWorkoutDayExerciseRequest request){

        log.info("Adding exercise to day");

        var workoutExercise = workoutDayExerciseMapper.toEntity(request);

        var workoutDay = workoutDayRepository.findById(dayId).orElseThrow(() -> new EntityNotFoundException("Day not found"));

        var exercise = exerciseRepository.findById(request.getExerciseId()).orElseThrow(() -> new EntityNotFoundException("Exercise not found"));

        workoutExercise.setExercise(exercise);
        workoutExercise.setWorkoutDay(workoutDay);

        var savedExercise = workoutDayExerciseRepository.save(workoutExercise);

        return workoutDayExerciseMapper.toResponse(savedExercise);

    }

    public List<WorkoutDayExerciseResponse> getExercisesByDay(Long dayId) {

        log.info("Fetching exercises for workout day ID: {}", dayId);

        var dayExercises = workoutDayExerciseRepository.findByWorkoutDayIdOrderByOrderInDayAsc(dayId);

        if (dayExercises.isEmpty()) {
            log.warn("No exercises found for workout day ID: {}", dayId);
        } else {
            log.info("Successfully retrieved {} exercises for day ID: {}", dayExercises.size(), dayId);
        }

        List<WorkoutDayExerciseResponse> exerciseResponses = dayExercises.stream()
                .map(workoutDayExerciseMapper::toResponse)
                .toList();

        return exerciseResponses;
    }
}
