package com.fitlens.backend.repositories;

import com.fitlens.backend.entities.WorkoutDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutDayRepository extends JpaRepository<WorkoutDay,Long> {
}
