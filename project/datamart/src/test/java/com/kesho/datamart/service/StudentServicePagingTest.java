package com.kesho.datamart.service;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.paging.Request;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/19/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Theories.class)
public class StudentServicePagingTest {
    @DataPoints
    public static Scenario[] scenarios = {
        new Scenario(new Request(0, -1), "Page size must be greater than zero"),
        new Scenario(new Request(0, null), "Page size must be greater than zero"),
        new Scenario(new Request(0, 0), "Page size must be greater than zero"),
        new Scenario(new Request(-1, 1), "Page number cannot be negative"),
        new Scenario(new Request(null, 1), "Page number must be zero or greater"),
        new Scenario(new Request(1, 51), "Page size cannot exceed 50"),
    };

    private static class Scenario {
        final Request request;
        final String expectedMsgs[];

        public Scenario(Request request, String ...expectedMsg) {
            this.expectedMsgs = expectedMsg;
            this.request = request;
        }
    }

    @Theory
    public void testScenario(Scenario scenario) {
        StudentService service = new StudentServiceImpl();
        Page<StudentDto> page = service.getPage(scenario.request);
        assertThat(page.isError(), is(true));
        assertThat(page.getErrors(), containsInAnyOrder(scenario.expectedMsgs));
    }
}
