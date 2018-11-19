package com.quarrion.ecards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.quarrion.ecards.model.Ecard;

@Repository
@Transactional
public interface EcardRepository extends JpaRepository<Ecard, Integer>{

}
