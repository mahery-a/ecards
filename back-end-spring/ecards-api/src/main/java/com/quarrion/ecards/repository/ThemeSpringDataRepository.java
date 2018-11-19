package com.quarrion.ecards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.quarrion.ecards.model.Theme;

@RepositoryRestResource(path="themes")   // -> SPRING DATA REST used to expose rest resources from repository - NOT RECOMMENDED FOR PRODUCTION JUST FOR QUICK TESTS/PROTOTYPE
public interface ThemeSpringDataRepository extends JpaRepository<Theme, Long> {
	List<Theme> findByNameAndId(String name, Long id);

	List<Theme> findByName(String name);

	List<Theme> countByName(String name);

	List<Theme> findByNameOrderByIdDesc(String name);

	List<Theme> deleteByName(String name);

	@Query("Select  t  From Theme t where name like 'Happy%'")
	List<Theme> themeWithHappyInName();

	@Query(value = "Select  *  From Theme t where name like 'Happy%'", nativeQuery = true)
	List<Theme> themeWithHappyInNameUsingNativeQuery();

	@Query(name = "query_get_happy_themes")
	List<Theme> themeWithHappyInNameUsingNamedQuery();
}
