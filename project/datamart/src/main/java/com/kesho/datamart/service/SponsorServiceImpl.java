package com.kesho.datamart.service;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.PageImpl;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.entity.Sponsor;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.SponsorsDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    private SponsorAssembler assembler = new SponsorAssembler();

    public SponsorDto save(SponsorDto sponsor) {
        return assembler.toDto(dao.save(assembler.toEntity(sponsor)));
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

    private Page<SponsorDto> toPageResult(final org.springframework.data.domain.Page<Sponsor> page, final Request request) {
        if(page.getTotalElements() > 0 && page.getTotalPages() <= request.getPageNumber()) {
            return new PageImpl<SponsorDto>().withError(String.format("Max pages is [%s]", page.getTotalPages())).withTotalPages(page.getTotalPages());
        }

        Page<SponsorDto> result = new PageImpl<SponsorDto>().withContent(assembler.toDto(page.getContent())).withTotalPages(page.getTotalPages()).withPageSize(page.getNumberOfElements());
        return result;
    }

}
