package com.kesho.datamart.repository;

import com.kesho.datamart.dbtest.DatabaseSetupRule;
import com.kesho.datamart.entity.Family;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.entity.model.Student_;
import org.dbunit.dataset.DataSetException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.criteria.*;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = { "classpath:repository-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentsPagingTest {
	@Rule
	public final DatabaseSetupRule dbSetup = DatabaseSetupRule.setUpDataFor(
			"kesho", "students-paging-it-data.xml");

	@Inject
	private StudentsDAO repo;

    @Test
    public void shouldFindStudentsWithNameLike() {
        Pageable pageSpecification = new PageRequest(0, 3, new Sort(
                Sort.Direction.ASC, "firstName"));
        List<Student> students = repo.findAll(lastNameIsLike("ab"));
        assertThat(students.size(), is(3));
    }

//	@Test    not working with paging and join
//	public void shouldFindStudentsPage() throws DataSetException, SQLException {
//		Pageable pageSpecification = new PageRequest(0, 3, new Sort(
//				Sort.Direction.ASC, "firstName"));
//
//		Page<Student> page = repo.findAll(lastNameIsLike("ab"), pageSpecification);
//		assertNotNull(page);
//
//		assertThat(page.getSize(), is(3));
//	}

	//TODO: assert pages count, pages content etc
	public Specification<Student> lastNameIsLike(
			final String searchTerm) {

		return new Specification<Student>() {
			@Override
			public Predicate toPredicate(Root<Student> student,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
                student.fetch("family", JoinType.INNER);
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
