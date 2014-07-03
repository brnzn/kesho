package com.kesho.datamart.repository;

import com.kesho.datamart.dto.StudentSponsorDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 7/2/14
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = { "classpath:repository-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SponsorDAOTest {

    @Inject
    private SponsorsDAO repo;

    @Test
    public void testme() {
        List<StudentSponsorDto> values = repo.getStudentSponsors(10000L);
        System.out.println(values);
    }

}
