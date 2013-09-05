package com.kesho.matrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kesho.matrix.entity.Student;

public interface StudentsRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student>{

}
