package com.fsoft.khoahn.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fsoft.khoahn.common.annotation.constraints.RequireFileCheck;

@Component
public class RequireFileCheckValidator implements ConstraintValidator<RequireFileCheck, MultipartFile> {

	@Override
	public void initialize(RequireFileCheck constraintAnnotation) {

	}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

		boolean result = true;
		if (file == null || file.isEmpty()) {
			result = false;
		}
		return result;
	}

}
