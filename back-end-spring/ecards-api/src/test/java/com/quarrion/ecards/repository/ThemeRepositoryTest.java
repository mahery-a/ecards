package com.quarrion.ecards.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.quarrion.ecards.EcardsApiApplication;
import com.quarrion.ecards.model.ReviewV2;
import com.quarrion.ecards.model.Theme;
import com.quarrion.ecards.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcardsApiApplication.class)
public class ThemeRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ThemeRepository repository;

	@Autowired
	EntityManager em;

	@Test
	public void findById_basic() {
		Optional<Theme> theme = repository.findById(10001L);
		String name = theme.isPresent()? theme.get().getName():"";
		assertEquals("Happy birthday", name);
	}
	
	@Test
	public void findById_firstLevelCacheDemo() {
		
		Optional<Theme> theme = repository.findById(10001L);
		String name = theme.isPresent()? theme.get().getName():"";
		logger.info("First Theme Retrieved {}", theme);

		Optional<Theme> theme1 = repository.findById(10001L);
		String name1 = theme1.isPresent()? theme1.get().getName():"";
		logger.info("First Theme Retrieved again {}", theme1);

		assertEquals("Happy birthday", name);
		
		assertEquals("Happy birthday", name1);
	}


	@Test
	@DirtiesContext // -> real data won't be changed. only during the test then rollbacked
	public void deleteById_basic() {
		repository.deleteById(10002L);
		assertNull(repository.findById(10002L));
	}

	@Test
	@DirtiesContext
	public void save_basic() {
		// get a theme
		Optional<Theme> theme = repository.findById(10001L);
		String name = theme.isPresent()? theme.get().getName():"";
		assertEquals("Happy birthday", name);

		// update details
		if(theme.isPresent())
		{
			theme.get().setName("Happy birthday - Updated");
			repository.save(theme.get());
		}

		// check the value
		Optional<Theme> theme1 = repository.findById(10001L);
		String name1 = theme1.isPresent()? theme1.get().getName():"";
		assertEquals("Happy birthday - Updated", name1);
	}

	@Test
	@DirtiesContext
	public void playWithEntityManager() {
		repository.playWithEntityManager();
	}

	@Test
	@Transactional
	public void retrieveReviewsForTheme() {
		Optional<Theme> theme = repository.findById(10001L);
		if(theme.isPresent())
			logger.info("{}", theme.get().getReviews());
	}

	@Test
	@Transactional
	public void retrieveThemeForReview() {
		ReviewV2 review = em.find(ReviewV2.class, 50001L);
		logger.info("{}", review.getTheme());
	}

	@Test
	@Transactional
	@DirtiesContext
	public void performance() {
		//for (int i = 0; i < 20; i++)
			//em.persist(new Theme("Something" + i));
		//em.flush();
		
		//EntityGraph graph = em.getEntityGraph("graph.ThemeAndUsers");
		
		EntityGraph<Theme> graph = em.createEntityGraph(Theme.class);
	    Subgraph<List<User>> bookSubGraph = graph.addSubgraph("users");
	    
	    List<Theme> themes = em.createQuery("Select t from Theme t", Theme.class)
	        .setHint("javax.persistence.loadgraph", graph)
	        .getResultList();
	    for (Theme theme : themes) {
	      System.out.println(theme + " " + theme.getUsers());
	    }
	}

	@Test
	@Transactional
	@DirtiesContext
	public void performance_without_hint() {	    
	    List<Theme> themes = em.createQuery("Select t from Theme t", Theme.class)
	        //.setHint("javax.persistence.loadgraph", graph)
	        .getResultList();
	    for (Theme theme : themes) {
	      System.out.println(theme + " " + theme.getUsers());
	    }
	}

}
