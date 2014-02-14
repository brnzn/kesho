package com.kesho.datamart.service;

import com.google.common.base.Predicate;
import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.*;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.EducationHistory;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.repository.EducationHistoryDAO;
import com.kesho.datamart.repository.FamilyDAO;
import com.kesho.datamart.repository.StudentsDAO;
import org.joda.time.LocalDate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
    private EducationHistoryDAO educationHistoryDAO;
    @Inject
    private FamilyDAO familyDAO;

    @Inject
    private JpaTransactionManager transactionManager;


    @Test
    public void shouldCascadeDeleteEducationOnly() {
        StudentDto dto = studentService.get(2L);

        Long familyId =  dto.getFamily().getId();

        assertThat(educationHistoryDAO.findByStudentId(2L), hasSize(2));

        studentService.deleteStudent(2L);

        assertThat(educationHistoryDAO.findByStudentId(2L), hasSize(0));
        assertThat(familyDAO.findOne(familyId), notNullValue());
    }

    @Test
    public void shouldUpdateFamily() {
        StudentDto dto = studentService.get(1L);
        assertThat(dto.getFamily().getFamilyName(), is("sn1"));

        StudentDto newFamily = dto.withFamily(new FamilyDto(2L, "sn2"));
        studentService.save(newFamily);

        Student saved = DBUtil.findOne(transactionManager, Student.class, dao, 1L);
        assertThat(saved.getFamily().getName(), is("sn2"));

    }

    @Test
    public void shouldGetEducationHistory() {
        List<EducationDto> educationHistory = studentService.getEducationHistory(2L);
        assertThat(educationHistory.size(), is(2));

        assertThat(educationHistory.get(0).getDate().getYear(), is(2013));
        assertThat(educationHistory.get(0).getDate().getMonthOfYear(), is(3));
        assertThat(educationHistory.get(0).getDate().getDayOfMonth(), is(28));

        assertThat(educationHistory.get(1).getDate().getYear(), is(2013));
        assertThat(educationHistory.get(1).getDate().getMonthOfYear(), is(4));
        assertThat(educationHistory.get(1).getDate().getDayOfMonth(), is(28));
    }

    @Test
    public void shouldAddEducationHistory() {
        LocalDate date = LocalDate.now();
        EducationDto dto = new EducationDto()
                .withYear(1)
                .withInstitution(new InstitutionDto(1L, "school"))
                .withCourse("course")
                .withEducationDate(date)
                .withEducationalStatus(EducationStatus.Secondary)
                .withSecondaryStatus1(SubEducationStatus.National)
                .withSecondaryStatus2(SubEducationStatus.Day)
                .withStudentId(1L)
                .withComments("comments")
                ;


        EducationDto result = studentService.addEducationHistory(dto);
        assertThat(result.getInstitution().getName(), is("school"));
        assertThat(result.getEducationalStatus(), is(EducationStatus.Secondary));
        assertThat(result.getSecondaryEducationStatus1(), is(SubEducationStatus.National));
        assertThat(result.getSecondaryEducationStatus2(), is(SubEducationStatus.Day));
        assertThat(result.getId(), notNullValue());
        assertThat(result.getCourse(), is("course"));
        assertThat(result.getYear(), is(1));
        assertThat(result.getDate(), is(date));
        assertThat(result.getEducationLevel(), is("Secondary (National)"));
        assertThat(result.getStudentId(), is(1L));
        assertThat(result.getInstitution().getId(), is(1L));
        assertThat(result.getComments(), is("comments"));

        EducationHistory saved = findOne(EducationHistory.class, educationHistoryDAO, result.getId());

        assertThat(saved.getSchool().getId(), is(1L));
    }

    @Test
    public void shouldFindStudent() {
        LocalDate startDate = LocalDate.parse("2013-03-28");
        StudentDto s1 = studentService.get(1L);
        assertNotNull(s1);

        assertNotNull(s1);
        assertThat("name should be fn", s1.getName(), is("fn"));
        assertThat("family name should be sn1", s1.getFamily().getFamilyName(), is("sn1"));
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
        assertThat("student status s1s", s1.getLeaverStatus(), is(LeaverStatus.DEPARTED_BEFORE_COMPLETE));
        assertThat("sponsor status sp1", s1.getSponsorshipStatus(), is(SponsorshipStatus.REVIEW));
        assertThat("level of support full", s1.getLevelOfSupport(), is(LevelOfSupport.FULL));
        assertThat("topup needed true", s1.isTopupNeeded(), is(true));
        assertThat("shortfall 100", s1.getShortfall(), is(100));
        assertThat("alumni number 111", s1.getAlumniNumber(), is(111));
    }

    @Test
    public void shouldSaveStudent() {
        LocalDate startDate = LocalDate.now();
        StudentDto dto = new StudentDto();
        dto.withName("name")
                .withFamily(new FamilyDto(1L, "sn1"))
                .withGender(Gender.M)
                .withHasDisability(true)
                .withHomeLocation("hl")
                .withStartDate(startDate)
                .withYearOfBirth(2000)
                .activeStudent(true)
                .sponsored(true)
                .withEmail("email")
                .withFacebookAddress("fb")
                .withLeaverStatus(LeaverStatus.ALUMNI_NO_LONGER_NEEDED)
                .withSponsorStatus(SponsorshipStatus.RECENT_SECONDARY_LEAVER)
                .withLevelOfSupport(LevelOfSupport.ON_HOLD)
                .withTopupNeeded(true)
                .withShortfall(1)
                .withAlumniNumber(1)
                .withMobileNumber("123");
        final StudentDto resultDto = studentService.save(dto);

        assertNotNull(resultDto.getId());

        Student saved = findOne(Student.class, dao, resultDto.getId());

        assertNotNull(saved);
        assertThat(saved.getFirstName(), is("name"));
        assertThat(saved.getFamily().getName(), is("sn1"));
        assertThat(saved.getGender(), is(Gender.M));
        assertThat(saved.hasDisability(), is(true));
        assertThat(saved.getHomeLocation(), is("hl"));
        assertThat(saved.getStartDate(), is(startDate));
        assertThat(saved.getYearOfBirth(), is(2000));
        assertThat(saved.isActive(), is(true));
        assertThat(saved.isSponsored(), is(true));
        assertThat(saved.getEmail(), is("email"));
        assertThat(saved.getFacebookAddress(), is("fb"));
        assertThat(saved.getLeaverStatus(), is(LeaverStatus.ALUMNI_NO_LONGER_NEEDED));
        assertThat(saved.getSponsorshipStatus(), is(SponsorshipStatus.RECENT_SECONDARY_LEAVER));
        assertThat(saved.getLevelOfSupport(), is(LevelOfSupport.ON_HOLD));
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

    private <T> T findOne(Class<T> entity, final JpaRepository<T, Long> dao, final Long id) {
        TransactionCallback<T> callback = new TransactionCallback<T>() {
            @Override
            public T doInTransaction(TransactionStatus status) {
                return dao.findOne(id);
            }
        };

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        return txTemplate.execute(callback);
    }
}
