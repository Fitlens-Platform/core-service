package com.fitlens.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

	private final RedisTemplate<String, Object> redisTemplate;

	private static final String TOKEN_BLACKLIST_PREFIX = "blacklist:token:";

	private static final String REFRESH_TOKEN_PREFIX = "refresh:token:";

	private static final String USER_SESSION_PREFIX = "session:user:";

	// Token Blacklist
	public void blacklistToken(String token, long expirationMs) {
		String key = TOKEN_BLACKLIST_PREFIX + token;
		redisTemplate.opsForValue().set(key, "blacklisted", expirationMs, TimeUnit.MILLISECONDS);
		log.info("Token blacklisted: {}", token.substring(0, 20) + "...");
	}

	public boolean isTokenBlacklisted(String token) {
		String key = TOKEN_BLACKLIST_PREFIX + token;
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	// Refresh Token Management
	public void saveRefreshToken(Long userId, String refreshToken, long expirationMs) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		redisTemplate.opsForValue().set(key, refreshToken, expirationMs, TimeUnit.MILLISECONDS);
		log.info("Refresh token saved for user: {}", userId);
	}

	public String getRefreshToken(Long userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		return (String) redisTemplate.opsForValue().get(key);
	}

	public void deleteRefreshToken(Long userId) {
		String key = REFRESH_TOKEN_PREFIX + userId;
		redisTemplate.delete(key);
		log.info("Refresh token deleted for user: {}", userId);
	}

	// Active Session Tracking
	public void trackUserSession(Long userId, String token, long expirationMs) {
		String key = USER_SESSION_PREFIX + userId;
		redisTemplate.opsForValue().set(key, token, expirationMs, TimeUnit.MILLISECONDS);
	}

	public void invalidateUserSession(Long userId) {
		String key = USER_SESSION_PREFIX + userId;
		redisTemplate.delete(key);
	}

	public boolean hasActiveSession(Long userId) {
		String key = USER_SESSION_PREFIX + userId;
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

}
