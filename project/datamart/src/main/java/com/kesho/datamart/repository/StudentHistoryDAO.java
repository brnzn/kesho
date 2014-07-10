package com.kesho.datamart.repository;

import com.kesho.datamart.entity.StudentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentHistoryDAO extends JpaRepository<StudentHistory, Long>{

    @Query("select sh from StudentHistory sh where sh.studentId = :studentId")
    List<StudentHistory> findByStudentId(@Param("studentId") Long studentId);
}
