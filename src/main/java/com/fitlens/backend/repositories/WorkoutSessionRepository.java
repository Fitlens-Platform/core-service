package com.fitlens.backend.repositories;

import com.fitlens.backend.entities.WorkoutSession;
import com.fitlens.backend.entities.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession,Long>, JpaSpecificationExecutor<WorkoutSession> {

    Optional<WorkoutSession> findByUserIdAndStatus(Long userId, SessionStatus status);
}
