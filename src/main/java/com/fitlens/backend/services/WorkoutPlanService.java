package com.fitlens.backend.services;

import com.fitlens.backend.dto.PagedResponse;
import com.fitlens.backend.dto.workoutplan.WorkoutPlanFilter;
import com.fitlens.backend.dto.workoutplan.CreateWorkoutPlanRequest;
import com.fitlens.backend.dto.workoutplan.WorkoutPlanResponse;
import com.fitlens.backend.mappers.WorkoutPlanMapper;
import com.fitlens.backend.repositories.WorkoutPlanRepository;
import com.fitlens.backend.specifications.WorkoutPlanSpecification;
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
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;

    private final WorkoutPlanMapper workoutPlanMapper;

    public PagedResponse<WorkoutPlanResponse> getAllPlans(WorkoutPlanFilter filter){

        log.info("Fetching workout plans from database.");

        var specification = WorkoutPlanSpecification.buildSpecification(filter);
        var sort = SortUtils.buildSort(filter.getSortBy(), filter.getSortDirection());
        var pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

        var workoutPlanPage =  workoutPlanRepository.findAll(specification,pageable);

        var plans = workoutPlanPage.getContent().stream().map(workoutPlanMapper::toPlanResponse).toList();

        log.info("Fetched {} workout plans successfully", plans.size());

        return PagedResponse.<WorkoutPlanResponse>builder()
                .content(plans)
                .totalElements(workoutPlanPage.getTotalElements())
                .totalPages(workoutPlanPage.getTotalPages())
                .currentPage(workoutPlanPage.getNumber())
                .pageSize(workoutPlanPage.getSize())
                .hasNext(workoutPlanPage.hasNext())
                .hasPrevious(workoutPlanPage.hasPrevious())
                .build();
    }

    public WorkoutPlanResponse addWorkoutPlan(CreateWorkoutPlanRequest request){

        log.info("Adding new workout plan: {}", request.getName());

        var workoutPlan = workoutPlanMapper.toPlanEntity(request);

        if (workoutPlan.getDays() != null) {
            workoutPlan.getDays().forEach(day -> day.setPlan(workoutPlan));
        }

        var savedPlan = workoutPlanRepository.save(workoutPlan);
        log.info("Workout plan '{}' added successfully with ID: {}",
                savedPlan.getName(), savedPlan.getId());

        return workoutPlanMapper.toPlanResponse(savedPlan);
    }


}
