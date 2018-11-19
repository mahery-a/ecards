package com.quarrion.ecards.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.quarrion.ecards.EcardsApiApplication;
import com.quarrion.ecards.model.Theme;
import com.quarrion.ecards.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcardsApiApplication.class)
public class JPQLTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	public void jpql_basic() {
		Query query = em.createNamedQuery("query_get_all_themes");
		List resultList = query.getResultList();
		logger.info("Select  t  From Theme t -> {}", resultList);
	}

	@Test
	public void jpql_typed() {
		TypedQuery<Theme> query = em.createNamedQuery("query_get_all_themes", Theme.class);

		List<Theme> resultList = query.getResultList();

		logger.info("Select  t  From Theme t -> {}", resultList);
	}

	@Test
	public void jpql_where() {
		TypedQuery<Theme> query = em.createNamedQuery("query_get_happy_themes", Theme.class);

		List<Theme> resultList = query.getResultList();

		logger.info("Select  t  From Theme t where name like 'Happy%'-> {}", resultList);
	}

	@Test
	public void jpql_themes_without_users() {
		TypedQuery<Theme> query = em.createQuery("Select t from Theme t where t.users is empty", Theme.class);
		List<Theme> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}

	
	@Test
	public void jpql_themes_with_atleast_2_users() {
		TypedQuery<Theme> query = em.createQuery("Select t from Theme t where size(t.users) >= 2", Theme.class);
		List<Theme> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}

	@Test
	public void jpql_themes_ordered_by_users() {
		TypedQuery<Theme> query = em.createQuery("Select t from Theme t order by size(t.users) desc", Theme.class);
		List<Theme> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}

	@Test
	public void jpql_users_with_passports_in_a_certain_pattern() {
		TypedQuery<User> query = em.createQuery("Select s from User s where s.passport.number like '%1234%'", User.class);
		List<User> resultList = query.getResultList();
		logger.info("Results -> {}", resultList);
	}
	
	@Test
	public void join(){
		Query query = em.createQuery("Select t, s from Theme t JOIN t.users s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Results Size -> {}", resultList.size());
		for(Object[] result:resultList){
			logger.info("Theme{} User{}", result[0], result[1]);
		}
	}

	@Test
	public void left_join(){
		Query query = em.createQuery("Select t, s from Theme t LEFT JOIN t.users s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Results Size -> {}", resultList.size());
		for(Object[] result:resultList){
			logger.info("Theme{} User{}", result[0], result[1]);
		}
	}

	@Test
	public void cross_join(){
		Query query = em.createQuery("Select t, s from Theme t, User s");
		List<Object[]> resultList = query.getResultList();
		logger.info("Results Size -> {}", resultList.size());
		for(Object[] result:resultList){
			logger.info("Theme{} User{}", result[0], result[1]);
		}
	}

}








