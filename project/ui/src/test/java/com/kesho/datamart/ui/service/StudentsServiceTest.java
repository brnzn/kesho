package com.kesho.datamart.ui.service;

import com.google.common.collect.Lists;
import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.repository.StudentsRepository;
import com.kesho.datamart.service.StudentService;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 10:29 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentsServiceTest {
    @InjectMocks
    private StudentsFacadeImpl studentsService;
    @Mock
    private StudentService studentService;

    @Test
    public  void shouldReturnListOfStudents() {
        List<StudentDto> studentDtos = Lists.newArrayList(mock(StudentDto.class), mock(StudentDto.class));

        when(studentsService.findAll()).thenReturn(studentDtos);
        List<StudentDto> result = studentsService.findAll();
        assertThat(studentDtos.size(), is(2));
    }
}
