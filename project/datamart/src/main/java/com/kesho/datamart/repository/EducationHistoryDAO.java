package com.kesho.datamart.repository;

import com.kesho.datamart.entity.EducationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationHistoryDAO extends JpaRepository<EducationHistory, Long>{

    List<EducationHistory> findByStudentId(Long studentId);
}
