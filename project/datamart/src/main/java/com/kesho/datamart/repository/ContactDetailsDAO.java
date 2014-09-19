package com.kesho.datamart.repository;

import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.entity.ContactDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/15/14
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ContactDetailsDAO extends JpaRepository<ContactDetail, Long> {
    @Query("select c from ContactDetail c where c.ownerId = :ownerId and c.type = :type")
    List<ContactDetail> findByIdAndType(@Param("ownerId") Long ownerId, @Param("type") ContactType type);

    @Query("select c from ContactDetail c where c.ownerId = :ownerId")
    List<ContactDetail> findById(@Param("ownerId") Long ownerId);

    @Modifying
    @Query("delete from ContactDetail c where c.id = :id")
    void deleteById(@Param("id") Long id);
}
