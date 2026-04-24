package com.fitlens.backend.controllers;

import com.fitlens.backend.dto.customer.CustomerResponse;
import com.fitlens.backend.dto.customer.CustomerUpdateRequest;
import com.fitlens.backend.services.CustomerService;
import com.fitlens.backend.utils.AuthenticationHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/customer")
@RequiredArgsConstructor
@Tag(name = "Customer",
		description = "Endpoints for managing user profiles, fitness metrics, and personal account settings for FitLens users")
@SecurityRequirement(name = "bearerAuth")
public class CustomerController {

	private final CustomerService customerService;

	@GetMapping("/profile")
	@Operation(summary = "Get Current Customer Profile",
			description = "Retrieves the detailed profile information for the currently authenticated customer.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Profile data retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Access token is missing or invalid"),
			@ApiResponse(responseCode = "404",
					description = "Customer not found - The authenticated user record does not exist"),
			@ApiResponse(responseCode = "500",
					description = "Internal Server Error - Something went wrong on the server") })
	public ResponseEntity<CustomerResponse> getProfile(@RequestHeader("X-User-Id") Long customerId)	{
		log.debug("Get profile request: customerId={}", customerId);

		var profile = customerService.getCustomerProfile(customerId);
		return ResponseEntity.ok(profile);
	}

	@PatchMapping("/profile")
	public ResponseEntity<CustomerResponse> updateProfile(@RequestHeader("X-User-Id") Long customerId,
			@RequestBody @Valid CustomerUpdateRequest request) {
		log.info("Update customer profile id: {}", customerId);
		var response = customerService.updateProfile(customerId, request);
		return ResponseEntity.ok(response);
	}

}
