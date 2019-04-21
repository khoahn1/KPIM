package com.fsoft.khoahn.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fsoft.khoahn.common.annotation.constraints.RequireCheck;
import com.fsoft.khoahn.common.support.ValidationSupport;

public class RequireCheckValidator implements ConstraintValidator<RequireCheck, Object> {

	@Override
	public void initialize(RequireCheck constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return !ValidationSupport.isBlank(value);
	}

}
