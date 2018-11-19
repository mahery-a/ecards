package com.quarrion.ecards.repository;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;

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
public class PerformanceTuningTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;

	@Test
	@Transactional
	public void creatingNPlusOneProblem() {
		List<Theme> themes = em
				.createNamedQuery("query_get_all_themes", Theme.class)
				.getResultList();
		for(Theme theme:themes){
			logger.info("Theme -> {} Users -> {}",theme, theme.getUsers());
		}
	}
	
	@Test
	@Transactional
	public void solvingNPlusOneProblem_EntityGraph() {

		EntityGraph<Theme> entityGraph = em.createEntityGraph(Theme.class);
		Subgraph<Object> subGraph = entityGraph.addSubgraph("students");
		
		List<Theme> themes = em
				.createNamedQuery("query_get_all_themes", Theme.class)
				.setHint("javax.persistence.loadgraph", entityGraph)
				.getResultList();
		
		for(Theme theme:themes){
			logger.info("Theme -> {} Users -> {}",theme, theme.getUsers());
		}
	}

	@Test
	@Transactional
	public void solvingNPlusOneProblem_JoinFetch() {
		List<Theme> themes = em
				.createNamedQuery("query_get_all_themes_join_fetch", Theme.class)
				.getResultList();
		for(Theme theme:themes){
			logger.info("Theme -> {} Users -> {}",theme, theme.getUsers());
		}
	}

}
