package com.fsoft.khoahn.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.fsoft.khoahn.common.annotation.constraints.EmailCheck;
import com.fsoft.khoahn.common.support.ValidationSupport;

@Component
public class EmailCheckValidator implements ConstraintValidator<EmailCheck, String> {

	@Override
	public void initialize(EmailCheck constraintAnnotation) {

	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		boolean result = true;
		if (email != null && !email.isEmpty()) {
			result = ValidationSupport.isEmailValid(email);
		}
		return result;
	}

}
