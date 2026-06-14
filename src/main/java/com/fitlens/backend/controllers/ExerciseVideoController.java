package com.fitlens.backend.controllers;

import com.fitlens.backend.dto.ecercise_video.ExerciseVideoResponse;
import com.fitlens.backend.entities.enums.MuscleGroup;
import com.fitlens.backend.services.ExerciseVideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workout/")
@Slf4j
public class ExerciseVideoController {

    private final ExerciseVideoService exerciseVideoService;

    @GetMapping("/muscle/{muscleGroup}")
    @Operation(
            summary = "Get videos by muscle group",
            description = "Retrieves a list of exercise video URLs and details for a specific muscle group."
    )
    public ResponseEntity<List<ExerciseVideoResponse>> getVideosByMuscleGroup(
            @Parameter(description = "The target muscle group (e.g., BENCH, BACK, LEG)", required = true)
            @PathVariable String muscleGroup) {

        log.info("REST request to fetch videos for muscle group: {}", muscleGroup);

        List<ExerciseVideoResponse> videos = exerciseVideoService.getVideosByMuscle(muscleGroup);

        return ResponseEntity.ok(videos);
    }
}
