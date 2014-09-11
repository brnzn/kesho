package com.kesho.datamart.service;

import com.kesho.datamart.dto.*;
import com.kesho.datamart.entity.Sponsor;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.PaymentArrangementDao;
import com.kesho.datamart.repository.SponsorsDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/23/13
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class SponsorServiceImpl implements SponsorService {
    @Inject
    private SponsorsDAO dao;
    @Inject
    private PaymentArrangementDao paymentArrangementDao;

    private SponsorAssembler assembler = new SponsorAssembler();

    @Override
    public SponsorDto save(SponsorDto sponsor) {
        return assembler.toDto(doSave(sponsor));
    }

    //This method is needed for version property, which get updated only after the transaction commit
    //TODO: use aspectj so the method don't need to be public
    @Transactional(readOnly = false)
    public Sponsor doSave(SponsorDto dto) {
        return dao.save(assembler.toEntity(dto));
    }

    @Override
    public Page<SponsorDto> getPage(Request request) {
        List<String> errors = ValidationUtil.validate(request);

        if(errors != null) {
            return new PageImpl<SponsorDto>().withErrors(errors);
        }

        Pageable pageSpecification = new PageRequest(request.getPageNumber(), request.getPageSize());

        return toPageResult(dao.findAll(pageSpecification), request);
    }

    @Transactional
    @Override
    public void deleteSponsor(Long id) {
        paymentArrangementDao.deleteBySponsorId(id);
        dao.deleteBySponsorId(id);

    }

    @Override
    public List<StudentSponsorDto> getStudentSponsors(Long studentId) {
        return dao.getStudentSponsors(studentId);
    }

    @Override
    public SponsorDto findOne(Long sponsorId) {
        return assembler.toDto(dao.findOne(sponsorId));
    }

    private Page<SponsorDto> toPageResult(final org.springframework.data.domain.Page<Sponsor> page, final Request request) {
        if(page.getTotalElements() > 0 && page.getTotalPages() <= request.getPageNumber()) {
            return new PageImpl<SponsorDto>().withError(String.format("Max pages is [%s]", page.getTotalPages())).withTotalPages(page.getTotalPages());
        }

        Page<SponsorDto> result = new PageImpl<SponsorDto>().withContent(assembler.toDto(page.getContent())).withTotalPages(page.getTotalPages()).withPageSize(page.getNumberOfElements());
        return result;
    }

}
