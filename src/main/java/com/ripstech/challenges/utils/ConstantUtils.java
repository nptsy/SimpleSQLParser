package com.ripstech.challenges.utils;

public class ConstantUtils {

	public static boolean isQuote(String input) {
		boolean isTrue = (input.startsWith("\"") && input.endsWith("\""))
				|| ((input.startsWith("'")) && (input.endsWith("'")));

		return isTrue;
	}

	public static boolean isNumber(String input) {
		return input.matches("-?\\d+(\\.\\d+)?");
	}

}
