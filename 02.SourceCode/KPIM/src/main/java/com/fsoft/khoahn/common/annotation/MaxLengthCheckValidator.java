package com.fsoft.khoahn.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.fsoft.khoahn.common.annotation.constraints.MaxLengthCheck;
import com.fsoft.khoahn.common.support.ValidationSupport;

public class MaxLengthCheckValidator implements ConstraintValidator<MaxLengthCheck, String> {

	private long max;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(MaxLengthCheck constraintAnnotation) {
		this.max = constraintAnnotation.max();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		boolean result = true;
		if (StringUtils.isNotEmpty(value)) {
			result = ValidationSupport.checkMax(value, this.max);
		}

		return result;
	}
}
