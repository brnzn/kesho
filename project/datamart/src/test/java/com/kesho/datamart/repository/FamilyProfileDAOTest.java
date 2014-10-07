package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 10/7/14
 * Time: 7:42 AM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = { "classpath:repository-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class FamilyProfileDAOTest {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "familyProfile-it-data.xml");

    @Inject
    private JpaTransactionManager transactionManager;

    @Inject
    private FamilyProfileDAO dao;

    @Test
    public void shouldFindByFamilyId() {
        assertThat(dao.findByFamilyId(1L), hasSize(2));
    }

    @Test
    public void shouldDeleteById() {
        assertThat(dao.findByFamilyId(1L), hasSize(2));
        dao.deleteById(1L);
        assertThat(dao.findByFamilyId(1L), hasSize(1));
    }
}
