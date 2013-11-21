package com.kesho.datamart.ui.repository;

import com.google.common.collect.Lists;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.paging.PageRequest;
import com.kesho.datamart.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 10:29 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentsRepositoryTest {
    @InjectMocks
    private StudentsRepositoryImpl studentsService;
    @Mock
    private StudentService studentService;
    @Mock
    private Page<StudentDto> page;

    @Test
    public void shouldGetPage1() {
        when(page.getContent()).thenReturn(Lists.newArrayList(new StudentDto().withId(1L), new StudentDto().withId(2L)));
        when(page.getTotalPages()).thenReturn(1);
        when(studentService.getPage(new PageRequest(0, 10))).thenReturn(page);

        Page<StudentDto> page = studentsService.getPage(0, 10);

        assertThat(page.getTotalPages(), is(1));
    }
//
//    @Test
//    public  void shouldReturnListOfStudents() {
//        List<StudentDto> studentDtos = Lists.newArrayList(mock(StudentDto.class), mock(StudentDto.class));
//
//        when(studentsService.findAll()).thenReturn(studentDtos);
//        List<StudentDto> result = studentsService.findAll();
//        assertThat(studentDtos.size(), is(2));
//    }
}
