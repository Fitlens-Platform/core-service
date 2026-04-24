package com.fitlens.backend.repositories;

import com.fitlens.backend.entities.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan,Long> , JpaSpecificationExecutor<WorkoutPlan> {
}
