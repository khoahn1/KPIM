package com.fsoft.khoahn.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.fsoft.khoahn.common.Constants;
import com.fsoft.khoahn.common.annotation.constraints.NumbericCheck;
import com.fsoft.khoahn.common.support.ValidationSupport;

public class NumbericCheckValidator implements ConstraintValidator<NumbericCheck, String> {
	@Override
	public void initialize(NumbericCheck constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		boolean result = true;
		if (StringUtils.isNotEmpty(value)) {
			value = value.replace(Constants.SPACE_CHARACTER, Constants.EMPTY_STRING);
			result = ValidationSupport.checkAlphaNumberic(value);
		}

		return result;
	}

}
