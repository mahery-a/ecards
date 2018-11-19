package com.quarrion.ecards.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.quarrion.ecards.EcardsApiApplication;
import com.quarrion.ecards.model.Theme;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcardsApiApplication.class)
public class CriteriaQueryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	public void all_themes() {
		// "Select t From Theme t"

		// 1. Use Criteria Builder to create a Criteria Query returning the
		// expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Theme> cq = cb.createQuery(Theme.class);

		// 2. Define roots for tables which are involved in the query
		Root<Theme> themeRoot = cq.from(Theme.class);

		// 3. Define Predicates etc using Criteria Builder

		// 4. Add Predicates etc to the Criteria Query

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Theme> query = em.createQuery(cq.select(themeRoot));

		List<Theme> resultList = query.getResultList();

		logger.info("Typed Query -> {}", resultList);
	}

	@Test
	public void all_themes_having_happy() {
		// "Select t From Theme t where name like 'Happy%' "

		// 1. Use Criteria Builder to create a Criteria Query returning the
		// expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Theme> cq = cb.createQuery(Theme.class);

		// 2. Define roots for tables which are involved in the query
		Root<Theme> themeRoot = cq.from(Theme.class);

		// 3. Define Predicates etc using Criteria Builder
		Predicate likeHappy = cb.like(themeRoot.get("name"), "Happy%");

		// 4. Add Predicates etc to the Criteria Query
		cq.where(likeHappy);

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Theme> query = em.createQuery(cq.select(themeRoot));

		List<Theme> resultList = query.getResultList();

		logger.info("Typed Query -> {}", resultList);
	}

	@Test
	public void all_themes_without_users() {
		// "Select t From Theme t where t.users is empty"

		// 1. Use Criteria Builder to create a Criteria Query returning the
		// expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Theme> cq = cb.createQuery(Theme.class);

		// 2. Define roots for tables which are involved in the query
		Root<Theme> themeRoot = cq.from(Theme.class);

		// 3. Define Predicates etc using Criteria Builder
		Predicate studentsIsEmpty = cb.isEmpty(themeRoot.get("users"));

		// 4. Add Predicates etc to the Criteria Query
		cq.where(studentsIsEmpty);

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Theme> query = em.createQuery(cq.select(themeRoot));

		List<Theme> resultList = query.getResultList();

		logger.info("Typed Query -> {}", resultList);
	}

	@Test
	public void join() {
		// "Select t From Theme t join c.users s"

		// 1. Use Criteria Builder to create a Criteria Query returning the
		// expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Theme> cq = cb.createQuery(Theme.class);

		// 2. Define roots for tables which are involved in the query
		Root<Theme> themeRoot = cq.from(Theme.class);

		// 3. Define Predicates etc using Criteria Builder
		Join<Object, Object> join = themeRoot.join("users");

		// 4. Add Predicates etc to the Criteria Query

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Theme> query = em.createQuery(cq.select(themeRoot));

		List<Theme> resultList = query.getResultList();

		logger.info("Typed Query -> {}", resultList);
	}

	@Test
	public void left_join() {
		// "Select t From Theme t left join t.users s"

		// 1. Use Criteria Builder to create a Criteria Query returning the
		// expected result object
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Theme> cq = cb.createQuery(Theme.class);

		// 2. Define roots for tables which are involved in the query
		Root<Theme> themeRoot = cq.from(Theme.class);

		// 3. Define Predicates etc using Criteria Builder
		Join<Object, Object> join = themeRoot.join("users", JoinType.LEFT);

		// 4. Add Predicates etc to the Criteria Query

		// 5. Build the TypedQuery using the entity manager and criteria query
		TypedQuery<Theme> query = em.createQuery(cq.select(themeRoot));

		List<Theme> resultList = query.getResultList();

		logger.info("Typed Query -> {}", resultList);
	}

}
