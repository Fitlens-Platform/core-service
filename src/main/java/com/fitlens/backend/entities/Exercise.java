package com.fitlens.backend.entities;

import com.fitlens.backend.entities.enums.DifficultyLevel;
import com.fitlens.backend.entities.enums.ExerciseCategory;
import com.fitlens.backend.entities.enums.ExerciseMechanic;
import com.fitlens.backend.mappers.converters.StringListConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "exercise", indexes = { @Index(name = "idx_exercise_name", columnList = "name") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode()
@ToString()
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "short_description", columnDefinition = "TEXT", nullable = false)
    private String shortDescription;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ExerciseCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_level", length = 50)
    private DifficultyLevel difficultyLevel;

    @Column(name = "target_muscle", length = 100)
    private String targetMuscle;

    @Convert(converter = StringListConverter.class)
    private List<String> secondaryMuscles;

    @Convert(converter = StringListConverter.class)
    @Column(name = "required_equipments")
    private List<String> requiredEquipments;

    @Column(name = "video_url", length = 512)
    private String videoUrl;

    @Column(name = "tips_and_cautions", columnDefinition = "TEXT")
    private String tipsAndCautions;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ExerciseMechanic mechanic;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

}
