package com.kesho.datamart.ui.repository;

import com.google.common.collect.Lists;
import com.kesho.datamart.domain.EducationStatus;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.service.StudentService;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.argThat;
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
public class StudentsRepositoryTest {
    @InjectMocks
    private StudentsRepositoryImpl studentsRepository;
    @Mock
    private StudentService studentService;
    @Mock
    private Page<StudentDto> page;

    @Test
    public void shouldSaveStudent() {
        StudentDto dto = new StudentDto();
        when(studentService.save(dto)).thenReturn(new StudentDto().withId(1L));

        StudentDto result = studentsRepository.save(dto);
        assertThat(result.getId(), is(1L));

    }

    @Test
    public void shouldGetPage() {
        when(studentService.getPage(argThat(new TypeSafeMatcher<Request>() {
            @Override
            protected boolean matchesSafely(Request request) {
                return (request.getPageNumber() == 0 && request.getPageSize() == 10);
            }

            @Override
            public void describeTo(Description description) {
            }
        }))).thenReturn(page);

        Page<StudentDto> result = studentsRepository.getPage(0, 10);

       assertThat(result, is(page));
    }

    @Test
    public  void shouldReturnStudent() {
        StudentDto dto = mock(StudentDto.class);
        when(studentService.get(1L)).thenReturn(dto);
        StudentDto result = studentsRepository.findOne(1L);
        assertThat(result, is(dto));
    }

    @Test
    public void shouldAddEducationHistory() {
        EducationDto dto = mock(EducationDto.class);
        EducationDto returnDto = mock(EducationDto.class);
        when(returnDto.getId()).thenReturn(1L);
        when(studentService.addEducationHistory(dto)).thenReturn(returnDto);

        EducationDto result = studentsRepository.addEducationHistory(dto);
        assertThat(result.getId(), is(1L));
    }

    @Test
    public void shouldGetStudentEducationHistory() {
        EducationDto dto1 = new EducationDto(EducationStatus.College, null);
        EducationDto dto2 = new EducationDto(EducationStatus.Nursery, null);
        List<EducationDto> educations = Lists.newArrayList(dto1, dto2);

        when(studentService.getEducationHistory(1L)).thenReturn(educations);

        List<EducationDto> result = studentsRepository.getEducationHistory(1L);

        assertThat(result, contains(dto1, dto2));
    }

    @Test
    public void shouldSaveEducation() {
        EducationDto dto = mock(EducationDto.class);
        EducationDto returnDto = mock(EducationDto.class);
        when(returnDto.getId()).thenReturn(1L);
        when(studentService.save(dto)).thenReturn(returnDto);

        EducationDto result = studentsRepository.save(dto);
        assertThat(result.getId(), is(1L));

    }

    @Test
    public void shouldFindLatestEducation() {
        EducationDto dto = mock(EducationDto.class);
        when(dto.getId()).thenReturn(1L);
        when(studentService.findLatestEducation(1L)).thenReturn(dto);

        EducationDto latest = studentsRepository.findLatestEducation(1L);

        assertThat(latest.getId(), is(1L));
    }
}
