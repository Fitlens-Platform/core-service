package com.fitlens.backend.entities;

import com.fitlens.backend.entities.enums.MuscleGroup;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "exercise_video")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "muscle_group", length = 50, nullable = false)
    private MuscleGroup muscleGroup;

    @Column(name = "exercise_name", length = 100, nullable = false)
    private String exerciseName;

    @Column(name = "video_path", nullable = false)
    private String videoPath;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}