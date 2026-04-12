package com.fitlens.backend.entities;

import com.fitlens.backend.entities.enums.FitnessLevel;
import com.fitlens.backend.entities.enums.UserGoal;
import com.fitlens.backend.entities.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "users", indexes = { @Index(name = "idx_user_email", columnList = "email") })
@SQLRestriction("deleted = false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotNull
	private String firstName;

	@Column(nullable = false)
	@NotNull
	private String lastName;

	@Column(nullable = false)
	@NotNull
	private String email;

	@Column(nullable = false)
	@NotNull
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NonNull
	private UserRole role;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	@Column(nullable = false)
	private Instant updatedAt;

	@Column(nullable = false)
	@NotNull
	private String gender;

	@Column(nullable = false)
	@NotNull
	private LocalDate birthDate;

	@Column(nullable = false)
	@NotNull
	@Positive
	private BigDecimal heightCm;

	@Column(nullable = false)
	@NotNull
	@Positive
	private BigDecimal weightKg;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@NotNull
	private UserGoal goal;

	@Enumerated(EnumType.STRING)
	private FitnessLevel fitnessLevel;

	@Column(nullable = false)
	@NotNull
	@Min(value = 1)
	@Max(value = 7)
	private int trainingDays;


	private int dailyCalories;

	@Column(name = "is_active", nullable = false)
	@Builder.Default
	private boolean isActive = true;

	@Column(name = "deleted", nullable = false)
	@Builder.Default
	private boolean deleted = false;

}
