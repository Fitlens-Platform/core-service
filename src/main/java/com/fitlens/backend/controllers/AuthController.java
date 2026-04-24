package com.fitlens.backend.controllers;

import com.fitlens.backend.dto.auth.AuthResponse;
import com.fitlens.backend.dto.auth.LoginRequest;
import com.fitlens.backend.dto.auth.RefreshTokenRequest;
import com.fitlens.backend.dto.auth.SignUpRequest;
import com.fitlens.backend.services.AuthService;
import com.fitlens.backend.utils.AuthenticationHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration, login, and session management")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@Operation(summary = "Register a new user",
			description = "Creates a new user profile in FitLens and returns JWT tokens")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User registered successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "409", description = "Email already exists") })
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody SignUpRequest request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@Operation(summary = "User Login",
			description = "Authenticate with email and password to receive access and refresh tokens")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login successful"),
			@ApiResponse(responseCode = "401", description = "Invalid email or password"),
			@ApiResponse(responseCode = "403", description = "Account is not active"),
			@ApiResponse(responseCode = "429", description = "Too many login attempts. Please try again later.") })
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}

	@Operation(summary = "Logout user",
			description = "Invalidates the current session and clears the refresh token from Redis")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Logged out successfully"),
			@ApiResponse(responseCode = "401", description = "Missing or invalid Authorization header") })
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails) {
		String token = extractTokenFromRequest(request);
		var userId = AuthenticationHelper.getCurrentUserId();
		authService.logout(token, userId);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Refresh Access Token",
			description = "Uses a valid refresh token to generate a new short-lived access token")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
			@ApiResponse(responseCode = "401", description = "Invalid or expired refresh token") })
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
	}

	private String extractTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}