package com.fitlens.backend.services;

import com.fitlens.backend.dto.PagedResponse;
import com.fitlens.backend.dto.workout_session.CompleteSessionRequest;
import com.fitlens.backend.dto.workout_session.StartSessionRequest;
import com.fitlens.backend.dto.workout_session.WorkoutSessionFilter;
import com.fitlens.backend.dto.workout_session.WorkoutSessionResponse;
import com.fitlens.backend.entities.WorkoutExerciseResult;
import com.fitlens.backend.entities.WorkoutSession;
import com.fitlens.backend.entities.enums.SessionStatus;
import com.fitlens.backend.mappers.WorkoutSessionMapper;
import com.fitlens.backend.repositories.*;
import com.fitlens.backend.specifications.WorkoutSessionSpecification;
import com.fitlens.backend.utils.SortUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final UserRepository userRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutDayRepository workoutDayRepository;
    private final WorkoutSessionMapper sessionMapper;
    private final WorkoutExerciseResultRepository workoutExerciseResultRepository;

    @Transactional
    public WorkoutSessionResponse startSession(Long customerId, StartSessionRequest request) {
        log.info("Attempting to start a new session for user id: {}", customerId);

        workoutSessionRepository.findByUserIdAndStatus(customerId, SessionStatus.IN_PROGRESS)
                .ifPresent(activeSession -> {
                    log.info("Auto-completing previous active session ID: {}", activeSession.getId());

                    activeSession.setStatus(SessionStatus.COMPLETED);
                    activeSession.setCompletedAt(Instant.now());

                    workoutSessionRepository.save(activeSession);
                });

        var user = userRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var plan = workoutPlanRepository.findById(request.getPlanId())
                .orElseThrow(() -> new EntityNotFoundException("Plan not found"));

        var day = workoutDayRepository.findById(request.getDayId())
                .orElseThrow(() -> new EntityNotFoundException("Day not found"));

        var session = WorkoutSession.builder()
                .user(user)
                .status(SessionStatus.IN_PROGRESS)
                .startedAt(Instant.now())
                .sessionDate(LocalDate.now())
                .workoutDay(day)
                .workoutPlan(plan)
                .build();

        var savedSession = workoutSessionRepository.save(session);
        log.info("New session started successfully with ID: {}", savedSession.getId());

        return sessionMapper.toResponse(savedSession);
    }

    @Transactional
    public WorkoutSessionResponse completeSession(Long sessionId, CompleteSessionRequest request) {
        log.info("Completing session id: {}", sessionId);

        var session = workoutSessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        if (session.getStatus() != SessionStatus.IN_PROGRESS) {
            log.warn("Attempt to complete a session that is not IN_PROGRESS. ID: {}, Current Status: {}",
                    sessionId, session.getStatus());
            throw new IllegalStateException("Only sessions in progress can be completed");
        }

        var exerciseResults = workoutExerciseResultRepository.findByWorkoutSessionId(sessionId);
        float calculatedPerformanceScore = 0.0f;

        if (exerciseResults != null && !exerciseResults.isEmpty()) {
            double totalAccuracy = exerciseResults.stream()
                    .mapToDouble(WorkoutExerciseResult::getAvgAccuracyScore)
                    .sum();
            calculatedPerformanceScore = (float) (totalAccuracy / exerciseResults.size());
        }

        session.setStatus(SessionStatus.COMPLETED);
        session.setCompletedAt(Instant.now());
        session.setPerformanceScore(calculatedPerformanceScore);
        session.setThoughts(request.getThoughts());

        var updatedSession = workoutSessionRepository.save(session);

        log.info("Session {} successfully completed for user {} with Performance Score: {}",
                sessionId, session.getUser().getId(), calculatedPerformanceScore);

        return sessionMapper.toResponse(updatedSession);
    }

    public PagedResponse<WorkoutSessionResponse> getUserHistory(Long customerId, WorkoutSessionFilter filter){

        log.info("Getting history for customer: {}", customerId);

        userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("User not found"));

        var specification = WorkoutSessionSpecification.buildSpecification(customerId, filter);
        var sort = SortUtils.buildSort(filter.getSortBy(), filter.getSortDirection());
        var pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        var historyPage = workoutSessionRepository.findAll(specification, pageable);

        var history = historyPage.getContent().stream().map(sessionMapper::toResponse).toList();

        return PagedResponse.<WorkoutSessionResponse>builder()
                .content(history)
                .totalElements(historyPage.getTotalElements())
                .totalPages(historyPage.getTotalPages())
                .currentPage(historyPage.getNumber())
                .pageSize(historyPage.getSize())
                .hasNext(historyPage.hasNext())
                .hasPrevious(historyPage.hasPrevious())
                .build();
    }
}
