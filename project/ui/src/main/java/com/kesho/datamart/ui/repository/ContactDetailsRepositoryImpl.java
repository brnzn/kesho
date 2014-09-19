package com.kesho.datamart.ui.repository;

import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.service.ContactDetailsService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 11:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("ContactDetailsRepositoryImpl")
public class ContactDetailsRepositoryImpl implements ContactDetailsRepository {
    @Inject
    private ContactDetailsService contactDetailsService;

    @Override
    public List<ContactDetailDto> getContacts(Long ownerId, ContactType type) {
//        switch(type) {
//            case S:
                return contactDetailsService.getContactsOf(ownerId);
//            default: return Collections.emptyList();
//        }
    }

    @Override
    public void delete(Long id) {
        contactDetailsService.delete(id);
    }

    @Override
    public ContactDetailDto save(ContactDetailDto dto) {
        return contactDetailsService.save(dto);
    }

}
