package com.kesho.datamart.service;

import com.google.common.collect.Lists;
import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.entity.School;
import com.kesho.datamart.entity.SchoolContact;
import com.kesho.datamart.repository.SchoolContactsDAO;
import com.kesho.datamart.repository.SchoolsDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:22 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class SchoolServiceTest {
    @Mock
    private SchoolsDAO dao;
    @Mock
    private SchoolContactsDAO contactsDao;

    @InjectMocks
    private SchoolServiceImpl service;

    @Test
    public void shouldGetContacts() {
        SchoolContact contact = new SchoolContact();
        //when(contactsDao.getSchoolContacts(1L)).thenReturn(Lists.newArrayList(contact));
    }

    @Test
    public void shouldCreateSchool() {
        School school = new School();
        school.setName("school");
        school.setId(1L);
        school.setCounty("county");
        school.setCountry("country");
        school.setAddressLine1("add1");
        school.setAddressLine2("add2");
        school.setAddressLine3("add3");
        school.setPostcode("postcode");

        SchoolDto dto = new SchoolDto(null)
                .withName("school")
                .withCounty("county")
                .withCountry("country")
                .withAddressLine1("add1")
                .withAddressLine2("add2")
                .withAddressLine3("add3")
                .withPostcode("postcode");


        ArgumentCaptor<School> saved = ArgumentCaptor.forClass(School.class);
        when(dao.save(saved.capture())).thenReturn(school);

        SchoolDto result = service.save(dto);
        verify(dao).save(saved.capture());

        School captured = saved.getValue();
        assertThat(captured.getName(), is("school"));
        assertThat(captured.getCounty(), is("county"));
        assertThat(captured.getCountry(), is("country"));
        assertThat(captured.getAddressLine1(), is("add1"));
        assertThat(captured.getAddressLine2(), is("add2"));
        assertThat(captured.getAddressLine3(), is("add3"));
        assertThat(captured.getPostcode(), is("postcode"));

        assertThat(result.getName(), is("school"));
        assertThat(result.getId(), is(1L));
        assertThat(result.getCounty(), is("county"));
        assertThat(result.getCountry(), is("country"));
        assertThat(result.getAddressLine1(), is("add1"));
        assertThat(result.getAddressLine2(), is("add2"));
        assertThat(result.getAddressLine3(), is("add3"));
        assertThat(result.getPostcode(), is("postcode"));

    }
}
