package com.fitlens.backend.entities;

import com.fitlens.backend.entities.enums.FitnessLevel;
import com.fitlens.backend.entities.enums.UserGoal;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "user_history", indexes = { @Index(name = "idx_user_history_user_id", columnList = "user_id") })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
public class CustomerHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@Enumerated(EnumType.STRING)
	private FitnessLevel fitnessLevel;

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

	@Column(nullable = false)
	@NotNull
	@Min(value = 1)
	@Max(value = 7)
	private int trainingDays;

}
