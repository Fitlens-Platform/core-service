package com.fitlens.backend.specifications;

import com.fitlens.backend.dto.workout_session.WorkoutSessionFilter;
import com.fitlens.backend.entities.WorkoutSession;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;

@UtilityClass
public class WorkoutSessionSpecification {
    private static Specification<WorkoutSession> hasCustomerId(Long customerId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), customerId));
    }

    private static Specification<WorkoutSession> createdBetween(Instant from, Instant to) {
        return (root, query, builder) -> {
            var predicates = new ArrayList<Predicate>();

            if (from != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), from));
            }

            if (to != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), to));
            }

            if (predicates.isEmpty()) {
                return null;
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<WorkoutSession> buildSpecification(Long customerId, WorkoutSessionFilter filter) {
        if (filter == null) {
            return null;
        }

        return Specification.<WorkoutSession>unrestricted()
                .and(hasCustomerId(customerId))
                .and(createdBetween(filter.getCreatedFrom(), filter.getCreatedTo()));
    }
}
