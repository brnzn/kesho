package com.kesho.datamart.service;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.paging.PageRequest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/19/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentServiceTest {
    @Test
    public void shouldReturnErrorPageIfSizeIsNegative() {
        StudentService service = new StudentServiceImpl();
        Page<StudentDto> page = service.getPage(new PageRequest(0, -1));
        assertThat(page.hasError(), is(true));
        assertThat(page.getErrorMessage(), is("Page size cannot be negative"));
    }

    @Test
    public void shouldReturnErrorPageIfSizeIsNull() {

    }

    @Test
    public void shouldReturnErrorPageIfPageNumberIsNegative() {

    }

    @Test
    public void shouldReturnErrorPageIfPageNumberIsNull() {

    }

    @Test
    public void shouldReturnErrorPageIfPageSizeIsMoreThat50() {

    }

}
