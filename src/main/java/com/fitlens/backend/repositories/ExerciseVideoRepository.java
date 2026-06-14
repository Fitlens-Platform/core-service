package com.fitlens.backend.repositories;

import com.fitlens.backend.entities.ExerciseVideo;
import com.fitlens.backend.entities.enums.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseVideoRepository extends JpaRepository<ExerciseVideo, Long> {
    List<ExerciseVideo> findByMuscleGroup(MuscleGroup muscleGroup);}
