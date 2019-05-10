package com.fsoft.khoahn.common.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fsoft.khoahn.common.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationSupport {

	public static boolean isEmailValid(String email) {
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isDateValid(String dateToValidate, String dateFromat) {
		if (dateToValidate == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean isBlank(final String target) {
		return StringUtils.isBlank(target);
	}

	public static boolean isBlank(final Object target) {

		if (target == null) {
			return true;
		}

		if (target instanceof String) {
			return StringUtils.isBlank(String.valueOf(target));
		}

		return false;
	}

	public static boolean checkLength(final String target, final long length) {
		return (StringUtils.length(target) == length);
	}

	public static boolean checkMin(final String target, final long min) {
		return (StringUtils.length(target) >= min);
	}

	public static boolean checkMax(final String target, final long max) {
		return (StringUtils.length(target) <= max);
	}

	public static boolean checkAlpha(final String target) {
		return StringUtils.isAlpha(target);
	}


	public static boolean checkNumberic(final String target) {
		return StringUtils.isNumberic(target);
	}

	public static boolean checkAlphaNumberic(final String target) {
		return StringUtils.isAlphaNumberic(target);
	}

	public static boolean checkPunct(final String target) {
		return StringUtils.isPunct(target);
	}

	public static boolean checkAlphaNumbericPunt(final String target) {
		return StringUtils.isAlphaNumbericPunt(target);
	}

	public static boolean checkDecimalNumberic(final String target) {
	    try {
            Double.parseDouble(target);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
	}

}
