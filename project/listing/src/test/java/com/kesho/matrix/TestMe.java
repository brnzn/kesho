package com.kesho.matrix;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kesho.matrix.dbtest.DatabaseSetupRule;
import com.kesho.matrix.entity.Student;
import com.kesho.matrix.repository.StudentsRepository;

//@Transactional
@ContextConfiguration(locations = {
        "classpath:repository-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMe {
    @Rule
    public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor("kesho", "18nLabelKey-it-data.xml");

	@Inject
	private StudentsRepository repo;
	
	@Test
	public void testme() {
		Student s1 = repo.findOne(2L);
		System.out.println("==========================" + s1.getFirstName());
		
		Student s = repo.save(new Student());
		System.out.println(s.getId());
		
		Student s1q = repo.findOne(s.getId());
		System.out.println(s1q.getId());	
	}

}
