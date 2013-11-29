package com.kesho.datamart.service;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.paging.Request;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/19/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = { "classpath:datamart-service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentServicePagingIT {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "students-paging-it-data.xml");

    @Inject
    private StudentService studentService;

    @Test
    public void shouldReturnErrorPageIfPageNumberIsNegative() {
        Page<StudentDto> page = studentService.getPage(new Request(-1, 1));
        assertThat("Expected error page",page.isError(), is(true));
        assertThat(page.getErrors().get(0), is("Page number cannot be negative"));
    }

    @Test
    public void shouldReturnEmptyPageIfExceededPages() {
        Page<StudentDto> page = studentService.getPage(new Request(4, 3));
        assertThat(page.getTotalPages(), is(4));
        assertThat(page.getSize(), is(0));
        assertThat("Expect error page", page.isError(), is(true));
        assertThat(page.getErrors().get(0), is("Max pages is [4]"));
    }

    @Test
    public void shouldReturnLastPage() {
        Page<StudentDto> page = studentService.getPage(new Request(3, 3));
        assertThat(page.getTotalPages(), is(4));
        assertThat(page.getSize(), is(1));
    }

}
