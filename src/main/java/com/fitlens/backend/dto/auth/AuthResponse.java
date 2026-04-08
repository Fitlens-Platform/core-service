package com.fitlens.backend.dto.auth;

import com.fitlens.backend.dto.customer.CustomerResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Authentication response")

public class AuthResponse {

	@Builder.Default
	private String type = "Bearer";

	private String token;

	private String refreshToken;

	private Long userId;

	private String email;

	private String firstName;

	private String lastName;

	private Long expiresIn; // in seconds

}
