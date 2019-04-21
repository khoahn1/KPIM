package com.fsoft.khoahn.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.fsoft.khoahn.common.annotation.constraints.LengthCheck;
import com.fsoft.khoahn.common.support.ValidationSupport;



public class LengthCheckValidator 
    implements ConstraintValidator<LengthCheck, String> {

    private long length;

    @Override
    public void initialize(LengthCheck constraintAnnotation) {
        this.length = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean result = true;
        if (StringUtils.isNotEmpty(value)) {
			result = ValidationSupport.checkLength(value, this.length);
        }

        return result;
    }
}
