package com.kesho.datamart.repository;

import com.kesho.datamart.entity.SchoolContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolContactsDAO extends JpaRepository<SchoolContact, Long>{
    List<SchoolContact> findBySchoolId(Long schoolId);

}
