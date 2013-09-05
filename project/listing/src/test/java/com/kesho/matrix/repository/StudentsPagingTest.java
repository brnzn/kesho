package com.kesho.matrix.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.dbunit.dataset.DataSetException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kesho.matrix.dbtest.DatabaseSetupRule;
import com.kesho.matrix.entity.Student;

@ContextConfiguration(locations = { "classpath:repository-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentsPagingTest {
	@Rule
	public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor(
			"kesho", "students-paging-it-data.xml");

	@Inject
	private StudentsRepository repo;

	@Test
	public void shouldSaveStudent() throws DataSetException, SQLException {
		Pageable pageSpecification = new PageRequest(0, 3, new Sort(
				Sort.Direction.ASC, "firstName"));
		
		Page<Student> page = repo.findAll(lastNameIsLike("ab"), pageSpecification);
		assertNotNull(page);

		assertThat(page.getSize(), is(3));
	}

	//TODO: assert pages count, pages content etc
	public Specification<Student> lastNameIsLike(
			final String searchTerm) {

		return new Specification<Student>() {
			@Override
			public Predicate toPredicate(Root<Student> student,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.like(cb.lower(student.<String>get("firstName")),
								likePattern);
			}

			private String getLikePattern(final String searchTerm) {
				StringBuilder pattern = new StringBuilder();
				pattern.append("%");
				pattern.append(searchTerm.toLowerCase());
				pattern.append("%");
				return pattern.toString();
			}
		};
	}

}
