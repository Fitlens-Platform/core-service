package com.fitlens.backend.repositories;

import com.fitlens.backend.entities.WorkoutDayExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutDayExerciseRepository extends JpaRepository<WorkoutDayExercise,Long> {

    List<WorkoutDayExercise> findByWorkoutDayIdOrderByOrderInDayAsc(Long dayId);

}
