package com.kesho.datamart.repository;

import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.entity.EducationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EducationHistoryDAO extends JpaRepository<EducationHistory, Long>{

    @Query("select eh from EducationHistory eh JOIN fetch eh.school where eh.studentId = :studentId")
    List<EducationHistory> findByStudentId(@Param("studentId") Long studentId);

    //@Query("select eh from EducationHistory eh where eh.studentId = :studentId and eh.startDate in(select max(eh1.startDate) from EducationHistory eh1 where eh1.studentId = :studentId)")
    @Query("select new com.kesho.datamart.dto.EducationDto(eh.educationStatus, eh.startDate) from EducationHistory eh where eh.studentId = :studentId and eh.startDate in(select max(eh1.startDate) from EducationHistory eh1 where eh1.studentId = :studentId)")
    EducationDto findLatestEducation(@Param("studentId") Long studentId);

    @Modifying
    @Transactional
    @Query("delete from EducationHistory eh where eh.studentId = :studentId")
    void deleteByStudentId(@Param("studentId") Long id);

    @Modifying
    @Transactional
    @Query("delete from EducationHistory eh where eh.id = :id")
    void deleteById(@Param("id") Long id);


    @Query("select eh from EducationHistory eh JOIN fetch eh.school where eh.id = :id")
    EducationHistory findByIdWithSchool(@Param("id")Long id);

}
