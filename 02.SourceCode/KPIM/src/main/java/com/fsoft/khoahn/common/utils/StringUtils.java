package com.fsoft.khoahn.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	private static final Pattern PATTERN_ALPHA = Pattern.compile("[\\p{Alpha}]*");
	private static final Pattern PATTERN_ALPHA_NUMBERIC = Pattern.compile("[\\p{Alnum}]*");
	private static final Pattern PATTERN_PUNCT = Pattern.compile("[\\p{Punct}]*");
	private static final Pattern PATTERN_NUMBERIC = Pattern.compile("[\\p{Digit}]*");
	public static String setNullToBlank(String string) {
		if (string == null) {
			string = "";
		}
		return string;
	}

	public static boolean isAlpha(final String target) {
		return check(target, PATTERN_ALPHA);
	}

	public static boolean isNumberic(final String target) {
		return check(target, PATTERN_NUMBERIC);
	}

	public static boolean isAlphaNumberic(final String target) {
		return check(target, PATTERN_ALPHA_NUMBERIC);
	}

	public static boolean isPunct(final String target) {
		return check(target, PATTERN_PUNCT);
	}

	public static boolean isAlphaNumbericPunt(final String target) {
		return check(target, PATTERN_ALPHA_NUMBERIC, PATTERN_PUNCT);
	}

	private static boolean check(final String target, final Pattern... patterns) {
		Assert.notNull(target, "value must no be null");
		String temp = target;
		for (final Pattern pattern : patterns) {
			final Matcher matcher = pattern.matcher(temp);
			temp = matcher.replaceAll("");
		}
		return StringUtils.isEmpty(temp);
	}
}
