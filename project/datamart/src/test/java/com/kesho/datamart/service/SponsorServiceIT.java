package com.kesho.datamart.service;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.FoundUs;
import com.kesho.datamart.domain.LevelOfParticipation;
import com.kesho.datamart.domain.PayeeType;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.entity.Family;
import com.kesho.datamart.entity.Sponsor;
import com.kesho.datamart.repository.FamilyDAO;
import com.kesho.datamart.repository.PaymentArrangementDao;
import com.kesho.datamart.repository.SponsorsDAO;
import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
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
public class SponsorServiceIT {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "sponsors-it-data.xml");

    @Inject
    private SponsorService service;
    @Inject
    private JpaTransactionManager transactionManager;
    @Inject
    private SponsorsDAO dao;
    @Inject
    private PaymentArrangementDao paymentArrangementDao;


    @Test
    public void shouldDeleteSponsorAndPaymentArrangement() {
        assertNotNull(dao.findOne(1L));
        assertThat(paymentArrangementDao.findBySponsorId(1L), hasSize(1));

        service.deleteSponsor(1L);
        assertNull(dao.findOne(1L));
        assertThat(paymentArrangementDao.findBySponsorId(1L), hasSize(0));

    }

    @Test
    public void shouldCreateSponsor() {
        SponsorDto dto = new SponsorDto();
        dto.setId(1L);
        dto.setName("name");
        dto.setStartDate(LocalDate.now())       ;
        dto.setActive(true);
        dto.setAddressLine1("lin1");
        dto.setAddressLine2("lin2");
        dto.setAnonymous(true);
        dto.setCountry("country");
        dto.setCounty("county");
        dto.setEndDate(LocalDate.now());
        dto.setPostcode("postcode");
        dto.setPayeeType(PayeeType.Club);
        dto.setPhone("12345");
        dto.setLevelOfParticipation(LevelOfParticipation.Anonymous);
        dto.setHowFoundUs(FoundUs.AnotherSponsor);
        dto.setSurname("surname");
        dto.setEmail1("email1");
        dto.setEmail2("email2");

        SponsorDto result = service.save(dto);
        assertThat(result.getId(), notNullValue());

        Sponsor saved = DBUtil.findOne(transactionManager, Sponsor.class, dao, result.getId());
        assertThat(saved.getName(), is(result.getName()));

        assertThat(saved.getStartDate(), is(result.getStartDate()));
        assertThat(saved.getActive(), is(result.getActive()));
        assertThat(saved.getAddressLine1(), is(result.getAddressLine1()));
        assertThat(saved.getAddressLine2(), is(result.getAddressLine2()));
        assertThat(saved.getAnonymous(), is(result.getAnonymous()));
        assertThat(saved.getCountry(), is(result.getCountry()));
        assertThat(saved.getCounty(), is(result.getCounty()));
        assertThat(saved.getEndDate(), is(result.getEndDate()));
        assertThat(saved.getPostcode(), is(result.getPostcode()));
        assertThat(saved.getPayeeType(), is(result.getPayeeType()));
        assertThat(saved.getPhone(), is(result.getPhone()));
        assertThat(saved.getLevelOfParticipation(), is(result.getLevelOfParticipation()));
        assertThat(saved.getHowFoundUs(), is(result.getHowFoundUs()));
        assertThat(saved.getSurname(), is(result.getSurname()));
        assertThat(saved.getEmail1(), is(result.getEmail1()));
        assertThat(saved.getEmail2(), is(result.getEmail2()));
    }
}
