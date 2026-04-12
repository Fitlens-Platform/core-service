package com.fitlens.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated response wrapper")
public class PagedResponse<T> {

	@Schema(description = "List of items for the current page")
	private List<T> content;

	@Schema(description = "Total number of elements across all pages", example = "100")
	private long totalElements;

	@Schema(description = "Total number of pages", example = "5")
	private int totalPages;

	@Schema(description = "Current page number (0-indexed)", example = "0")
	private int currentPage;

	@Schema(description = "Number of items per page", example = "20")
	private int pageSize;

	@Schema(description = "Whether there is a next page", example = "true")
	private boolean hasNext;

	@Schema(description = "Whether there is a previous page", example = "false")
	private boolean hasPrevious;

}
