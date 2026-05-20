package com.fitlens.backend.entities;

import com.fitlens.backend.entities.enums.SessionStatus;
import com.fitlens.backend.entities.enums.SessionThoughts;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "workout_session")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private WorkoutPlan workoutPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    private WorkoutDay workoutDay;

    @Column(nullable = false)
    @Builder.Default
    private LocalDate sessionDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SessionStatus status;

    private Float performanceScore;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private SessionThoughts thoughts;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant startedAt;

    @UpdateTimestamp
    private Instant completedAt;

    private Long durationMinutes;


}
