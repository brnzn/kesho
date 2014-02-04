package com.kesho.datamart.service;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.entity.Family;
import com.kesho.datamart.repository.FamilyDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

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
    @Inject
    private FamilyService familyService;
    @Inject
    private JpaTransactionManager transactionManager;
    @Inject
    private FamilyDAO dao;


    @Test
    public void shouldCreateFamily() {
        FamilyDto family = new FamilyDto(null, "name");

        FamilyDto result = familyService.save(family);
        assertThat(result.getId(), notNullValue());

        Family saved = DBUtil.findOne(transactionManager, Family.class, dao, result.getId());
        assertThat(saved.getName(), is(result.getFamilyName()));
    }
}
