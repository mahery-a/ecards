package com.quarrion.ecards.repository;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.quarrion.ecards.EcardsApiApplication;
import com.quarrion.ecards.model.Address;
import com.quarrion.ecards.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcardsApiApplication.class)
public class UserRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserRepository repository;

	@Autowired
	EntityManager em;

	// Session & Session Factory

	// EntityManager & Persistence Context
	// Transaction

	@Test
	@Transactional
	public void setAddressDetails() {
		User user = em.find(User.class, 20001L);
		user.setAddress(new Address("No 101", "Some Street", "Hyderabad"));
		em.flush();
	}
	
	@Test
	@Transactional
	public void retrieveUserAndEcards() {
		User user = em.find(User.class, 20001L);
		
		logger.info("user -> {}", user);
		logger.info("ecards -> {}", user.getEcards());
	}

}
