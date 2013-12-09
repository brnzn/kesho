package com.kesho.datamart.ui.repository;

import com.google.common.collect.Lists;
import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.service.InstitutionService;
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
    private InstitutionRepositoryImpl repo;
    @Mock
    private InstitutionService institutionService;

    @Test
    public void shouldReturnInstitutions() {
        when(institutionService.getInstitutions()).thenReturn(Lists.newArrayList(new InstitutionDto(1L, "school")));

        List<InstitutionDto> institutions = repo.getInstitutions();
        assertThat(institutions.get(0).getName(), is("school"));
        assertThat(institutions.get(0).getId(), is(1L));
    }

    @Test
    public void shouldCreateInstitution() {
        InstitutionDto dto = new InstitutionDto(null, "school");
        when(institutionService.create(dto)).thenReturn(new InstitutionDto(1L, "school"));

        InstitutionDto result = repo.create(dto);
        assertThat(result.getId(), is(1L));
        assertThat(result.getName(), is("school"));
    }
}
