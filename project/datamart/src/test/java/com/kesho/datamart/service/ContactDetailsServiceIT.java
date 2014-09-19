package com.kesho.datamart.service;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.dto.ContactDetailDto;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/19/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {"classpath:datamart-service-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ContactDetailsServiceIT {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "students-it-data.xml");

    @Inject
    private ContactDetailsService contactDetailsService;

    @Test
    public void shouldGetContactDetail() {
        List<ContactDetailDto> contacts = contactDetailsService.getContactsOf(1L);
        assertThat(contacts, hasSize(1));
        assertThat(contacts.get(0).getType(), is(ContactType.S));
        assertThat(contacts.get(0).getValue(), is("val"));
        assertThat(contacts.get(0).getComments(), is("comm"));
        assertThat(contacts.get(0).getOwnerId(), is(1L));
    }

    @Test
    public void shouldDeleteContactDetail() {
        assertThat(contactDetailsService.getContactsOf(1L), hasSize(1));
        contactDetailsService.delete(1L);

        assertThat(contactDetailsService.getContactsOf(1L), hasSize(0));
    }


    @Test
    public void shouldSaveStudentContact() {
        ContactDetailDto dto = new ContactDetailDto().withOwnerId(1L)
                .withType(ContactType.S)
                .withValue("val")
                .withComments("comm");

        assertNull(dto.getId());

        dto = contactDetailsService.save(dto);
        assertNotNull(dto.getId());
    }
}
