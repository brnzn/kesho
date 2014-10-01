package com.kesho.datamart.service;

import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.dto.ContactDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.paging.Request;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SchoolService {
    //TODO: create helper class for all services
    List<SchoolDto> getAllSchools();

    Page<SchoolDto> getPage(Request request);
    SchoolDto save(SchoolDto institution);

    List<ContactDto> getContacts(Long schoolId);

    ContactDto save(ContactDto dto);

    ContactDetailDto save(ContactDetailDto cd);

    void deleteContact(Long contactId);
}
