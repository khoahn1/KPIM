package com.fsoft.khoahn.common.annotation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fsoft.khoahn.common.annotation.EmailCheckValidator;

@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EmailCheckValidator.class })
public @interface EmailCheck {
	String message() default "field.invalid.check.valid.format";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}