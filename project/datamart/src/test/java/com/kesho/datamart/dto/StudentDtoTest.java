package com.kesho.datamart.dto;

import com.kesho.datamart.domain.ValidationResult;
import com.kesho.datamart.service.ValidationUtil;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 10/9/14
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentDtoTest {
    @Test
    public void shouldValidateDto() {
        StudentDto dto = new StudentDto();

        ValidationResult validations = ValidationUtil.validateNew(dto);

        Map<String, String> violations = validations.getViolations();
        assertThat(violations.get("firstName"), is("Name is mandatory"));
        assertThat(violations.get("family"), is("Family is mandatory"));
        assertThat(violations.get("gender"), is("Gender is mandatory"));
        assertThat(violations.get("yearOfBirth"), is("Year Of Birth is mandatory"));

    }
    @Test
    public void shouldValidateMinYearOfBirth() {
        StudentDto dto = new StudentDto().withYearOfBirth(1900);

        ValidationResult validations = ValidationUtil.validateNew(dto);

        Map<String, String> violations = validations.getViolations();
        assertThat(violations.get("yearOfBirth"), is("Year of Birth cannot be less than 1980"));
    }
}
