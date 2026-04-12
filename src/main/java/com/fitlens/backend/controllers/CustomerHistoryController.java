package com.fitlens.backend.controllers;

import com.fitlens.backend.dto.PagedResponse;
import com.fitlens.backend.dto.customerhistory.CustomerHistoryFilter;
import com.fitlens.backend.dto.customerhistory.CustomerHistoryResponse;
import com.fitlens.backend.services.CustomerHistoryService;
import com.fitlens.backend.utils.AuthenticationHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user-history")
@RequiredArgsConstructor
@Tag(name = "User history",
		description = "Endpoints for tracking physical progress history")
@SecurityRequirement(name = "bearerAuth")
public class CustomerHistoryController {

	private final CustomerHistoryService customerHistoryService;

	@GetMapping
	@Operation(summary = "Get User Physical History", description = """
			Retrieve paginated physical progress history (weight, height, etc.) for a specific user.

			**Filtering:**
			- Filter by date range: `?from=2026-01-01T00:00:00Z&to=2026-04-10T23:59:59Z`

			**Date Format (UTC Required):**
			- All dates must be in ISO-8601 UTC format (e.g., `2026-04-10T17:00:00Z`).
			- If no dates are provided, the API returns the full history.

			**Sorting:**
			- Default sorting: `createdAt` DESC (Newest first).
			- Custom sorting: Use `?sortBy=weightKg&sortDirection=ASC`.
			""")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Progress history retrieved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request parameters or date format"),
			@ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource"),
			@ApiResponse(responseCode = "403", description = "You don't have permission to access this user's history"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	public ResponseEntity<PagedResponse<CustomerHistoryResponse>> getAllHistory(
			@AuthenticationPrincipal Map<String, Object> principal, CustomerHistoryFilter filter) {

		var customerId = AuthenticationHelper.extractCustomerId(principal);

		log.debug("Get customer history for customer: {}", customerId);

		var response = customerHistoryService.getAllHistory(customerId, filter);

		return ResponseEntity.ok(response);

	}

}
