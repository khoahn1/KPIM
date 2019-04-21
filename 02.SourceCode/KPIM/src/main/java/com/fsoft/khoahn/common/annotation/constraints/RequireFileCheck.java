package com.fsoft.khoahn.common.annotation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fsoft.khoahn.common.annotation.RequireFileCheckValidator;

@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { RequireFileCheckValidator.class })
public @interface RequireFileCheck {

	String message() default "field.invalid.check.file.upload.required";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}