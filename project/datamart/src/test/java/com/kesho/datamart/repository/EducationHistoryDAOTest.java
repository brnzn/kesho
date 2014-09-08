package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.EducationStatus;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.entity.EducationHistory;
import com.kesho.datamart.entity.Student;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
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
public class EducationHistoryDAOTest {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "education-it-data.xml");

    @Inject
    private EducationHistoryDAO educationHistoryDAO;

    @Inject
    private JpaTransactionManager transactionManager;

    @Test(expected = OptimisticLockingFailureException.class)
    public void shouldFailToSaveStaleEntity() {
        EducationHistory entity = educationHistoryDAO.findOne(2L);

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionCallback<EducationHistory> callback = new TransactionCallback<EducationHistory>() {
            @Override
            public EducationHistory doInTransaction(TransactionStatus status) {
                EducationHistory e1 = educationHistoryDAO.findOne(2L);
                e1.setCourse("dummy");
                return educationHistoryDAO.save(e1);
            }
        };

        txTemplate.execute(callback);

        entity.setComments("test");
        educationHistoryDAO.save(entity);
    }

    @Test
    public void shouldFindLatestEducation() {
        EducationDto dto = educationHistoryDAO.findLatestEducation(2L);
        assertThat(dto.getEducationalStatus(), is(EducationStatus.Primary));
        assertThat(dto.getDate().getYear(), is(2013));
        assertThat(dto.getDate().getMonthOfYear(), is(4));
        assertThat(dto.getDate().getDayOfMonth(), is(28));
    }
    @Test
    public void shouldGetEducationHistory() {
        List<EducationHistory> result = educationHistoryDAO.findByStudentId(2L);
        assertThat("Unexpected number of education history", result.size(), is(2));
    }


}
