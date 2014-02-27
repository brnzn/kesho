package com.kesho.datamart.repository;

import com.kesho.datamart.entity.School;
import com.kesho.datamart.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SponsorsDAO extends JpaRepository<Sponsor, Long>{
    @Modifying
    @Transactional
    @Query("delete from Sponsor s where s.id = :id")
    void deleteBySponsorId(@Param("id") Long id);
}
