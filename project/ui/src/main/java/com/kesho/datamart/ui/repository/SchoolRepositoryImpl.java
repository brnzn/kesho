package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.dto.ContactDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.paging.Request;
import com.kesho.datamart.service.ContactDetailsService;
import com.kesho.datamart.service.SchoolService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("SchoolRepository")
public class SchoolRepositoryImpl implements SchoolRepository {
    @Inject
    private SchoolService contactService;
    @Inject
    private ContactDetailsService contactDetailsService;

    @Override
    public SchoolDto save(SchoolDto institution) {
        return contactService.save(institution);
    }

    @Override
    public ContactDto save(ContactDto dto) {
        return contactService.save(dto);
    }

    @Override
    public List<ContactDto> getContactsFor(Long id) {
        return contactService.getContacts(id);
    }

    @Override
    public void deleteContact(Long contactId) {
        contactService.deleteContact(contactId);
    }

    @Override
    public ContactDetailDto save(ContactDetailDto dto) {
        return contactDetailsService.save(dto);
    }

    @Override
    public List<ContactDetailDto> getContactDetailsOf(Long contactId) {
        return contactDetailsService.getContactsOf(contactId);
    }

    @Override
    public void deleteContactDetail(Long contactDetailId) {
        contactDetailsService.delete(contactDetailId);
    }

    @Override
    public Page<SchoolDto> getPage(int page, int pageSize) {
        return contactService.getPage(new Request(page, pageSize));
    }

    @Override
    public List<SchoolDto> getAllSchools() {
        return contactService.getAllSchools();
    }
}
