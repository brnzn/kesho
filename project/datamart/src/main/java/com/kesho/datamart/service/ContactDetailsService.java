package com.kesho.datamart.service;

import com.kesho.datamart.dto.ContactDetailDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/20/13
 * Time: 5:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ContactDetailsService {
    List<ContactDetailDto> getContactsOf(Long ownerId);

    void delete(Long id);

    ContactDetailDto save(ContactDetailDto dto);
}
