package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.*;
import org.dbunit.dataset.DataSetException;
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
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = {
        "classpath:repository-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentsDAOTest {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "students-it-data.xml");

	@Inject
	private StudentsDAO repo;
    @Inject
    private FamilyDAO familyDAO;

    @Inject
    private JpaTransactionManager transactionManager;

    @Test
    public void testFindAll() {
        List<StudentVO> students = repo.all();
        assertThat(students, hasSize(4));

        for (StudentVO student:students) {
            assertThat(student.getId(), notNullValue());
            assertThat(student.getFirstName(), notNullValue());
            assertThat(student.getSurname(), notNullValue());
        }
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void shouldFailToSaveStaleEntity() {
        Student student = repo.findOne(2L);

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        TransactionCallback<Student> callback = new TransactionCallback<Student>() {
            @Override
            public Student doInTransaction(TransactionStatus status) {
                Student s1 = repo.findOne(2L);
                s1.setFirstName("dummy");
                return repo.save(s1);
            }
        };

        txTemplate.execute(callback);

        student.setFirstName("test");
        repo.save(student);
    }


    @Test
	public void shouldSaveStudent() throws DataSetException, SQLException {
        LocalDate startDate = LocalDate.now();
        Student student = new Student();
        student.setFirstName("s1");
        Family f = familyDAO.findOne(1L);
        student.setFamily(f);
        student.setGender(Gender.M);
        student.setHasDisability(true);
        student.setHomeLocation(Location.Ganze);
        student.setContactNumber("12345");
        student.setStartDate(startDate);
        student.setFinancialSupport(true);
        student.setYearOfBirth(2000);

		final Student s = repo.save(student);
		assertNotNull("Student should have an id", s.getId());

        TransactionCallback<Student> callback = new TransactionCallback<Student>() {
            @Override
            public Student doInTransaction(TransactionStatus status) {
                return repo.findOne(s.getId());
            }
        };

        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        Student saved = txTemplate.execute(callback);

        assertThat(saved.getFirstName(), is("s1"));
        assertThat(saved.getFamily().getName(), is("a"));
        assertThat(saved.getGender(), is(Gender.M));
        assertThat(saved.hasDisability(), is(true));
        assertThat(saved.getHomeLocation(), is(Location.Ganze));
        assertThat(saved.getContactNumber(), is("12345"));
        assertThat(saved.getStartDate(), is(startDate));
        assertThat(saved.getYearOfBirth(), is(2000));

		assertThat("Expected 1 row", dbSetup.getConnection().createQueryTable("students", String.format("select * from STUDENTS where id=%d", s.getId())).getRowCount(), is(1));
	}

	@Test
	public void shouldFindStudent() {
		Student student = repo.findOne(2L);
		assertNotNull(student);
		assertThat("Should match first name", student.getFirstName(), is("n2"));
	}
}
