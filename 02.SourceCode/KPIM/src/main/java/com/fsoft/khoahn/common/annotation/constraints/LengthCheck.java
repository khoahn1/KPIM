package com.fsoft.khoahn.common.annotation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fsoft.khoahn.common.annotation.LengthCheckValidator;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LengthCheckValidator.class)
public @interface LengthCheck {

    long value();

	String message() default "field.invalid.check.length";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
