package com.kesho.datamart.service;

import com.google.common.collect.Lists;
import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.entity.School;
import com.kesho.datamart.repository.SchoolsDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class InstitutionServiceTest {
    @Mock
    private SchoolsDAO dao;
    @InjectMocks
    private InstitutionServiceImpl service;

    @Test
    public void shouldCreateInstitution() {
        School school = new School();
        school.setName("school");

        School saved = new School();
        saved.setName("school");
        saved.setId(1L);
        when(dao.save(eq(school))).thenReturn(saved);

        InstitutionDto dto = service.create(new InstitutionDto(null, "school"));
        assertThat(dto.getId(), is(1L));
        assertThat(dto.getName(), is("school"));
    }

    @Test
    public void shouldRetrunInstitutions() {
        when(dao.findAll()).thenReturn(Lists.newArrayList(new School(1L, "s1"), new School(2L, "s2")));

        List<InstitutionDto> institutions = service.getInstitutions();
        assertThat(institutions.size(), is(2));
        assertThat(institutions.get(0).getName(), is("s1"));
        assertThat(institutions.get(0).getId(), is(1L));
        assertThat(institutions.get(1).getName(), is("s2"));
        assertThat(institutions.get(1).getId(), is(2L));


    }
}
