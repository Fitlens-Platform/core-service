package com.fitlens.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Component
public class AuthenticationHelper {

	/// Gets the current authentication object from SecurityContextHolder.
	/// This method exists to improve testability by allowing the SecurityContextHolder
	/// access to be mocked.
	///
	/// @return The current Authentication object, or null if not authenticated
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static Map<?, ?> getAuthenticatedPrincipal() {
		var authentication = getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "error.auth.not_authenticated");
		}

		var principal = authentication.getPrincipal();
		if (!(principal instanceof Map)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "error.auth.invalid_principal");
		}

		return (Map<?, ?>) principal;
	}

	public static Long getCurrentUserId() {
		var principal = getAuthenticatedPrincipal();
		return extractCustomerId(principal);
	}

	public static Long extractCustomerId(Map<?, ?> principal) {
		if (principal == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication principal is missing");
		}

		var customerIdObj = principal.get("customerId");
		if (customerIdObj == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
					"Customer ID not found in authentication principal");
		}

		if (customerIdObj instanceof Number) {
			return ((Number) customerIdObj).longValue();
		}
		else if (customerIdObj instanceof String) {
			try {
				return Long.parseLong((String) customerIdObj);
			}
			catch (NumberFormatException e) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
						"Invalid customer ID format in authentication principal");
			}
		}

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
				"Invalid customer ID type in authentication principal");
	}

}