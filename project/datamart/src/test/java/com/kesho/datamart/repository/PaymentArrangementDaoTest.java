package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.FinancialArrangement;
import com.kesho.datamart.dto.PaymentArrangementDto;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 1/1/14
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = { "classpath:repository-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentArrangementDaoTest {
    @Inject
    private PaymentArrangementDao dao;
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "payment-it-data.xml");

    @Test
    public void shouldFindPaymentArrangements() {
        List<PaymentArrangementDto> list = dao.findBySponsorId(1L);
        assertThat(list, hasSize(2));

        PaymentArrangementDto dto = list.get(0);
        assertThat(dto.getId(), is(2L));
        assertTrue(dto.getAmount().compareTo(BigDecimal.valueOf(12)) == 0);
        assertThat(dto.getStudentId(), is(2L));
        assertThat(dto.getSponsorId(), is(1L));
        assertThat(dto.getFinancialArrangement(), is(FinancialArrangement.Annual));
        assertThat(dto.getStartDate().getYear(), is(2013));
        assertThat(dto.getStartDate().getMonthOfYear(), is(3));
        assertThat(dto.getStartDate().getDayOfMonth(), is(28));
        assertThat(dto.getEndDate().getYear(), is(2014));
        assertThat(dto.getEndDate().getMonthOfYear(), is(2));
        assertThat(dto.getEndDate().getDayOfMonth(), is(28));

        dto = list.get(1);
        assertThat(dto.getId(), is(1L));
        assertTrue(dto.getAmount().compareTo(BigDecimal.ONE) == 0);
        assertThat(dto.getStudentId(), is(1L));
        assertThat(dto.getSponsorId(), is(1L));
        assertThat(dto.getFinancialArrangement(), is(FinancialArrangement.StandingOrder));
        assertThat(dto.getStartDate().getYear(), is(2013));
        assertThat(dto.getStartDate().getMonthOfYear(), is(3));
        assertThat(dto.getStartDate().getDayOfMonth(), is(28));
        assertThat(dto.getEndDate().getYear(), is(2014));
        assertThat(dto.getEndDate().getMonthOfYear(), is(3));
        assertThat(dto.getEndDate().getDayOfMonth(), is(28));

/*
            <FAMILY ID="1" FAMILY_NAME="a"/>

    <STUDENTS ID="1" NAME="s1" FAMILY_ID="1"/>
    <STUDENTS ID="2" NAME="s2" FAMILY_ID="1"/>

    <SPONSORS ID="1" NAME="fn" SURNAME="surname" ADDRESS_LINE_1="addline1" ADDRESS_LINE_2="addline2" ADDRESS_LINE_3="addline3" COUNTY="county" POSTCODE="postcode"
              COUNTRY="country" PHONE="phone" EMAIL1="email"/>

    <PAYMENT_ARRANGEMENTS ID="1" STUDENT_ID="1" SPONSOR_ID="1" START_DATE="2013-03-28 00:00:00.0" END_DATE="2014-03-28 00:00:00.0" TYPE="StandingOrder" AMOUNT="1"/>
    <PAYMENT_ARRANGEMENTS ID="2" STUDENT_ID="2" SPONSOR_ID="1" START_DATE="2013-03-28 00:00:00.0" END_DATE="2014-02-28 00:00:00.0" TYPE="Annual" AMOUNT="12" />

         */
    }
}
