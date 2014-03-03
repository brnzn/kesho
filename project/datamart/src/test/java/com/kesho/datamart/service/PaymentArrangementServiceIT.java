package com.kesho.datamart.service;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.FinancialArrangement;
import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.entity.PaymentArrangement;
import com.kesho.datamart.repository.PaymentArrangementDao;
import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/25/13
 * Time: 8:59 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = { "classpath:datamart-service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentArrangementServiceIT {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "payment-arrangement-it-data.xml");
    @Inject
    private PaymentArrangementService service;

    @Inject
    private JpaTransactionManager transactionManager;
    @Inject
    private PaymentArrangementDao paymentArrangementDao;


    @Test
    public void shouldDeletePaymentArrangement() {
        assertThat(paymentArrangementDao.findOne(1L), notNullValue());
        service.delete(1L);
        assertThat(paymentArrangementDao.findOne(1L), nullValue());
    }

    @Test
    public void shouldCreate() {
        PaymentArrangementDto dto = new PaymentArrangementDto();
        LocalDate end = LocalDate.now();
        dto.setEndDate(end);
        dto.setAmount(BigDecimal.TEN);
        dto.setSponsorId(1L);
        dto.setStudentId(1L);
        LocalDate start = LocalDate.now().minusMonths(1);
        dto.setStartDate(start);
        dto.setFinancialArrangement(FinancialArrangement.Annual);

        dto = service.save(dto);
        assertThat(dto.getId() , notNullValue());

        PaymentArrangement saved = DBUtil.findOne(transactionManager, PaymentArrangement.class, paymentArrangementDao, dto.getId());

        assertThat(saved.getEndDate() , is(end));
        assertTrue(saved.getAmount().compareTo(BigDecimal.TEN) == 0);
        assertThat(saved.getSponsorId() , is(1L));
        assertThat(saved.getStudentId() , is(1L));
        assertThat(saved.getStartDate() , is(start));
        assertThat(saved.getType() , is(FinancialArrangement.Annual));
    }

    @Test
    public void shouldUpdate() {
        PaymentArrangementDto dto = new PaymentArrangementDto();
        dto.setId(1L);
        LocalDate end = LocalDate.now();
        dto.setEndDate(end);
        dto.setAmount(new BigDecimal("1.1"));
        dto.setSponsorId(1L);
        dto.setStudentId(1L);
        LocalDate start = LocalDate.now().minusMonths(1);
        dto.setStartDate(start);
        dto.setFinancialArrangement(FinancialArrangement.OneOf);

        dto = service.save(dto);
        assertThat(dto.getId() , notNullValue());

        PaymentArrangement saved = DBUtil.findOne(transactionManager, PaymentArrangement.class, paymentArrangementDao, dto.getId());

        assertThat(saved.getEndDate() , is(end));
        assertTrue(saved.getAmount().compareTo(new BigDecimal("1.1")) == 0);
        assertThat(saved.getSponsorId() , is(1L));
        assertThat(saved.getStudentId() , is(1L));
        assertThat(saved.getStartDate() , is(start));
        assertThat(saved.getType() , is(FinancialArrangement.OneOf));
    }
}
