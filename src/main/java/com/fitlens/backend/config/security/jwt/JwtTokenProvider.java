package com.fitlens.backend.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.access-token-expiration:28800000}")
	private long jwtExpirationMs;

	@Value("${jwt.refresh-token-expiration:604800000}")
	private long refreshExpirationMs;

	private SecretKey getSigningKey() {
		byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(Authentication authentication, Long userId) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return generateToken(userDetails.getUsername(), userId, jwtExpirationMs);
	}

	public String generateRefreshToken(String username, Long userId) {
		return generateToken(username, userId, refreshExpirationMs);
	}

	private String generateToken(String username, Long userId, long expirationMs) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expirationMs);

		return Jwts.builder()
			.setSubject(username)
			.claim("userId", userId)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(getSigningKey(), SignatureAlgorithm.HS512)
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

		Object userId = claims.get("userId");
		if (userId instanceof Number) {
			return ((Number) userId).longValue();
		}
		return null;
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public long getExpirationMs(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

		Date expiration = claims.getExpiration();
		return expiration.getTime() - System.currentTimeMillis();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		}
		catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		}
		catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		}
		catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		}
		catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty");
		}
		return false;
	}

}