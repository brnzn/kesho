package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.entity.EducationHistory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

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

    @Test
    public void shouldGetEducationHistory() {
//        educationHistoryDAO.findOne(1L);
//        List<EducationHistory> h = educationHistoryDAO.findByStudentId(2L);
//        System.out.println(h.get(0).getSchool().getName());

    }


}
