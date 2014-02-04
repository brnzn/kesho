package com.kesho.datamart.service;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.PageImpl;
import com.kesho.datamart.entity.Family;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.FamilyDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 5:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class FamilyServiceImpl implements FamilyService {
    @Inject
    private FamilyDAO dao;
    private FamilyAssembler assembler = new FamilyAssembler();

    public FamilyDto save(FamilyDto family) {
        return assembler.toDto(dao.save(assembler.toFamily(family)));
    }

    @Override
    public List<FamilyDto> getFamilies() {
        return assembler.toDto(dao.findAll());
    }

    @Override
    public Page<FamilyDto> getPage(Request request) {
        List<String> errors = ValidationUtil.validate(request);

        if(errors != null) {
            return new PageImpl<FamilyDto>().withErrors(errors);
        }

        Pageable pageSpecification = new PageRequest(request.getPageNumber(), request.getPageSize());

        return toPageResult(dao.findAll(pageSpecification), request);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    private Page<FamilyDto> toPageResult(final org.springframework.data.domain.Page<Family> page, final Request request) {
        if(page.getTotalElements() > 0 && page.getTotalPages() <= request.getPageNumber()) {
            return new PageImpl<FamilyDto>().withError(String.format("Max pages is [%s]", page.getTotalPages())).withTotalPages(page.getTotalPages());
        }

        Page<FamilyDto> result = new PageImpl<FamilyDto>().withContent(assembler.toDto(page.getContent())).withTotalPages(page.getTotalPages()).withPageSize(page.getNumberOfElements());
        return result;
    }

}
