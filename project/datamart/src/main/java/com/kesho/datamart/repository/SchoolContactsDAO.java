package com.kesho.datamart.repository;

import com.kesho.datamart.entity.School;
import com.kesho.datamart.entity.SchoolContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolContactsDAO extends JpaRepository<SchoolContact, Long>{
    @Query("select c from SchoolContact c left join fetch c.contactDetails where c.id = :id")
    SchoolContact get(@Param("id") Long id);

//    @Query("select c from SchoolContact c left join fetch c.contactDetails where c.schoolId = :id")
//    List<SchoolContact> getSchoolContacts(@Param("id") Long schoolId);

    List<SchoolContact> findBySchoolId(Long schoolId);

}
