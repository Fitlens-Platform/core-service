package com.fitlens.backend.services;

import com.fitlens.backend.dto.workout_exercise_result.ExerciseResultDetailsResponse;
import com.fitlens.backend.dto.workout_exercise_result.ExerciseResultSummaryResponse;
import com.fitlens.backend.dto.workout_exercise_result.WorkoutExerciseResultRequest;
import com.fitlens.backend.entities.WorkoutExerciseResult;
import com.fitlens.backend.mappers.WorkoutExerciseResultMapper;
import com.fitlens.backend.repositories.WorkoutDayExerciseRepository;
import com.fitlens.backend.repositories.WorkoutExerciseResultRepository;
import com.fitlens.backend.repositories.WorkoutSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkoutExerciseResultService {

    private final WorkoutExerciseResultRepository workoutExerciseResultRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final WorkoutDayExerciseRepository workoutDayExerciseRepository;
    private final WorkoutExerciseResultMapper workoutExerciseResultMapper;


    public ExerciseResultSummaryResponse submitExerciseResult(WorkoutExerciseResultRequest request){
        log.info("Saving workout result for session: {}", request.getSessionId());

        List<Double> calculatedAccuracyScores = new ArrayList<>();
        double avgAccuracyScore = 0.0;

        if (request.getRepsCount() != null && request.getCorrectReps() != null &&
                request.getRepsCount().size() == request.getCorrectReps().size()) {

            double totalScore = 0.0;
            int numberOfSets = request.getRepsCount().size();

            for (int i = 0; i < numberOfSets; i++) {
                int totalReps = request.getRepsCount().get(i);
                int correctReps = request.getCorrectReps().get(i);

                double setAccuracy = 0.0;
                if (totalReps > 0) {
                    setAccuracy = ((double) correctReps / totalReps) * 100.0;
                }

                calculatedAccuracyScores.add(setAccuracy);
                totalScore += setAccuracy;
            }

            if (numberOfSets > 0) {
                avgAccuracyScore = totalScore / numberOfSets;
            }

        } else {
            log.warn("Mismatch or null values in repsCount and correctReps arrays for session: {}", request.getSessionId());
        }

        var entity = workoutExerciseResultMapper.toEntity(request);
        entity.setAvgAccuracyScore(avgAccuracyScore);
        entity.setAccuracyScoresPerSet(calculatedAccuracyScores);


        var session = workoutSessionRepository.findById(request.getSessionId()).orElseThrow(()->new EntityNotFoundException("Session not found"));
        var workoutDayExercise = workoutDayExerciseRepository.findById(request.getWorkoutDayExerciseId()).orElseThrow(()->new EntityNotFoundException("Workout day exercise not found"));

        entity.setWorkoutSession(session);
        entity.setWorkoutDayExercise(workoutDayExercise);

        var savedResult = workoutExerciseResultRepository.save(entity);

        var response = workoutExerciseResultMapper.toSummaryResponse(savedResult);

        if (savedResult.getRepsCount() != null) {
            response.setSetsCount(savedResult.getRepsCount().size());
        }

        return response;
    }

    @Transactional(readOnly = true)
    public List<ExerciseResultSummaryResponse> getSessionSummary(Long sessionId) {
        log.info("Fetching workout results summary for session ID: {}", sessionId);

        var results = workoutExerciseResultRepository.findByWorkoutSessionId(sessionId);

        return results.stream()
                .map(entity -> {
                    var response = workoutExerciseResultMapper.toSummaryResponse(entity);

                    if (entity.getRepsCount() != null) {
                        response.setSetsCount(entity.getRepsCount().size());
                    } else {
                        response.setSetsCount(0);
                    }

                    return response;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public ExerciseResultDetailsResponse getExerciseDetails(Long resultId) {
        log.info("Fetching workout exercise details for result ID: {}", resultId);

        var entity = workoutExerciseResultRepository.findById(resultId)
                .orElseThrow(() -> {
                    log.error("Exercise result not found with ID: {}", resultId);
                    return new EntityNotFoundException("Exercise result not found with ID: " + resultId);
                });

        var response = workoutExerciseResultMapper.toDetailsResponse(entity);

        log.info("Successfully fetched details for exercise result ID: {}", resultId);

        return response;
    }


}
