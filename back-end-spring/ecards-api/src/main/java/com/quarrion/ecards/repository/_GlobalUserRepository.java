package com.quarrion.ecards.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quarrion.ecards.model.AbstractUser;
import com.quarrion.ecards.model.PremiumUser;
import com.quarrion.ecards.model.User;

// TODO: don't use this class for now, only when premium user features will be ready
//@Repository
//@Transactional
public class _GlobalUserRepository {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	public void insert(AbstractUser user) {
		em.persist(user);
	}

	public List<User> retrieveAllNormalUsers() {
		return em.createQuery("select e from User e", User.class).getResultList();
	}

	public List<PremiumUser> retrieveAllPremiumUsers() {
		return em.createQuery("select e from PremiumUser e", PremiumUser.class).getResultList();
	}

}