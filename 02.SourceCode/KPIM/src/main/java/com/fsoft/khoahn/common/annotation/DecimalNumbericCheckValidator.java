package com.fsoft.khoahn.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.fsoft.khoahn.common.annotation.constraints.DecimalNumbericCheck;
import com.fsoft.khoahn.common.support.ValidationSupport;

public class DecimalNumbericCheckValidator implements ConstraintValidator<DecimalNumbericCheck, String> {

    @Override
    public void initialize(DecimalNumbericCheck constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = true;
        if (StringUtils.isNotEmpty(value)) {
            result = ValidationSupport.checkDecimalNumberic(value);
        }
        return result;
    }

}
