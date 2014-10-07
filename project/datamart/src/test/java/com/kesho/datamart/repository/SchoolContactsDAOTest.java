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
    @Test
    public void shouldFindBySchoolId() {
        assertThat(repo.findBySchoolId(1L), hasSize(2));
    }
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
    }
}
