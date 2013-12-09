package com.kesho.datamart.service;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.entity.School;
import com.kesho.datamart.repository.SchoolsDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 8:32 PM
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = { "classpath:datamart-service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class InstitutionServiceIT {
    @Inject
    private InstitutionService service;
    @Inject
    private JpaTransactionManager transactionManager;
    @Inject
    private SchoolsDAO dao;

    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "schools-it-data.xml");


    @Test
    public void shouldCreateInstitution() {
        InstitutionDto dto = service.create(new InstitutionDto(null, "s1"));
        assertThat(dto.getName(), is("s1"));
        assertThat(dto.getId(), notNullValue());

        School school = DBUtil.findOne(transactionManager, School.class, dao, dto.getId());
        assertThat(school.getName(), is("s1"));
    }

    @Test
    public void shouldReturnInstitutions() {
        List<InstitutionDto> institutions = service.getInstitutions();
        assertThat(institutions.size(), is(2));
        assertThat(institutions.get(0).getId(), is(1L));
        assertThat(institutions.get(0).getName(), is("school1"));
        assertThat(institutions.get(1).getId(), is(2L));
        assertThat(institutions.get(1).getName(), is("school2"));
    }
}
