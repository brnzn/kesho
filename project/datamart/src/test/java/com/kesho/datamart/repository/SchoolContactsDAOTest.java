package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.entity.ContactDetail;
import com.kesho.datamart.entity.SchoolContact;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/22/14
 * Time: 7:39 AM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = { "classpath:repository-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SchoolContactsDAOTest {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor(
            "kesho", "school-contacts-it-data.xml");

    @Inject
    private SchoolContactsDAO repo;

//    @Test
//    public void shouldGetContactWithDetails() {
//        SchoolContact contact = repo.get(1L);
//        assertThat(contact.getContactDetails(), hasSize(3));
//    }

    @Test
    public void shouldSaveContact() {
        SchoolContact contact = new SchoolContact();
        contact.setJobTitle("jt");
        contact.setSurname("sn");
        contact.setName("name");
        contact.setSchoolId(1L);
        contact.setTitle("mr");

        contact = repo.save(contact);
        assertThat(contact.getId(), notNullValue());

//        List<ContactDetail> details = new ArrayList<>();
//        ContactDetail detail = new ContactDetail();
//        detail.setType(ContactType.I);
//        detail.setValue("val");
//        detail.setOwner(contact.getId());
//        details.add(detail);
//        contact.setContactDetails(details);

//        contact = repo.save(contact);

//        contact.getContactDetails().get(0).setValue("aaaaaaaaaaaa");

//        ContactDetail detail1 = new ContactDetail();
//        detail1.setType(ContactType.I);
//        detail1.setValue("val");
//        detail1.setOwner(contact.getId());
//        contact.getContactDetails().add(detail1);
//
//        contact = repo.save(contact);
//        System.out.println(contact.getContactDetails().get(0).getValue());
    }
//    @Test
//    public void test() {
//        List<SchoolContact> l = repo.findBySchoolId(1L);
//        System.out.println("==========" + l.size());
//
//    }
//    @Test
//    public void shouldGetSchoolContactsWithDetails() {
//        List<SchoolContact> l = repo.findBySchoolId(1L);
//        System.out.println(l.get(0).getContactDetails().get(0).getComments());
//        assertThat(contacts, hasSize(2));
//        assertThat(contacts.get(0).getContactDetails(), hasSize(3));
//        assertThat(contacts.get(1).getContactDetails(), hasSize(1));


//    }

}
