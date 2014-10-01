package com.kesho.datamart.service;

import com.kesho.datamart.dto.*;
import com.kesho.datamart.entity.School;
import com.kesho.datamart.entity.SchoolContact;
import com.kesho.datamart.entity.Sponsor;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.repository.ContactDetailsDAO;
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

    @Inject
    private ContactDetailsDAO contactDetailsDAO;

    private SchoolAssembler assembler = new SchoolAssembler();
    private ContactAssembler contactAssembler = new ContactAssembler();
    private ContactDetailAssembler contactDetailsAssembler = new ContactDetailAssembler();


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
        return contactAssembler.toDto(contactsDAO.findBySchoolId(schoolId));
    }

    @Override
    @Transactional
    public ContactDto save(ContactDto dto) {
        return contactAssembler.toDto(contactsDAO.save(contactAssembler.toEntity(dto)));
    }

    @Override
    @Transactional
    public ContactDetailDto save(ContactDetailDto cd) {
        return contactDetailsAssembler.toDto(contactDetailsDAO.save(contactDetailsAssembler.toEntity(cd)));
    }

    @Override
    @Transactional
    public void deleteContact(Long contactId) {
        contactsDAO.delete(contactId);
        contactDetailsDAO.deleteByOwner(contactId);
    }

    private Page<SchoolDto> toPageResult(final org.springframework.data.domain.Page<School> page, final Request request) {
        if(page.getTotalElements() > 0 && page.getTotalPages() <= request.getPageNumber()) {
            return new PageImpl<SchoolDto>().withError(String.format("Max pages is [%s]", page.getTotalPages())).withTotalPages(page.getTotalPages());
        }

        Page<SchoolDto> result = new PageImpl<SchoolDto>().withContent(assembler.toDto(page.getContent())).withTotalPages(page.getTotalPages()).withPageSize(page.getNumberOfElements());
        return result;
    }


}
