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
public class ContactDetailDtoTest {
    @Test
    public void shouldValidateDto() {
        ContactDetailDto dto = new ContactDetailDto();

        ValidationResult validations = ValidationUtil.validateNew(dto);

        Map<String, String> violations = validations.getViolations();
        assertThat(violations.get("ownerId"), is("Owner Id is mandatory"));
        assertThat(violations.get("type"), is("Contact type is mandatory"));
        assertThat(violations.get("value"), is("Value is mandatory"));
        assertThat(violations.get("comments"), is("Comments is mandatory"));

    }

    @Test
    public void shouldValidateLength() {
        ContactDetailDto dto = new ContactDetailDto();
        dto.withValue("01234567890123456789012345678901234567891");
        dto.withComments("0123456789012345678901234567890123456789123456");

        ValidationResult validations = ValidationUtil.validateNew(dto);
        Map<String, String> violations = validations.getViolations();

        assertThat(violations.get("value"), is("Value cannot be more than 40 characters"));
        assertThat(violations.get("comments"), is("Comments cannot be more than 45 characters"));
    }
}
