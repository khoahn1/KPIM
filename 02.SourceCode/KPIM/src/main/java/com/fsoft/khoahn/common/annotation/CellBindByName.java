package com.fsoft.khoahn.common.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Payload;

@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
public @interface CellBindByName {

	String column() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
