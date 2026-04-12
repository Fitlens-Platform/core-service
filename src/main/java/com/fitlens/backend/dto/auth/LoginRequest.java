package com.fitlens.backend.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to login a customer")
public class LoginRequest {

	@NotBlank(message = "Email is required")
	@Email(message = "Please provide a valid email address")
	@Schema(description = "Customer's email address", example = "mahmoud.samir@example.com",
			requiredMode = Schema.RequiredMode.REQUIRED)
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
			message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
	@Schema(description = "User's account password", example = "StrongP@ss123",
			requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.WRITE_ONLY)
	private String password;

}
