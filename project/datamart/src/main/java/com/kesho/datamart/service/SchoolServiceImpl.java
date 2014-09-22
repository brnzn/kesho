package com.kesho.datamart.service;

import com.kesho.datamart.dto.*;
import com.kesho.datamart.entity.School;
import com.kesho.datamart.entity.SchoolContact;
import com.kesho.datamart.entity.Sponsor;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.SchoolContactsDAO;
import com.kesho.datamart.repository.SchoolsDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class SchoolServiceImpl implements SchoolService {
    @Inject
    private SchoolsDAO schoolsDAO;
    @Inject
    private SchoolContactsDAO contactsDAO;

    private SchoolAssembler assembler = new SchoolAssembler();
    private ContactAssembler contactAssembler = new ContactAssembler();


    @Override
    public List<SchoolDto> getAllSchools() {
        return assembler.toDto(schoolsDAO.findAll());
    }

    @Override
    public Page<SchoolDto> getPage(Request request) {
        List<String> errors = ValidationUtil.validate(request);

        if(errors != null) {
            return new PageImpl<SchoolDto>().withErrors(errors);
        }

        Pageable pageSpecification = new PageRequest(request.getPageNumber(), request.getPageSize());

        return toPageResult(schoolsDAO.findAll(pageSpecification), request);
    }

    @Override
    @Transactional
    public SchoolDto save(SchoolDto dto) {
        return assembler.toDto(schoolsDAO.save(assembler.toEntity(dto)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> getContacts(Long schoolId) {
        List<SchoolContact> contacts = contactsDAO.findBySchoolId(schoolId);
        System.out.println(contacts.get(0).getContactDetails().get(0).getComments());
        return null;
    }


    private Page<SchoolDto> toPageResult(final org.springframework.data.domain.Page<School> page, final Request request) {
        if(page.getTotalElements() > 0 && page.getTotalPages() <= request.getPageNumber()) {
            return new PageImpl<SchoolDto>().withError(String.format("Max pages is [%s]", page.getTotalPages())).withTotalPages(page.getTotalPages());
        }

        Page<SchoolDto> result = new PageImpl<SchoolDto>().withContent(assembler.toDto(page.getContent())).withTotalPages(page.getTotalPages()).withPageSize(page.getNumberOfElements());
        return result;
    }


}
