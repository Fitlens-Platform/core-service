package com.fitlens.backend.repositories;

import com.fitlens.backend.entities.WorkoutExerciseResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WorkoutExerciseResultRepository
        extends JpaRepository<WorkoutExerciseResult,Long>, JpaSpecificationExecutor<WorkoutExerciseResult> {

    List<WorkoutExerciseResult> findByWorkoutSessionId(Long sessionId);
}
