package com.kesho.datamart.service;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.dto.StudentSponsorDto;
import com.kesho.datamart.paging.Request;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/23/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SponsorService {
    SponsorDto save(SponsorDto sponsor);

    Page<SponsorDto> getPage(Request request);

    void deleteSponsor(Long id);

    List<StudentSponsorDto> getStudentSponsors(Long studentId);

    SponsorDto findOne(Long sponsorId);
}
