package com.kesho.datamart.service;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.Family;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.FamilyDAO;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 5:52 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = { "classpath:datamart-service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class FamilyServiceIT {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "families-it-data.xml");

    @Inject
    private FamilyService familyService;
    @Inject
    private JpaTransactionManager transactionManager;
    @Inject
    private FamilyDAO dao;

    @Test(expected = OptimisticLockingFailureException.class)
    public void shouldFailToSaveStaleEntity() {
        FamilyDto family = new FamilyDto().withFamilyName("name");

        final FamilyDto result = familyService.save(family);
        assertThat(result.getId(), notNullValue());
        assertThat(result.getVersion(), is(0));

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionCallback<Family> callback = new TransactionCallback<Family>() {
            @Override
            public Family doInTransaction(TransactionStatus status) {
                Family s1 = dao.findOne(result.getId());
                s1.setName("dummy");
                return dao.save(s1);
            }
        };

        txTemplate.execute(callback);

        result.withAliveParents(10);

        familyService.save(result);

    }

    @Test
    public void shouldCreateFamily() {
        FamilyDto family = new FamilyDto().withFamilyName("name");

        FamilyDto result = familyService.save(family);
        assertThat(result.getId(), notNullValue());

        Family saved = DBUtil.findOne(transactionManager, Family.class, dao, result.getId());
        assertThat(saved.getName(), is(result.getFamilyName()));
    }

    @Test
    public void shouldUpdateFamily() {
        FamilyDto family = new FamilyAssembler().toDto(dao.findOne(1L));

        family.withFamilyName("newname");
        FamilyDto result = familyService.save(family);
        assertThat(result.getId(), notNullValue());
        assertThat(result.getVersion(), is(1));

        Family saved = DBUtil.findOne(transactionManager, Family.class, dao, result.getId());
        assertThat(saved.getName(), is("newname"));
    }

    @Test
    public void shouldDeleteFamily() {
        assertThat("Expected to find family ID 1", DBUtil.findOne(transactionManager, Family.class, dao, 1L), notNullValue());
        familyService.delete(1L);
        assertThat("Expected not to find family ID 1", DBUtil.findOne(transactionManager, Family.class, dao, 1L), nullValue());
    }
    @Test
    public void shouldReturnErrorPageIfPageNumberIsNegative() {
        Page<FamilyDto> page = familyService.getPage(new Request(-1, 1));
        assertThat("Expected error page",page.isError(), is(true));
        assertThat(page.getErrors().get(0), is("Page number cannot be negative"));
    }

    @Test
    public void shouldReturnEmptyPageIfExceededPages() {
        Page<FamilyDto> page = familyService.getPage(new Request(3, 3));
        assertThat(page.getTotalPages(), is(2));
        assertThat(page.getSize(), is(0));
        assertThat("Expect error page", page.isError(), is(true));
        assertThat(page.getErrors().get(0), is("Max pages is [2]"));
    }

    @Test
    public void shouldReturnLastPage() {
        Page<FamilyDto> page = familyService.getPage(new Request(1, 3));
        assertThat(page.getTotalPages(), is(2));
        assertThat(page.getSize(), is(2));
    }
}
