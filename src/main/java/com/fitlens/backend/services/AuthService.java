package com.fitlens.backend.services;

import com.fitlens.backend.config.security.jwt.JwtTokenProvider;
import com.fitlens.backend.dto.auth.AuthResponse;
import com.fitlens.backend.dto.auth.LoginRequest;
import com.fitlens.backend.dto.auth.SignUpRequest;
import com.fitlens.backend.entities.User;
import com.fitlens.backend.entities.enums.UserRole;
import com.fitlens.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	private final CustomerHistoryService customerHistoryService;

	private final CustomerService customerService;

	private final PasswordEncoder passwordEncoder;

	private final JwtTokenProvider tokenProvider;

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;

	@Value("${jwt.access-token-expiration:28800000}")
	private long jwtExpirationMs;

	@Value("${jwt.refresh-token-expiration:604800000}")
	private long refreshExpirationMs;

	@Transactional
	public AuthResponse register(SignUpRequest request) {
		log.info("Attempting to register a new user with email: {}", request.getEmail());
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already registered");
		}

		User user = User.builder()
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.firstName(request.getFirstName())
			.lastName(request.getLastName())
			.goal(request.getGoal())
			.birthDate(request.getBirthDate())
			.gender(request.getGender())
			.trainingDays(request.getTrainingDays())
			.heightCm(request.getHeightCm())
			.weightKg(request.getWeightKg())
			.isActive(true)
			.role(UserRole.USER)
			.build();

		user.setDailyCalories(customerService.calculateCalories(user));
		User savedUser = userRepository.save(user);
		log.info("User saved successfully with ID: {}, email: {}", savedUser.getId(), savedUser.getEmail());

		customerHistoryService.saveCustomerHistorySnapshot(savedUser);

		Authentication authentication = authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		String accessToken = tokenProvider.generateToken(authentication, savedUser.getId());
		String refreshToken = tokenProvider.generateRefreshToken(savedUser.getEmail(), savedUser.getId());

		// Save refresh token in Redis
		tokenService.saveRefreshToken(savedUser.getId(), refreshToken, refreshExpirationMs);

		// Track session
		tokenService.trackUserSession(savedUser.getId(), accessToken, jwtExpirationMs);

		return AuthResponse.builder()
			.token(accessToken)
			.refreshToken(refreshToken)
			.type("Bearer")
			.userId(savedUser.getId())
			.email(savedUser.getEmail())
			.firstName(savedUser.getFirstName())
			.lastName(savedUser.getLastName())
			.expiresIn(jwtExpirationMs / 1000) // in seconds
			.build();
	}

	public AuthResponse login(LoginRequest request) {
		Authentication authentication = authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new RuntimeException("User not found"));

		String accessToken = tokenProvider.generateToken(authentication, user.getId());

		String refreshToken = tokenProvider.generateRefreshToken(user.getEmail(), user.getId());

		// Save refresh token in Redis
		tokenService.saveRefreshToken(user.getId(), refreshToken, refreshExpirationMs);

		// Track session
		tokenService.trackUserSession(user.getId(), accessToken, jwtExpirationMs);

		return AuthResponse.builder()
			.token(accessToken)
			.refreshToken(refreshToken)
			.type("Bearer")
			.userId(user.getId())
			.email(user.getEmail())
			.firstName(user.getFirstName())
			.lastName(user.getLastName())
			.expiresIn(jwtExpirationMs / 1000)
			.build();
	}

	public void logout(String token, Long userId) {
		// Blacklist current token
		long expirationMs = tokenProvider.getExpirationMs(token);
		tokenService.blacklistToken(token, expirationMs);

		// Delete refresh token
		tokenService.deleteRefreshToken(userId);

		// Invalidate session
		tokenService.invalidateUserSession(userId);
	}

	public AuthResponse refreshToken(String refreshToken) {
		if (!tokenProvider.validateToken(refreshToken)) {
			throw new RuntimeException("Invalid refresh token");
		}

		String username = tokenProvider.getUsernameFromToken(refreshToken);
		User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

		// Verify refresh token matches stored one
		String storedRefreshToken = tokenService.getRefreshToken(user.getId());
		if (!refreshToken.equals(storedRefreshToken)) {
			throw new RuntimeException("Refresh token mismatch");
		}

		// Generate new access token
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, null);
		String newAccessToken = tokenProvider.generateToken(authentication, user.getId());

		// Track new session
		tokenService.trackUserSession(user.getId(), newAccessToken, jwtExpirationMs);

		return AuthResponse.builder()
			.token(newAccessToken)
			.refreshToken(refreshToken) // Same refresh token
			.type("Bearer")
			.userId(user.getId())
			.email(user.getEmail())
			.firstName(user.getFirstName())
			.lastName(user.getLastName())
			.expiresIn(jwtExpirationMs / 1000)
			.build();
	}

}