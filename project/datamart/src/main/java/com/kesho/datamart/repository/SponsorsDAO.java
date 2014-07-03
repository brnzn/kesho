package com.kesho.datamart.repository;

import com.kesho.datamart.dto.StudentSponsorDto;
import com.kesho.datamart.entity.School;
import com.kesho.datamart.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SponsorsDAO extends JpaRepository<Sponsor, Long>{
    @Modifying
    @Transactional
    @Query("delete from Sponsor s where s.id = :id")
    void deleteBySponsorId(@Param("id") Long id);

    @Query("select NEW com.kesho.datamart.dto.StudentSponsorDto(pa.sponsorId, pa.amount, sp.anonymous, pa.endDate, sp.surname, sp.name, sp.active) "
            + "from PaymentArrangement pa, Sponsor sp "
            + "where pa.studentId = :studentId and sp.id = pa.sponsorId" +
            " order by pa.endDate ASC")
    List<StudentSponsorDto> getStudentSponsors(@Param("studentId") Long studentId);
}
