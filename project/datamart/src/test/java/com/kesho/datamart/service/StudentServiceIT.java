package com.kesho.datamart.service;

import com.google.common.base.Predicate;
import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.repository.StudentsDAO;
import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/19/13
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = { "classpath:datamart-service-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentServiceIT {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "students-it-data.xml");

    @Inject
    private StudentService studentService;

    @Inject
    private StudentsDAO dao;

    @Inject
    private JpaTransactionManager transactionManager;


    @Test
    public void shouldFindStudent() {
        LocalDate startDate = LocalDate.parse("2013-03-28");
        StudentDto s1 = studentService.get(1L);
        assertNotNull(s1);

        assertNotNull(s1);
        assertThat("name should be fn", s1.getName(), is("fn"));
        assertThat("family name should be sn1", s1.getFamilyName(), is("sn1"));
        assertThat("should be active", s1.isActiveStudent(), is(true));
        assertThat("gender should be M", s1.getGender(), is(Gender.M));
        assertThat("has disability true", s1.hasDisability(), is(true));
        assertThat("home location h1", s1.getHomeLocation(), is("h1"));
        assertThat("contact number 11111", s1.getMobileNumber(), is("11111"));
        assertThat("is sponsored true", s1.isSponsored(), is(true));
        assertThat("start date " + startDate.toString(), s1.getStartDate(), is(startDate));
        assertThat("year of birth 2000", s1.getYearOfBirth(), is(2000));
        assertThat("email email1 ", s1.getEmail(), is("email1"));
        assertThat("facebook fb1", s1.getFacebookAddress(), is("fb1"));
        assertThat("student status s1s", s1.getStatus(), is("s1s"));
        assertThat("sponsor status sp1", s1.getSponsorshipStatus(), is("sp1"));
        assertThat("level of support los1", s1.getLevelOfSupport(), is("los1"));
        assertThat("topup needed true", s1.isTopupNeeded(), is(true));
        assertThat("shortfall 100", s1.getShortfall(), is(100));
        assertThat("alumni number 111", s1.getAlumniNumber(), is(111));
    }

    @Test
    public void shouldSaveStudent() {
        LocalDate startDate = LocalDate.now();
        StudentDto dto = new StudentDto();
        dto.withName("name")
                .withFamilyName("fn")
                .withGender(Gender.M)
                .withHasDisability(true)
                .withHomeLocation("hl")
                .withStartDate(startDate)
                .withYearOfBirth(2000)
                .activeStudent(true)
                .sponsored(true)
                .withEmail("email")
                .withFacebookAddress("fb")
                .withStatus("status")
                .withSponsorStatus("sps")
                .withLevelOfSupport("los")
                .withTopupNeeded(true)
                .withShortfall(1)
                .withAlumniNumber(1)
                .withMobileNumber("123");
        final StudentDto resultDto = studentService.save(dto);

        assertNotNull(resultDto.getId());

        TransactionCallback<Student> callback = new TransactionCallback<Student>() {
            @Override
            public Student doInTransaction(TransactionStatus status) {
                return dao.findOne(resultDto.getId());
            }
        };

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        Student saved = txTemplate.execute(callback);

        assertNotNull(saved);
        assertThat(saved.getFirstName(), is("name"));
        assertThat(saved.getSurname(), is("fn"));
        assertThat(saved.getGender(), is(Gender.M));
        assertThat(saved.hasDisability(), is(true));
        assertThat(saved.getHomeLocation(), is("hl"));
        assertThat(saved.getStartDate(), is(startDate));
        assertThat(saved.getYearOfBirth(), is(2000));
        assertThat(saved.isActive(), is(true));
        assertThat(saved.isSponsored(), is(true));
        assertThat(saved.getEmail(), is("email"));
        assertThat(saved.getFacebookAddress(), is("fb"));
        assertThat(saved.getStatus(), is("status"));
        assertThat(saved.getSponsorshipStatus(), is("sps"));
        assertThat(saved.getLevelOfSupport(), is("los"));
        assertThat(saved.isTopupNeeded(), is(true));
        assertThat(saved.getShortfall(), is(1));
        assertThat(saved.getAlumniNumber(), is(1));
    }

//    @Test
//    public void shouldGetStudents() {
//        LocalDate startDate = LocalDate.parse("2013-03-28");
//        List<StudentDto> students = studentService.getAll();
//        assertNotNull(students);
//
//        assertThat(students.size(), is(2));
//        assertNotNull(Iterables.find(students, findFyId(1L)));
//        assertNotNull(Iterables.find(students, findFyId(2L)));
//    }


    private Predicate<StudentDto> findFyId(final Long id) {
        return new Predicate<StudentDto>() {
            @Override
            public boolean apply(@Nullable StudentDto studentDto) {
                return studentDto.getId() == id;
            }
        };
    }
}
