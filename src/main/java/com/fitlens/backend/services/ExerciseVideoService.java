package com.fitlens.backend.services;

import com.fitlens.backend.dto.ecercise_video.ExerciseVideoResponse;
import com.fitlens.backend.entities.ExerciseVideo;
import com.fitlens.backend.entities.enums.MuscleGroup;
import com.fitlens.backend.mappers.ExerciseVideoMapper;
import com.fitlens.backend.repositories.ExerciseVideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExerciseVideoService {

    private final ExerciseVideoRepository exerciseVideoRepository;
    private final ExerciseVideoMapper exerciseVideoMapper;

    @Value("${spring.supabase.base-url}")
    private String supabaseBaseUrl;

    @Transactional(readOnly = true)
    public List<ExerciseVideoResponse> getVideosByMuscle(String muscleGroup) {
        log.debug("Fetching exercise videos for muscle group: {}", muscleGroup);

        MuscleGroup muscleGroupEnum = MuscleGroup.valueOf(muscleGroup.toUpperCase());

        return exerciseVideoRepository.findByMuscleGroup(muscleGroupEnum)
                .stream()
                .map(entity -> exerciseVideoMapper.toDto(entity, supabaseBaseUrl))
                .toList();
    }
}
