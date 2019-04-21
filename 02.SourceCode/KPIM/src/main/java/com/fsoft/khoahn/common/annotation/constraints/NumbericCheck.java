package com.fsoft.khoahn.common.annotation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fsoft.khoahn.common.annotation.NumbericCheckValidator;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
@Constraint(validatedBy = NumbericCheckValidator.class)
public @interface NumbericCheck {

	String message() default "field.invalid.check.numberic";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
