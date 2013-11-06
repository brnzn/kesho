package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@ContextConfiguration(locations = { "classpath:repository-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SchoolsRepositoryTest {
	@Rule
	public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor(
			"kesho", "schools-it-data.xml");

	@Inject
	private SchoolsRepository repo;

	@Test
	public void shouldFindSchool() {
		
	}
}
