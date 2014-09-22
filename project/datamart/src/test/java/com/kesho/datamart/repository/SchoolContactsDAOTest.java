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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

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

    @Test
    public void shouldGetContactWithDetails() {
        SchoolContact contact = repo.get(1L);
        assertThat(contact.getContactDetails(), hasSize(3));
    }

    @Test
    public void test() {
        SchoolContact c = repo.get(1L);
        c.setJobTitle("dfasdfasdfaf");
        SchoolContact saved = repo.save(c);
        System.out.println(saved);

    }
//    @Test
//    public void shouldGetSchoolContactsWithDetails() {
//        List<SchoolContact> l = repo.findBySchoolId(1L);
//        System.out.println(l.get(0).getContactDetails().get(0).getComments());
//        assertThat(contacts, hasSize(2));
//        assertThat(contacts.get(0).getContactDetails(), hasSize(3));
//        assertThat(contacts.get(1).getContactDetails(), hasSize(1));


//    }

}
