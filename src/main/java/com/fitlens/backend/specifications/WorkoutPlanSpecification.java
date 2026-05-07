package com.fitlens.backend.specifications;

import com.fitlens.backend.dto.workout_plan.WorkoutPlanFilter;
import com.fitlens.backend.entities.WorkoutPlan;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class WorkoutPlanSpecification {

    public static Specification<WorkoutPlan> searchByKeyword(String search){
        return ((root, query, criteriaBuilder) -> {
            if(search == null || search.isBlank()){
                return null;
            }
            var keyword = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),keyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),keyword));
        });
    }

    public static Specification<WorkoutPlan> hasDays(Integer days){
        return ((root, query, criteriaBuilder) -> {
            if(days == null){
                return null;
            }
            return criteriaBuilder.equal(root.get("numberOfDays"),days);
        });
    }

    public static Specification<WorkoutPlan> buildSpecification(WorkoutPlanFilter filter){
        if(filter == null){
            return null;
        }
        return Specification.<WorkoutPlan>unrestricted()
                .and(searchByKeyword(filter.getSearch()))
                .and(hasDays(filter.getNumberOfDays()));
    }

}
