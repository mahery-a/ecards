package com.quarrion.ecards.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quarrion.ecards.model.Theme;

@Repository
@Transactional
public interface ThemeRepository extends JpaRepository<Theme,Long>, ThemeRepositoryCustom{
	
}