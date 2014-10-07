package com.kesho.datamart.repository;

import com.kesho.datamart.entity.FamilyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/15/14
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FamilyProfileDAO extends JpaRepository<FamilyProfile, Long> {
    @Query("select p from FamilyProfile p where p.familyId = :familyId")
    List<FamilyProfile> findByFamilyId(@Param("familyId") Long familyId);

    @Modifying
    @Transactional
    @Query("delete from FamilyProfile fp where fp.id = :id")
    void deleteById(@Param("id") Long id);
}
