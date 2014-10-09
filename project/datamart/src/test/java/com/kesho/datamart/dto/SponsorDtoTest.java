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
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class SponsorDtoTest {
    @Test
    public void shouldValidateDto() {
        SponsorDto dto = new SponsorDto();

        ValidationResult validations = ValidationUtil.validateNew(dto);

        Map<String, String> violations = validations.getViolations();
        assertThat(violations.get("name"), is("Name is mandatory"));
        assertThat(violations.get("surname"), is("Family Name is mandatory"));
    }

}
