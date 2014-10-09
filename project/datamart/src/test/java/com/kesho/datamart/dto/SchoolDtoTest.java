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
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchoolDtoTest {
    @Test
    public void shouldValidateDto() {
        SchoolDto dto = new SchoolDto(1L);

        ValidationResult validations = ValidationUtil.validateNew(dto);

        Map<String, String> violations = validations.getViolations();
        assertThat(violations.get("name"), is("Name is mandatory"));
    }
}
