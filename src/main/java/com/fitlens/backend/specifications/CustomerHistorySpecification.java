package com.fitlens.backend.specifications;

import com.fitlens.backend.dto.customerhistory.CustomerHistoryFilter;
import com.fitlens.backend.entities.CustomerHistory;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;

@UtilityClass
public class CustomerHistorySpecification {

	private static Specification<CustomerHistory> hasCustomerId(Long customerId) {
		return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), customerId));
	}

	private static Specification<CustomerHistory> createdBetween(Instant from, Instant to) {
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

	public static Specification<CustomerHistory> buildSpecification(Long customerId, CustomerHistoryFilter filter) {
		if (filter == null) {
			return null;
		}

		return Specification.<CustomerHistory>unrestricted()
			.and(hasCustomerId(customerId))
			.and(createdBetween(filter.getCreatedFrom(), filter.getCreatedTo()));
	}

}
