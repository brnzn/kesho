package com.kesho.datamart.service;

import com.kesho.datamart.domain.ValidationResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/23/13
 * Time: 8:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> List<String> validate(T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);

        List<String> errors = null;
        if (!violations.isEmpty()) {
            errors = new ArrayList<String>();
            for (ConstraintViolation<T> violation:violations) {
                errors.add(violation.getMessage());
            }
        }
        return errors;
    }

    public static <T> ValidationResult validateNew(T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);

        ValidationResult result = new ValidationResult();
        for (ConstraintViolation<T> violation:violations) {
            result.add(violation.getMessage(), violation.getPropertyPath().toString());
        }

        return result;
    }
}
