package com.kesho.datamart.repository;

import com.kesho.datamart.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FamilyDAO extends JpaRepository<Family, Long>{
    @Query("select f from Family f left join fetch f.students where f.id = :id")
    Family loadFamily(@Param("id") Long id);
}
