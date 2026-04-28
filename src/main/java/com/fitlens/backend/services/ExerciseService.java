package com.fitlens.backend.services;


import com.fitlens.backend.dto.PagedResponse;
import com.fitlens.backend.dto.exercise.CreateExerciseRequest;
import com.fitlens.backend.dto.exercise.ExerciseFilter;
import com.fitlens.backend.dto.exercise.ExerciseResponse;
import com.fitlens.backend.mappers.ExerciseMapper;
import com.fitlens.backend.repositories.ExerciseRepository;
import com.fitlens.backend.specifications.ExerciseSpecification;
import com.fitlens.backend.utils.SortUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final ExerciseMapper exerciseMapper;

    @Transactional
    public PagedResponse<ExerciseResponse> getAllExercises(ExerciseFilter filter){

        log.info("Fetching exercises from database.");

        var specification = ExerciseSpecification.buildSpecification(filter);
        var sort = SortUtils.buildSort(filter.getSortBy(), filter.getSortDirection());
        var pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        var exercisePage = exerciseRepository.findAll(specification,pageable);

        var exercises = exercisePage.getContent().stream().map(exerciseMapper::toResponse).toList();

        log.info("Fetched {} exercises successfully", exercises.size());

        return PagedResponse.<ExerciseResponse>builder()
                .content(exercises)
                .totalElements(exercisePage.getTotalElements())
                .totalPages(exercisePage.getTotalPages())
                .currentPage(exercisePage.getNumber())
                .pageSize(exercisePage.getSize())
                .hasNext(exercisePage.hasNext())
                .hasPrevious(exercisePage.hasPrevious())
                .build();

    }

    @Transactional
    public ExerciseResponse addExercise(CreateExerciseRequest request){

        log.info("Adding {} exercise", request.getName());

        var exercise = exerciseMapper.toEntity(request);
        var savedExercise = exerciseRepository.save(exercise);

        log.info("Exercise '{}' added successfully with ID: {}", savedExercise.getName(), savedExercise.getId());

        return exerciseMapper.toResponse(savedExercise);
    }
}
