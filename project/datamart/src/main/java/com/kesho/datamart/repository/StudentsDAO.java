package com.kesho.datamart.repository;

import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentsDAO extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student>{
    @Query(value="select st from Student st join fetch st.family order by st.firstName ASC" , countQuery = "select count(t) from Student t")
    Page<Student> findWithFamily(Pageable p);

    @Query("select new com.kesho.datamart.repository.StudentVO(st.id, st.firstName, st.family.name) from Student st JOIN st.family f")
    List<StudentVO> all();
}
