package com.fitlens.backend.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

@UtilityClass
public class SortUtils {

	public static Sort buildSort(String sortBy, String sortDirection) {
		if (sortBy == null || sortBy.isBlank()) {
			return Sort.unsorted();
		}

		var direction = Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.DESC);
		return Sort.by(direction, sortBy);
	}

}
