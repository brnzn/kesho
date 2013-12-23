package com.kesho.datamart.service;

import com.kesho.datamart.paging.Request;

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
public class PageUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static List<String> validate(Request request) {
        Set<ConstraintViolation<Request>> violations = validator.validate(request);

        List<String> errors = null;
        if (!violations.isEmpty()) {
            errors = new ArrayList<String>();
            for (ConstraintViolation<Request> violation:violations) {
                errors.add(violation.getMessage());
            }
        }
        return errors;
    }
}
