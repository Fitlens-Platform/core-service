package com.fitlens.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "workout_exercise_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutExerciseResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private WorkoutSession workoutSession;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_day_exercise_id", nullable = false)
    private WorkoutDayExercise workoutDayExercise;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "reps_count", columnDefinition = "jsonb")
    private List<Integer> repsCount;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "correct_reps", columnDefinition = "jsonb")
    private List<Integer> correctReps;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "duration_seconds", columnDefinition = "jsonb")
    private List<Integer> durationSeconds;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "weight_used", columnDefinition = "jsonb")
    private List<Double> weightUsed;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "ai_feedback", columnDefinition = "jsonb")
    private List<String> aiFeedback;

    @Column(name = "avg_accuracy_score")
    private Double avgAccuracyScore;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "accuracy_scores_per_set", columnDefinition = "jsonb")
    private List<Double> accuracyScoresPerSet;
}
