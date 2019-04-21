package com.fsoft.khoahn.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fsoft.khoahn.common.annotation.constraints.DateCheck;
import com.fsoft.khoahn.common.enums.DateTimeFormat;
import com.fsoft.khoahn.common.utils.DateTimeUtils;

@Component
public class DateCheckValidator implements ConstraintValidator<DateCheck, String> {

	private DateTimeFormat format;
	private boolean strick;
	@Override
	public void initialize(DateCheck constraintAnnotation) {
		this.format = constraintAnnotation.format();
		this.strick = constraintAnnotation.strick();
	}

	@Override
	public boolean isValid(String target, ConstraintValidatorContext context) {
		boolean result = true;
		if (StringUtils.isNotEmpty(target)) {
			result = DateTimeUtils.isDate(target, format, strick);
		}
		return result;
	}

}
