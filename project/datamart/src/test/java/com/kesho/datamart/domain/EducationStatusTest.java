package com.kesho.datamart.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/11/13
 * Time: 6:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class EducationStatusTest {
    @Test
    public void testEducationStatus() {
        assertThat(EducationStatus.Nursery.getChildren(), hasSize(0));

        assertThat(EducationStatus.Primary.getChildren(), containsInAnyOrder(SubEducationStatus.Boarding,
                                                                                SubEducationStatus.Day));

        assertThat(EducationStatus.Secondary.getChildren(), containsInAnyOrder(SubEducationStatus.District,
                                                                                SubEducationStatus.Provincial,
                                                                                SubEducationStatus.National));
        assertThat(EducationStatus.GapSchoolLeaver.getChildren(), hasSize(0));

        assertThat(EducationStatus.College.getChildren(), containsInAnyOrder(SubEducationStatus.Diploma,
                SubEducationStatus.Certifcate,
                SubEducationStatus.Bridging,
                SubEducationStatus.ShortTermCourse));

        assertThat(EducationStatus.University.getChildren(), hasSize(0));

        assertThat(EducationStatus.GapAfterTertiary.getChildren(), hasSize(0));
    }

    @Test
    public void testSubStatuses() {
        assertThat(SubEducationStatus.District.getChildren(), containsInAnyOrder(SubEducationStatus.Day, SubEducationStatus.Boarding));
        assertThat(SubEducationStatus.Boarding.getChildren(), hasSize(0));
        assertThat(SubEducationStatus.Bridging.getChildren(), hasSize(0));
        assertThat(SubEducationStatus.Certifcate.getChildren(), hasSize(0));
        assertThat(SubEducationStatus.Day.getChildren(), hasSize(0));
        assertThat(SubEducationStatus.Diploma.getChildren(), hasSize(0));
        assertThat(SubEducationStatus.National.getChildren(), hasSize(0));
        assertThat(SubEducationStatus.Provincial.getChildren(), hasSize(0));
        assertThat(SubEducationStatus.ShortTermCourse.getChildren(), hasSize(0));

    }
}
