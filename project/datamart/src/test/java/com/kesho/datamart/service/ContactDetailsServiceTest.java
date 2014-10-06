package com.kesho.datamart.service;

import com.google.common.collect.Lists;
import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.entity.ContactDetail;
import com.kesho.datamart.repository.ContactDetailsDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/26/13
 * Time: 7:35 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactDetailsServiceTest {
    @InjectMocks
    private ContactDetailsServiceImpl service;

    @Mock
    private ContactDetailsDAO contactDao;

    @Test
    public void shouldGetStudentContacts() {
        ContactDetail contact = new ContactDetail();
        contact.setId(1L);
        contact.setOwner(1L);
        contact.setType(ContactType.P);
        contact.setValue("1234");
        contact.setComments("comments");


        when(contactDao.findById(1L)).thenReturn(Lists.newArrayList(contact));

        List<ContactDetailDto> contacts = service.getContactsOf(1L);

        assertThat(contacts, hasSize(1));
        assertThat(contacts.get(0).getId(), is(1L));
        assertThat(contacts.get(0).getOwnerId(), is(1L));
        assertThat(contacts.get(0).getType(), is(ContactType.P));
        assertThat(contacts.get(0).getValue(), is("1234"));
        assertThat(contacts.get(0).getComments(), is("comments"));

    }
}
