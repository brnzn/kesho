package com.kesho.datamart.service;

import com.google.common.collect.Lists;
import com.kesho.datamart.dto.HistoryDto;
import com.kesho.datamart.entity.FamilyProfile;
import com.kesho.datamart.repository.FamilyProfileDAO;
import org.hamcrest.Matchers;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/15/14
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class FamilyServiceTest {
    @InjectMocks
    private FamilyServiceImpl service;
    @Mock
    private FamilyProfileDAO profileDao;

    @Test
    public void shouldSaveProfile() {
        LocalDate date = LocalDate.now();
        HistoryDto dto = new HistoryDto(1L, "comments", date);

        FamilyProfile profile = new FamilyProfile();
        profile.setId(0L);
        profile.setFamilyId(1L);
        profile.setComments("comments");
        profile.setDate(date);

        ArgumentCaptor<FamilyProfile> saved = ArgumentCaptor.forClass(FamilyProfile.class);
        when(profileDao.save(saved.capture())).thenReturn(profile);

        HistoryDto result = service.save(dto);

        FamilyProfile captured = saved.getValue();
        assertThat(captured.getFamilyId(), is(1L));
        assertThat(captured.getComments(), is("comments"));
        assertThat(captured.getDate(), is(date));

        assertThat(result.getId(), is(0L));
        assertThat(result.getOwnerId(), is(1L));
        assertThat(result.getComments(), is("comments"));
        assertThat(result.getDate(), is(date));
    }

    @Test
    public void shouldGetFamilyProfileItems() {
        LocalDate date = LocalDate.now();
        FamilyProfile p = new FamilyProfile();
        p.setId(1L);
        p.setDate(date);
        p.setComments("c1");
        p.setFamilyId(1L);

        FamilyProfile p1 = new FamilyProfile();
        p1.setId(2L);
        p1.setDate(date);
        p1.setComments("c2");
        p1.setFamilyId(1L);

        when(profileDao.findByFamilyId(1L)).thenReturn(Lists.newArrayList(p,p1));

        List<HistoryDto> profiles = service.getFamilyProfile(1L);

        assertThat(profiles, hasSize(2));

        assertThat(profiles.get(0).getId(), is(1L));
        assertThat(profiles.get(0).getOwnerId(), is(1L));
        assertThat(profiles.get(0).getDate(), is(date));
        assertThat(profiles.get(0).getComments(), is("c1"));

        assertThat(profiles.get(1).getId(), is(2L));
        assertThat(profiles.get(1).getOwnerId(), is(1L));
        assertThat(profiles.get(1).getDate(), is(date));
        assertThat(profiles.get(1).getComments(), is("c2"));

    }

    @Test
    public void shouldDeleteProfile() {
        service.deleteHistory(1L);
        verify(profileDao).deleteById(1L);
    }

    @Test
    public void shouldUpdateProfile() {

    }

}
