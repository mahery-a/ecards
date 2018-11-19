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
import org.springframework.transaction.annotation.Transactional;

import com.quarrion.ecards.EcardsApiApplication;
import com.quarrion.ecards.model.Theme;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcardsApiApplication.class)
public class NativeQueriesTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	public void native_queries_basic() {
		Query query = em.createNativeQuery("SELECT * FROM THEME", Theme.class);
		List resultList = query.getResultList();
		logger.info("SELECT * FROM THEME  -> {}", resultList);	
	}

	@Test
	public void native_queries_with_parameter() {
		Query query = em.createNativeQuery("SELECT * FROM THEME where id = ?", Theme.class);
		query.setParameter(1, 10001L);
		List resultList = query.getResultList();
		logger.info("SELECT * FROM THEME  where id = ? -> {}", resultList);
	}

	@Test
	public void native_queries_with_named_parameter() {
		Query query = em.createNativeQuery("SELECT * FROM THEME where id = :id", Theme.class);
		query.setParameter("id", 10001L);
		List resultList = query.getResultList();
		logger.info("SELECT * FROM THEME  where id = :id -> {}", resultList);
	}
	
	@Test
	@Transactional
	public void native_queries_to_update() {
		Query query = em.createNativeQuery("Update THEME set last_updated_date=sysdate()");
		int noOfRowsUpdated = query.executeUpdate();
		logger.info("noOfRowsUpdated  -> {}", noOfRowsUpdated);
	}


}
