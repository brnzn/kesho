package com.kesho.datamart.repository;

import com.kesho.datamart.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolsDAO extends JpaRepository<School, Long>{

}
