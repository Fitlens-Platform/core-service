package com.fitlens.backend.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {

	/// Regular expression pattern for names (firstName, lastName).
	/// Allows: letters (A-Z, a-z), spaces, hyphens, apostrophes.
	/// Length enforced separately via @Size annotation.
	public static final String NAME_PATTERN = "^[A-Za-z\\s\\-']+$";

	/// Validation message for invalid name format.
	public static final String NAME_MESSAGE = "Must contain only letters, spaces, hyphens, and apostrophes";

}