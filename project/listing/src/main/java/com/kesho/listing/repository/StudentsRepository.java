package com.kesho.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kesho.listing.entity.Student;

public interface StudentsRepository extends JpaRepository<Student, Long>{

}
