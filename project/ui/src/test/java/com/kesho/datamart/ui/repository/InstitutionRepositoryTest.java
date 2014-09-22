package com.kesho.datamart.ui.repository;

import com.google.common.collect.Lists;
import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.service.SchoolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:06 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class InstitutionRepositoryTest {
    @InjectMocks
    private SchoolRepositoryImpl repo;
    @Mock
    private SchoolService schoolService;

//    @Test
//    public void shouldReturnInstitutions() {
//        when(schoolService.getSchools()).thenReturn(Lists.newArrayList(new SchoolDto(1L, "school")));
//
//        List<SchoolDto> institutions = repo.getInstitutions();
//        assertThat(institutions.get(0).getName(), is("school"));
//        assertThat(institutions.get(0).getId(), is(1L));
//    }

    @Test
    public void shouldCreateInstitution() {
        SchoolDto dto = new SchoolDto(null, "school");
        when(schoolService.save(dto)).thenReturn(new SchoolDto(1L, "school"));

        SchoolDto result = repo.create(dto);
        assertThat(result.getId(), is(1L));
        assertThat(result.getName(), is("school"));
    }
}
