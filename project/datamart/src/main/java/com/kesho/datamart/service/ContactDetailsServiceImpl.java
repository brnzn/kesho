package com.kesho.datamart.service;

import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.entity.ContactDetail;
import com.kesho.datamart.repository.ContactDetailsDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/20/13
 * Time: 6:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("ContactDetailsService")
public class ContactDetailsServiceImpl implements ContactDetailsService {
    @Inject
    private ContactDetailsDAO contactDAO;

    private ContactDetailAssembler contactAssembler = new ContactDetailAssembler();

    @Override
    public List<ContactDetailDto> getContactsOf(Long ownerId) {
        return contactAssembler.toDto(contactDAO.findById(ownerId));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        contactDAO.deleteById(id);
    }

    @Override
    public ContactDetailDto save(ContactDetailDto dto) {
        return contactAssembler.toDto(doSave(dto));
    }

    //This method is needed for version property, which get updated only after the transaction commit
    //TODO: use aspectj so the method don't need to be public
    @Transactional(readOnly = false)
    public ContactDetail doSave(ContactDetailDto dto) {
        return contactDAO.save(contactAssembler.toEntity(dto));
    }
}
