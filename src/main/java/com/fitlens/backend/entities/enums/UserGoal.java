package com.fitlens.backend.entities.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public enum UserGoal {

	LOSE_WEIGHT("Lose Weight"), GAIN_WEIGHT("Gain Weight"), BUILD_MUSCLE("Build Muscle");

	private final String displayName;

}
