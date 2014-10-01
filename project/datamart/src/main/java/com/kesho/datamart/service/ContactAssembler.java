package com.kesho.datamart.service;

import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.dto.ContactDto;
import com.kesho.datamart.entity.ContactDetail;
import com.kesho.datamart.entity.SchoolContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/22/14
 * Time: 5:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactAssembler {
    private ContactDetailAssembler contactDetailAssembler = new ContactDetailAssembler();

    public List<ContactDto> toDto(List<SchoolContact> schoolContacts) {
        List<ContactDto> contacts = new ArrayList<>(schoolContacts.size());
        for (SchoolContact contact:schoolContacts) {
            contacts.add(toDto(contact));
        }

        return contacts;
    }

    public ContactDto toDto(SchoolContact contact) {
        ContactDto dto = new ContactDto();
        dto.setId(contact.getId());
        dto.setSchoolId(contact.getSchoolId());
        dto.setName(contact.getName());
        dto.setSurname(contact.getSurname());
        dto.setJobTitle(contact.getJobTitle());
        dto.setTitle(contact.getTitle());

//        dto.setContactDetails(toContactDetails(contact));

        return dto;
    }

//    private List<ContactDetailDto> toContactDetails(SchoolContact contact) {
//        return contactDetailAssembler.toDto(contact.getContactDetails());
//    }
//
    public SchoolContact toEntity(ContactDto dto) {
        SchoolContact contact = new SchoolContact();
        contact.setSchoolId(dto.getSchoolId());
        contact.setId(dto.getId());
        contact.setJobTitle(dto.getJobTitle());
        contact.setName(dto.getName());
        contact.setSurname(dto.getSurname());
        contact.setTitle(dto.getTitle());
//        contact.setContactDetails(toEntity(dto.getContactDetails()));

        return contact;
    }

    private List<ContactDetail> toEntity(List<ContactDetailDto> contactDetails) {
        return contactDetailAssembler.toEntity(contactDetails);
    }
}
