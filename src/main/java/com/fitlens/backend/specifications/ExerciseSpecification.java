package com.fitlens.backend.specifications;

import com.fitlens.backend.dto.exercise.ExerciseFilter;
import com.fitlens.backend.entities.Exercise;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class ExerciseSpecification {

    public static Specification<Exercise> searchByKeyword(String search){
        return ((root, query, criteriaBuilder) -> {
            if(search == null || search.isBlank()){
                return null;
            }
            var keyword = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),keyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("category")),keyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("targetMuscle")),keyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("mechanic")),keyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("mechanic")),keyword),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("shortDescription")),keyword));
        });
    }

    public static Specification<Exercise> buildSpecification(ExerciseFilter filter){
        if(filter == null){
            return null;
        }
        return Specification.<Exercise>unrestricted()
                .and(searchByKeyword(filter.getSearch()));
    }
}
