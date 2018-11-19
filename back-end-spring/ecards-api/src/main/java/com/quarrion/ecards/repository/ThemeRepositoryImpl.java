package com.quarrion.ecards.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.quarrion.ecards.model.ReviewV2;
import com.quarrion.ecards.model.ReviewRating;
import com.quarrion.ecards.model.Theme;

@Transactional
public class ThemeRepositoryImpl implements ThemeRepositoryCustom{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EntityManager em;

	public Theme findById(Long id) {
		Theme theme = em.find(Theme.class, id);
		logger.info("Theme -> {}", theme);
		return theme;
	}

	public Theme save(Theme theme) {

		if (theme.getId() == null) {
			em.persist(theme);
		} else {
			em.merge(theme);
		}

		return theme;
	}

	public void deleteById(Long id) {
		Theme theme = findById(id);
		em.remove(theme);
	}

	@Override
	public void playWithEntityManager() {
		Theme theme1 = new Theme("Happy Halloween");
		em.persist(theme1);
		
		Theme theme2 = findById(10001L);
		
		theme2.setName("Happy Halloween - Updated");
		
	}

	@Override
	public void addHardcodedReviewsForTheme() {
		//get the theme 10003
		Theme theme = findById(10003L);
		logger.info("theme.getReviews() -> {}", theme.getReviews());
		
		//add 2 reviews to it
		ReviewV2 review1 = new ReviewV2(ReviewRating.FIVE, "Awesome theme");	
		ReviewV2 review2 = new ReviewV2(ReviewRating.FIVE, "Beautiful");
		
		//setting the relationship
		theme.addReview(review1);
		review1.setTheme(theme);
		
		theme.addReview(review2);
		review2.setTheme(theme);
		
		//save it to the database
		em.persist(review1);
		em.persist(review2);
	}
	
	@Override
	public void addReviewsForTheme(Long themeId, List<ReviewV2> reviews) {		
		Theme theme = findById(themeId);
		logger.info("theme.getReviews() -> {}", theme.getReviews());
		for(ReviewV2 review:reviews)
		{			
			//setting the relationship
			theme.addReview(review);
			review.setTheme(theme);
			em.persist(review);
		}
	}
}
