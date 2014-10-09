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
 * Time: 7:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class EducationDtoTest {
    @Test
    public void shouldValidateDto() {
        EducationDto dto = new EducationDto();

        ValidationResult validations = ValidationUtil.validateNew(dto);

        Map<String, String> violations = validations.getViolations();
        assertThat(violations.get("date"), is("Date is mandatory"));
        assertThat(violations.get("educationalStatus"), is("Education Status is mandatory"));
    }

}
