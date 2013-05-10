package com.kesho.listing;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kesho.listing.entity.Student;
import com.kesho.listing.repository.StudentsRepository;

@Transactional
@ContextConfiguration(locations = {
        "classpath:repository-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMe {
	@Inject
	private StudentsRepository repo;
	
	@Test
	public void testme() {
		Student s = repo.save(new Student());
		System.out.println(s.getId());
		
		Student s1q = repo.findOne(s.getId());
		System.out.println(s1q.getId());	
	}

}
