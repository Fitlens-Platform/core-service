package com.fitlens.backend.controllers;

import com.fitlens.backend.dto.customer.CustomerResponse;
import com.fitlens.backend.dto.customer.CustomerUpdateRequest;
import com.fitlens.backend.dto.customer.UpdateUserPlanRequest;
import com.fitlens.backend.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

	@Operation(summary = "Update Customer Profile",
			description = "Partially updates the profile information for the authenticated customer (e.g., name, height, weight, goals...etc).")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Profile updated successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Validation failed or invalid input data"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - User ID header is missing or invalid"),
			@ApiResponse(responseCode = "404", description = "Customer not found - The record does not exist"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
	})
	@PatchMapping("/profile")
	public ResponseEntity<CustomerResponse> updateProfile(@RequestHeader("X-User-Id") Long customerId,
			@RequestBody @Valid CustomerUpdateRequest request) {
		log.info("Update customer profile id: {}", customerId);
		var response = customerService.updateProfile(customerId, request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Update Workout Plan",
			description = "Changes the currently assigned workout plan for the customer to a new one based on the provided Plan ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Workout plan updated successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Invalid Plan ID or validation failed"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - User ID header is missing or invalid"),
			@ApiResponse(responseCode = "404", description = "Not Found - Either the Customer or the Workout Plan does not exist"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Something went wrong on the server")
	})
	@PatchMapping("/plan")
	public ResponseEntity<CustomerResponse> updatePlan(@RequestHeader("X-User-Id") Long customerId,
			@RequestBody@Valid UpdateUserPlanRequest request){

		log.info("Request to update workout plan for customer ID: {} to new plan ID: {}",
				customerId, request.getPlanId());
		var response = customerService.updatePlan(customerId,request);

		return ResponseEntity.ok(response);
	}

}
