package com.kesho.datamart.repository;

import com.kesho.datamart.entity.School;
import com.kesho.datamart.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorsDAO extends JpaRepository<Sponsor, Long>{

}
