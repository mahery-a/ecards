package com.quarrion.ecards.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.quarrion.ecards.EcardsApiApplication;
import com.quarrion.ecards.model.Theme;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcardsApiApplication.class)
public class ThemeSpringDataRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ThemeSpringDataRepository repository;

	@Test
	public void findById_ThemePresent() {
		Optional<Theme> courseOptional = repository.findById(10001L);
		assertTrue(courseOptional.isPresent());
	}

	@Test
	public void findById_ThemeNotPresent() {
		Optional<Theme> courseOptional = repository.findById(20001L);
		assertFalse(courseOptional.isPresent());
	}

	@Test
	public void playingAroundWithSpringDataRepository() {
		//Theme course = new Theme("Happy birthday");
		//repository.save(course);

		//course.setName("Happy birthday - Updated");
		//repository.save(course);
		logger.info("Themes -> {} ", repository.findAll());
		logger.info("Count -> {} ", repository.count());
	}

	@Test
	public void sort() {
		Sort sort = new Sort(Sort.Direction.ASC, "name");//.and(additionalSort) to add additonal sort criterias
		logger.info("Sorted Themes -> {} ", repository.findAll(sort));
	}

	@Test
	public void pagination() {
		PageRequest pageRequest = PageRequest.of(0, 3);
		Page<Theme> firstPage = repository.findAll(pageRequest);
		logger.info("First Page -> {} ", firstPage.getContent());
		
		Pageable secondPageable = firstPage.nextPageable();
		Page<Theme> secondPage = repository.findAll(secondPageable);
		logger.info("Second Page -> {} ", secondPage.getContent());
	}
	
	@Test
	public void findUsingName() {
		logger.info("FindByName -> {} ", repository.findByName("Happy birthday"));
	}

	@Test
	public void findUsingUsersName() {
		logger.info("findUsingUsersName -> {} ", repository.findByName("Ranga"));
	}

}
