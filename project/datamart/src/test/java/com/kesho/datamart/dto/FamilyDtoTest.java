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
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyDtoTest {
    @Test
    public void shouldValidateDto() {
        FamilyDto dto = new FamilyDto();

        ValidationResult validations = ValidationUtil.validateNew(dto);

        Map<String, String> violations = validations.getViolations();
        assertThat(violations.get("familyName"), is("Name is mandatory"));
        assertThat(violations.get("homeLocation"), is("Home location is mandatory"));
        assertThat(violations.get("homeSubLocation"), is("Home sub location is mandatory"));
        assertThat(violations.get("aliveParents"), is("Number of parents alive is mandatory"));
        assertThat(violations.get("numOfChildrenAtAddress"), is("Number of non Kesho students in the house is mandatory"));
        assertThat(violations.get("numOfAdultsAtAddress"), is("Number of adults in the house is mandatory"));
        assertThat(violations.get("profile"), is("Profile is mandatory"));
    }

    @Test
    public void shouldValidateMin() {
        FamilyDto dto = new FamilyDto()
                .withNumOfWives(-1)
                .withAliveParents(-1)
                .withNumOfAdultsAtAddress(-1)
                .withNumOfChildrenAtAddress(-1);

        ValidationResult validations = ValidationUtil.validateNew(dto);

        Map<String, String> violations = validations.getViolations();
        assertThat(violations.get("numOfWives"), is("Number of wives cannot be negative"));
        assertThat(violations.get("aliveParents"), is("Number of parents alive cannot be negative"));
        assertThat(violations.get("numOfChildrenAtAddress"), is("Number of non Kesho students in the house cannot be negative"));
        assertThat(violations.get("numOfAdultsAtAddress"), is("Number of adults in the house cannot be negative"));
    }

}
