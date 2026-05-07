package com.fitlens.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "workout_day_exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDayExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id", nullable = false)
    private WorkoutDay workoutDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    @NotNull
    private Integer orderInDay;

    @Column(nullable = false)
    @NotNull
    private Integer sets;

    @Column(nullable = false)
    @NotNull
    private Integer reps;

    @Column(nullable = false)
    @NotNull
    @Builder.Default
    private Integer restSeconds=60;

}