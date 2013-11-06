package com.kesho.datamart.repository;

import com.kesho.datamart.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentsRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student>{

}
