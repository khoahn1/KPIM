package com.fsoft.khoahn.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.fsoft.khoahn.common.annotation.constraints.MinLengthCheck;
import com.fsoft.khoahn.common.support.ValidationSupport;

public class MinLengthCheckValidator implements ConstraintValidator<MinLengthCheck, String> {

	private long min;

	@Override
	public void initialize(MinLengthCheck constraintAnnotation) {
		this.min = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		boolean result = true;
		if (StringUtils.isNotEmpty(value)) {
			result = ValidationSupport.checkMin(value, this.min);
		}

		return result;
	}
}
