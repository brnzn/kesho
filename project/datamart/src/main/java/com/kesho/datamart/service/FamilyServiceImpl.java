package com.kesho.datamart.service;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.PageImpl;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.Family;
import com.kesho.datamart.entity.Student;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.FamilyDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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
    private StudentsAssembler studentsAssembler = new StudentsAssembler();

    @Override
    public FamilyDto save(FamilyDto family) {
        return assembler.toDto(doSave(family));
    }

    //TODO: use aspectj so the method don't need to be public
    @Transactional(readOnly = false)
    public Family doSave(FamilyDto dto) {
        return dao.save(assembler.toFamily(dto));
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
    @Transactional(readOnly = false)
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public List<StudentDto> getFamilyStudents(Long familyId) {
        Family family = dao.loadFamily(familyId);
        return family != null ? studentsAssembler.toDto(family.getStudents()) : null;
    }

    private Page<FamilyDto> toPageResult(final org.springframework.data.domain.Page<Family> page, final Request request) {
        if(page.getTotalElements() > 0 && page.getTotalPages() <= request.getPageNumber()) {
            return new PageImpl<FamilyDto>().withError(String.format("Max pages is [%s]", page.getTotalPages())).withTotalPages(page.getTotalPages());
        }

        Page<FamilyDto> result = new PageImpl<FamilyDto>().withContent(assembler.toDto(page.getContent())).withTotalPages(page.getTotalPages()).withPageSize(page.getNumberOfElements());
        return result;
    }

}
