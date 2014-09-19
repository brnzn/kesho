package com.kesho.datamart.service;

import com.google.common.collect.Lists;
import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.ContactDetail;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.StudentContactDAO;
import com.kesho.datamart.repository.StudentsDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
public class StudentsServiceTest {
    @InjectMocks
    private StudentServiceImpl service;

    @Mock
    private StudentsDAO dao;

    @Mock
    private StudentContactDAO contactDao;

    @Mock
    private org.springframework.data.domain.Page<Student> page;

    @Test
    public void shouldReturnEmptyResultForPageZeroNoSearchResult() {
        Pageable pageSpecification = new PageRequest(0, 10);
        when(dao.findWithFamily(pageSpecification)).thenReturn(page);
        when(page.getTotalPages()).thenReturn(0);
        Page<StudentDto> result = service.getPage(new Request(0, 10));
        assertThat(result.getTotalPages(), is(0));
        assertThat("Should not be error", result.isError(), is(false));
    }

    @Test
    public void shouldGetStudentContacts() {
        ContactDetail contact = new ContactDetail();
        contact.setId(1L);
        contact.setOwner(1L);
        contact.setType(ContactType.S);
        contact.setValue("1234");
        contact.setComments("comments");


        when(contactDao.findByIdAndType(1L, ContactType.S)).thenReturn(Lists.newArrayList(contact));

        List<ContactDetailDto> contacts = service.getStudentContacts(1L);

        assertThat(contacts, hasSize(1));
        assertThat(contacts.get(0).getId(), is(1L));
        assertThat(contacts.get(0).getOwnerId(), is(1L));
        assertThat(contacts.get(0).getType(), is(ContactType.S));
        assertThat(contacts.get(0).getValue(), is("1234"));
        assertThat(contacts.get(0).getComments(), is("comments"));

    }
}
