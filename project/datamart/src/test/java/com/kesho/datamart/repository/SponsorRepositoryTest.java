package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.entity.Sponsor;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.dbunit.dataset.DataSetException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = {
        "classpath:repository-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SponsorRepositoryTest {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "sponsor-it-data.xml");

	@Inject
	private SponsorRepository repo;
	
	@Test
	public void shouldSaveSponsor() throws DataSetException, SQLException {
		Sponsor s = repo.save(new Sponsor());
		assertNotNull("Sponsor should have an id", s.getId());
		
		assertThat("Expected 1 row", dbSetup.getConnection().createQueryTable("sponsors", String.format("select * from sponsors where id=%d", s.getId())).getRowCount(), is(1));
	}

	@Test
	public void shouldFindSponsor() {
		Sponsor sponsor = repo.findOne(999L);
		assertNotNull(sponsor);
		assertThat("Should match first name", sponsor.getFirstName(), is("fn"));
		
		Sponsor expected = new Sponsor();
		expected.setId(999L);
		expected.setTitle("Mr");
		expected.setFirstName("fn");
		expected.setSurname("surname");
		expected.setAddressLin1("addline1");
		expected.setAddressLin2("addline2");
		expected.setAddressLin3("addline3");
		expected.setCounty("county");
		expected.setPostcode("postcode");
		expected.setCountry("country");
		expected.setPhone("phone");
		expected.setEmail("email");
		expected.setGiftAid(Boolean.TRUE);
		
		assertTrue(EqualsBuilder.reflectionEquals(expected, sponsor));
	}
	
	@Test
	public void shouldDeleteSponsor() throws DataSetException, SQLException {
		repo.delete(999L);
		assertThat("Expected no rows", dbSetup.getConnection().createQueryTable("sponsors", "select * from sponsors where id=999").getRowCount(), is(0));
	}
//	
//	@Test
//	public void shuoldCascadeInsertLog() throws DataSetException, SQLException {
//		Student student = new Student();
//		StudentLog log = new StudentLog();
//		log.setComment("test log");
//		student.addLog(log);
//		Student s = repo.save(student);
//		assertNotNull("Student should have an id", s.getId());
//		assertThat("Student should have one log", s.getLogs().size(), is(1));
//		assertThat("Expected student log row", dbSetup.getConnection().createQueryTable("student_log", String.format("select * from student_log where student_id=%d", s.getId())).getRowCount(), is(1));
//		
//		Object logComment = dbSetup.getConnection().createQueryTable("student_log", String.format("select * from student_log where student_id=%d", s.getId())).getValue(0, "LOG");
//		assertThat("Expected comment", log.getComment(), is(logComment));
//	}
//
//	@Test
//	public void shuoldCascadeInsertMultipleLogs() throws DataSetException, SQLException {
//		Student student = new Student();
//		StudentLog log1 = new StudentLog();
//		log1.setComment("log1");
//		student.addLog(log1);
//
//		StudentLog log2 = new StudentLog();
//		log2.setComment("log2");
//		student.addLog(log2);
//		Student s = repo.save(student);
//		
//		assertNotNull("Student should have an id", s.getId());
//		assertThat("Student should have 2 logs", s.getLogs().size(), is(2));
//
//		assertThat("Expected student log row", dbSetup.getConnection().createQueryTable("student_log", String.format("select * from student_log where student_id=%d", s.getId())).getRowCount(), is(2));
//	}
//
//	@Test
//	public void shouldDeleteLogsOnUpdate() throws DataSetException, SQLException {
//		Student student = new Student();
//		StudentLog log = new StudentLog();
//		log.setComment("test log");
//		student.addLog(log);
//		Student s = repo.save(student);
//		
//		s.getLogs().remove(0);
//		s = repo.save(s);
//		
//		assertThat("Expected student log row", dbSetup.getConnection().createQueryTable("student_log", String.format("select * from student_log where student_id=%d", s.getId())).getRowCount(), is(0));
//	}
//
//	@Test
//	public void shouldCascadeDeleteLogs() throws DataSetException, SQLException {
//		Student student = new Student();
//		StudentLog log = new StudentLog();
//		log.setComment("test log");
//		student.addLog(log);
//		Student s = repo.save(student);
//		
//		repo.delete(s.getId());
//		
//		assertThat("Expected student log row", dbSetup.getConnection().createQueryTable("student_log", String.format("select * from student_log where student_id=%d", s.getId())).getRowCount(), is(0));
//	}
//	
//	@Test
//	public void shouldCascadeUpdateLog() throws DataSetException, SQLException {
//		Student student = new Student();
//		StudentLog log = new StudentLog();
//		log.setComment("test log");
//		student.addLog(log);
//		Student s = repo.save(student);
//
//		s.getLogs().get(0).setComment("new comment");
//		s = repo.save(s);
//		
//		assertThat("Expected comment", dbSetup.getConnection().createQueryTable("student_log", String.format("select LOG from student_log where student_id=%d", s.getId())).getValue(0, "LOG").toString(), is("new comment"));
//	}
//	
//	@Test
//	public void shouldCascadeInsertEducationHistory() throws DataSetException, SQLException {
//		Student student = repo.findOne(2L);
//		School school = new School();
//		school.setId(1L);
//		
//		EducationHistory eh = new EducationHistory();
//		eh.setCurrentClass(CLASS.YEAR1);
//		eh.setLevel("Level1");
//		eh.setPredictedEndDate(LocalDate.now());
//		eh.setPredictedEndDate(LocalDate.now().plusYears(1));
//		//eh.setStudentId(student.getId());
//		eh.setSchool(school);
//		
//		EducationHistory eh1 = new EducationHistory();
//		eh1.setCurrentClass(CLASS.YEAR2);
//		eh1.setLevel("Level2");
//		eh1.setPredictedEndDate(LocalDate.now());
//		eh1.setPredictedEndDate(LocalDate.now().plusYears(1));
//		//eh1.setStudentId(student.getId());
//		eh1.setSchool(school);
//		
//		student.addToEducationHistory(eh);
//		student.addToEducationHistory(eh1);
//		student = repo.save(student);
//
//		assertThat("Expected EDUCATION_HISTORY", dbSetup.getConnection().createQueryTable("EDUCATION_HISTORY", String.format("select * from EDUCATION_HISTORY where student_id=%d order by level asc", student.getId())).getValue(0, "level").toString(), is("Level1"));
//		assertThat("Expected EDUCATION_HISTORY", dbSetup.getConnection().createQueryTable("EDUCATION_HISTORY", String.format("select * from EDUCATION_HISTORY where student_id=%d order by level asc", student.getId())).getValue(0, "class").toString(), is(CLASS.YEAR1.name()));
//		assertThat("Expected EDUCATION_HISTORY", dbSetup.getConnection().createQueryTable("EDUCATION_HISTORY", String.format("select * from EDUCATION_HISTORY where student_id=%d order by level asc", student.getId())).getValue(1, "level").toString(), is("Level2"));
//		assertThat("Expected EDUCATION_HISTORY", dbSetup.getConnection().createQueryTable("EDUCATION_HISTORY", String.format("select * from EDUCATION_HISTORY where student_id=%d order by level asc", student.getId())).getValue(1, "class").toString(), is(CLASS.YEAR2.name()));
//		
//		assertThat(student.getEducationHistory().iterator().next().getSchool().getName(), is("school1"));
//	}
//	
//	@Test
//	public void shouldCascadeDeleteEducationHistory() throws DataSetException, SQLException {
//		Student student = repo.findOne(2L);
//		School school = new School();
//		school.setId(1L);
//		
//		EducationHistory eh = new EducationHistory();
//		eh.setCurrentClass(CLASS.YEAR1);
//		eh.setLevel("Level1");
//		eh.setPredictedEndDate(LocalDate.now());
//		eh.setPredictedEndDate(LocalDate.now().plusYears(1));
//		//eh.setStudentId(student.getId());
//		eh.setSchool(school);
//		
//		student.addToEducationHistory(eh);
//		student = repo.save(student);
//
//		repo.delete(student.getId());
//		
//		assertThat("Expected EDUCATION_HISTORY", dbSetup.getConnection().createQueryTable("EDUCATION_HISTORY", String.format("select * from EDUCATION_HISTORY where student_id=%d", student.getId())).getRowCount(), is(0));
//	}
//	
//	@Test
//	public void shouldNotCascadeDeleteEducationHistoryToSchool() throws DataSetException, SQLException {
//		Student student = repo.findOne(2L);
//		School school = new School();
//		school.setId(1L);
//		
//		EducationHistory eh = new EducationHistory();
//		eh.setCurrentClass(CLASS.YEAR1);
//		eh.setLevel("Level1");
//		eh.setPredictedEndDate(LocalDate.now());
//		eh.setPredictedEndDate(LocalDate.now().plusYears(1));
//		//eh.setStudentId(student.getId());
//		eh.setSchool(school);
//		student.addToEducationHistory(eh);
//		student = repo.save(student);
//
//		repo.delete(student.getId());
//		
//		assertThat("Expected EDUCATION_HISTORY", dbSetup.getConnection().createQueryTable("EDUCATION_HISTORY", String.format("select * from EDUCATION_HISTORY where student_id=%d", student.getId())).getRowCount(), is(0));
//		assertThat("Expected EDUCATION_HISTORY", dbSetup.getConnection().createTable("SCHOOLS").getRowCount(), is(1));
//		
//	}
//	
//	@Test(expected = JpaObjectRetrievalFailureException.class)
//	public void shouldNotCascadeInsertEducationHistoryToSchool() throws DataSetException, SQLException {
//		Student student = repo.findOne(2L);
//		School school = new School();
//		school.setId(Long.MAX_VALUE);
//		
//		EducationHistory eh = new EducationHistory();
//		eh.setCurrentClass(CLASS.YEAR1);
//		eh.setLevel("Level1");
//		eh.setPredictedEndDate(LocalDate.now());
//		eh.setPredictedEndDate(LocalDate.now().plusYears(1));
//		//eh.setStudentId(student.getId());
//		eh.setSchool(school);
//		student.addToEducationHistory(eh);
//		student = repo.save(student);
//	}
	
}
