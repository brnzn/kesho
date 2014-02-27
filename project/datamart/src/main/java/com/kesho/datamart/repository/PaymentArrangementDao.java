package com.kesho.datamart.repository;

import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.entity.PaymentArrangement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/24/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PaymentArrangementDao extends JpaRepository<PaymentArrangement, Long> {
    @Query("select NEW com.kesho.datamart.dto.PaymentArrangementDto(pa.id, pa.studentId, pa.sponsorId, pa.startDate, pa.endDate, pa.type, pa.amount, st.firstName, st.family.name) "
            + "from PaymentArrangement pa, Student st JOIN st.family f "
            + "where pa.sponsorId = :sponsorId and pa.studentId = st.id" +
            " order by pa.endDate ASC")
    List<PaymentArrangementDto> findBySponsorId(@Param("sponsorId") Long sponsorId);

    @Modifying
    @Transactional
    @Query("delete from PaymentArrangement pa where pa.sponsorId = :sponsorId")
    void deleteBySponsorId(@Param("sponsorId") Long id);
}
