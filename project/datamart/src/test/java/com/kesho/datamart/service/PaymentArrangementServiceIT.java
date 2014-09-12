package com.kesho.datamart.service;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.FinancialArrangement;
import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.entity.PaymentArrangement;
import com.kesho.datamart.entity.Sponsor;
import com.kesho.datamart.repository.PaymentArrangementDao;
import org.joda.time.LocalDate;
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
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

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


    @Test(expected = OptimisticLockingFailureException.class)
    public void shouldFailToSaveStaleEntity() {
        final List<PaymentArrangementDto> payments = service.getPaymentArrangements(1L);
        assertThat(payments.get(0).getVersion(), is(0));

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionCallback<PaymentArrangement> callback = new TransactionCallback<PaymentArrangement>() {
            @Override
            public PaymentArrangement doInTransaction(TransactionStatus status) {
                PaymentArrangement s1 = paymentArrangementDao.findOne(payments.get(0).getId());
                s1.setType(FinancialArrangement.OneOf);
                return paymentArrangementDao.save(s1);
            }
        };

        PaymentArrangement modified = txTemplate.execute(callback);
        assertThat(modified.getVersion(), is(1));

        payments.get(0).setCurrency(Currency.getInstance("GBP"));

        service.save(payments.get(0));
    }

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
        dto.setCurrency(Currency.getInstance("GBP"));

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
        dto.setVersion(0);
        LocalDate end = LocalDate.now();
        dto.setEndDate(end);
        dto.setAmount(new BigDecimal("1.1"));
        dto.setSponsorId(1L);
        dto.setStudentId(1L);
        LocalDate start = LocalDate.now().minusMonths(1);
        dto.setStartDate(start);
        dto.setFinancialArrangement(FinancialArrangement.OneOf);
        dto.setCurrency(Currency.getInstance("GBP"));

        dto = service.save(dto);
        assertThat(dto.getId() , notNullValue());

        PaymentArrangement saved = DBUtil.findOne(transactionManager, PaymentArrangement.class, paymentArrangementDao, 1L);

        assertThat(saved.getEndDate() , is(end));
        assertTrue(saved.getAmount().compareTo(new BigDecimal("1.1")) == 0);
        assertThat(saved.getSponsorId() , is(1L));
        assertThat(saved.getStudentId() , is(1L));
        assertThat(saved.getStartDate() , is(start));
        assertThat(saved.getType() , is(FinancialArrangement.OneOf));
    }
}
