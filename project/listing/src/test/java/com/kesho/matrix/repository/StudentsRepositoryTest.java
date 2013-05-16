package com.kesho.matrix.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.Matchers.is;

import java.sql.SQLException;

import javax.inject.Inject;

import org.dbunit.dataset.DataSetException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kesho.matrix.dbtest.DatabaseSetupRule;
import com.kesho.matrix.entity.Student;
import com.kesho.matrix.entity.StudentLog;

@ContextConfiguration(locations = {
        "classpath:repository-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentsRepositoryTest {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "students-it-data.xml");

	@Inject
	private StudentsRepository repo;
	
	@Test
	public void shouldSaveStudent() throws DataSetException, SQLException {
		Student s = repo.save(new Student());
		assertNotNull("Student should have an id", s.getId());
		
		assertThat("Expected 1 row", dbSetup.getConnection().createQueryTable("students", String.format("select * from students where id=%d", s.getId())).getRowCount(), is(1));
	}

	@Test
	public void shouldFindStudent() {
		Student student = repo.findOne(2L);
		assertNotNull(student);
		assertThat("Should match first name", student.getFirstName(), is("fn"));
	}
	
	@Test
	public void shouldDeleteStudent() throws DataSetException, SQLException {
		repo.delete(2L);
		assertThat("Expected no rows", dbSetup.getConnection().createQueryTable("students", "select * from students where id=2").getRowCount(), is(0));
	}
	
	@Test
	public void shuoldCascadeInsert() throws DataSetException, SQLException {
		Student student = new Student();
		StudentLog log = new StudentLog();
		log.setComment("test log");
		student.addLog(log);
		Student s = repo.save(student);
		assertNotNull("Student should have an id", s.getId());
		assertThat("Student should have an id", s.getLogs().size(), is(1));
		assertThat("Expected student log row", dbSetup.getConnection().createQueryTable("student_log", String.format("select * from student_log where student_id=%d", s.getId())).getRowCount(), is(1));
		
		Object logComment = dbSetup.getConnection().createQueryTable("student_log", String.format("select * from student_log where student_id=%d", s.getId())).getValue(0, "LOG");
		assertThat("Expected comment", log.getComment(), is(logComment));
	}

	@Test
	public void shuoldCascadeInsertMultipleLogs() throws DataSetException, SQLException {

	}
	@Test
	public void shouldCascadeDelete() {
		
	}

	@Test
	public void shouldCascadeUpdate() {
		
	}
	
	
}
