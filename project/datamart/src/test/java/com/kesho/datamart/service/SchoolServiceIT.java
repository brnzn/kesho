package com.kesho.datamart.service;

import com.google.common.collect.Lists;
import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.dto.ContactDto;
import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.entity.ContactDetail;
import com.kesho.datamart.entity.School;
import com.kesho.datamart.repository.ContactDetailsDAO;
import com.kesho.datamart.repository.SchoolContactsDAO;
import com.kesho.datamart.repository.SchoolsDAO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 8:32 PM
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = { "classpath:datamart-service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SchoolServiceIT {
    @Inject
    private SchoolService service;
    @Inject
    private JpaTransactionManager transactionManager;
    @Inject
    private SchoolsDAO dao;
    @Inject
    private SchoolContactsDAO contactsDAO;
    @Inject
    private ContactDetailsDAO contactDetailsDAO;


    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "schools-it-data.xml");


    @Test
    public void shouldDeleteContactDetailsWithContact() {
        assertThat(contactsDAO.getOne(1L), notNullValue());
        assertThat(contactDetailsDAO.findById(1L), hasSize(2));
        service.deleteContact(1L);
        assertThat(contactsDAO.findOne(1L), nullValue());
        assertThat(contactDetailsDAO.findById(1L), hasSize(0));
    }

    @Test
    public void shouldDeleteContact() {
        assertThat(contactsDAO.getOne(1L), notNullValue());
        service.deleteContact(1L);
        assertThat(contactsDAO.findOne(1L), nullValue());
    }

    @Test
    public void shouldCreateContact() {
        ContactDto dto = new ContactDto();
        dto.setJobTitle("jt");
        dto.setSurname("sn");
        dto.setName("name");
        dto.setSchoolId(1L);
        dto.setTitle("mr");

        dto = service.save(dto);

        assertThat(dto.getId(), notNullValue());
    }

    @Test
    public void shouldGetContacts() {
        List<ContactDto> contacts = service.getContacts(1L);
        assertThat(contacts, hasSize(2));
        assertThat(contacts.get(0).getId(), is(1L));
        assertThat(contacts.get(0).getSchoolId(), is(1L));
        assertThat(contacts.get(0).getName(), is("name"));
        assertThat(contacts.get(0).getSurname(), is("sn"));
        assertThat(contacts.get(0).getTitle(), is("mr"));
        assertThat(contacts.get(0).getJobTitle(), is("job"));

        assertThat(contacts.get(1).getId(), is(2L));
        assertThat(contacts.get(1).getSchoolId(), is(1L));
        assertThat(contacts.get(1).getName(), is("name"));
        assertThat(contacts.get(1).getSurname(), is("sn"));
        assertThat(contacts.get(1).getTitle(), is("mr"));
        assertThat(contacts.get(1).getJobTitle(), is("job"));
    }


    @Test
    public void shouldCreateSchool() {
        SchoolDto dto = service.save(new SchoolDto(null).withName("s1"));
        assertThat(dto.getName(), is("s1"));
        assertThat(dto.getId(), notNullValue());

        School school = DBUtil.findOne(transactionManager, School.class, dao, dto.getId());
        assertThat(school.getName(), is("s1"));
    }
}
