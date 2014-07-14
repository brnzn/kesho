package com.kesho.datamart.repository;

import com.kesho.datamart.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentsDAO extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student>{
//    @Query("select st from Student st left join fetch st.educationHistory where st.id = :id")
//    Student findwithJoin(@Param("id") Long id);

    @Query(value="select st from Student st join fetch st.family order by st.firstName ASC" , countQuery = "select count(t) from Student t")
    Page<Student> findWithFamily(Pageable p);

    @Query(value="select st from Student st join fetch st.family")
    List<Student> findAll();

    @Modifying
    @Transactional
    @Query("delete from Student s where s.id = :studentId")
    void deleteByStudentId(@Param("studentId") Long id);


//    @Query(value="select st from Student st join fetch st.family" , countQuery = "select count(t) from Student t")
//    Page<Student> findWithFamily(Specification<Student> ab, Pageable pageSpecification);
}
