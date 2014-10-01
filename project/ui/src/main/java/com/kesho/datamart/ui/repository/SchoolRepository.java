package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.dto.ContactDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SchoolDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SchoolRepository {
    Page<SchoolDto> getPage(int page, int pageSize);
    List<SchoolDto> getAllSchools();
    SchoolDto save(SchoolDto dto);

    ContactDto save(ContactDto dto);

    List<ContactDto> getContactsFor(Long id);

    void deleteContact(Long contactId);

    ContactDetailDto save(ContactDetailDto dto);

    List<ContactDetailDto> getContactDetailsOf(Long contactId);

    void deleteContactDetail(Long contactDetailId);
}
