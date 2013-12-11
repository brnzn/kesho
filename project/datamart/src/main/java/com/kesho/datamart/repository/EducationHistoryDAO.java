package com.kesho.datamart.repository;

import com.kesho.datamart.entity.EducationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EducationHistoryDAO extends JpaRepository<EducationHistory, Long>{

    @Query("select eh from EducationHistory eh JOIN fetch eh.school where eh.studentId = :studentId")
    List<EducationHistory> findByStudentId(@Param("studentId")Long studentId);
}
