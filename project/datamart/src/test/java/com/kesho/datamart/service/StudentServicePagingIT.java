package com.kesho.datamart.service;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
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

    }

    @Test
    public void shouldReturnLastPageIfExceededPages() {

    }

}
