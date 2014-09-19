package com.kesho.datamart.ui.repository;

import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.dto.ContactDetailDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/19/14
 * Time: 8:42 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ContactDetailsRepository {
    List<ContactDetailDto> getContacts(Long ownerId, ContactType type);
    void delete(Long id);
    ContactDetailDto save(ContactDetailDto dto);

}
