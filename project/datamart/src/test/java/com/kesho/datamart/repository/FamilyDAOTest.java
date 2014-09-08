package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.Location;
import com.kesho.datamart.entity.Family;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/10/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = { "classpath:repository-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class FamilyDAOTest {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "family-it-data.xml");

    @Inject
    private JpaTransactionManager transactionManager;

    @Inject
    private FamilyDAO dao;

    @Test
    public void shouldLoadFamily() {
        Family family = dao.loadFamily(2L);
        assertThat(family.getStudents(), hasSize(2));
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void shouldFailToSaveStaleEntity() {
        Family f = dao.loadFamily(1L);

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionCallback<Family> callback = new TransactionCallback<Family>() {
            @Override
            public Family doInTransaction(TransactionStatus status) {
                Family f1 = dao.loadFamily(1L);
                f1.setProfile("test");
                return dao.save(f1);
            }
        };

        txTemplate.execute(callback);

        f.setName("test");
        dao.save(f);
    }

    @Test
    public void shouldSaveFamily() {
        Family family = new Family();
        family.setName("name");
        family.setAliveParents(2);
        family.setHomeClusterId("clusterId");
        family.setHomeSubLocation("subLocation");
        family.setMainContactName("mainContact");
        family.setHomeLocation(Location.Ganze);
        family.setMobileNumber("01234556");
        family.setNumOfChildrenAtAddress(3);
        family.setNumOfAdultsAtAddress(2);
        family.setNumOfWives(4);
        family.setPhoneOwnerName("phoneOwner");
        family.setPrimaryCaretaker("primaryTaker");
        family.setProfile("profileffffffffffffffffffff\nghjfghjfghjfghj");
        family.setMarried(Boolean.TRUE);
        family.setPhoneOwner(Boolean.TRUE);

        final Family result = dao.save(family);

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionCallback<Family> callback = new TransactionCallback<Family>() {
            @Override
            public Family doInTransaction(TransactionStatus status) {
                return dao.findOne(result.getId());
            }
        };

        Family saved = txTemplate.execute(callback);

        assertThat(saved.getName(), is("name"));
        assertThat(saved.getAliveParents(), is(2));
        assertThat(saved.getHomeClusterId(), is("clusterId"));
        assertThat(saved.getHomeSubLocation(), is("subLocation"));
        assertThat(saved.getMainContactName(), is("mainContact"));
        assertThat(saved.getHomeLocation(), is(Location.Ganze));
        assertThat(saved.getMobileNumber(), is("01234556"));
        assertThat(saved.getNumOfChildrenAtAddress(), is(3));
        assertThat(saved.getNumOfAdultsAtAddress(), is(2));
        assertThat(saved.getNumOfWives(), is(4));
        assertThat(saved.getPhoneOwnerName(), is("phoneOwner"));
        assertThat(saved.getPrimaryCaretaker(), is("primaryTaker"));
        assertThat(saved.getProfile(), is("profileffffffffffffffffffff\n" +
                "ghjfghjfghjfghj"));
        assertThat(saved.isMarried(), is(Boolean.TRUE));
        assertThat(saved.isPhoneOwner(), is(Boolean.TRUE));
    }


}
