package com.kesho.datamart.service;

import com.kesho.datamart.domain.FinancialSupportStatus;
import com.kesho.datamart.domain.FinancialSupportStatusDetails;
import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.ContactDetail;
import com.kesho.datamart.entity.Student;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 11:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactDetailAssembler {

    //TODO: function
    public List<ContactDetailDto> toDto(List<ContactDetail> contacts) {
        if (contacts == null) return null;  // TODO: use optional

        List<ContactDetailDto> dtos = new ArrayList<>();
        for (ContactDetail contact : contacts) {
            dtos.add(toDto(contact));
        }

        return dtos;
    }

    public ContactDetailDto toDto(ContactDetail contactDetail) {
        ContactDetailDto dto = new ContactDetailDto();
        dto.withId(contactDetail.getId())
           .withType(contactDetail.getType())
           .withOwnerId(contactDetail.getOwner())
           .withValue(contactDetail.getValue())
           .withComments(contactDetail.getComments());

        return dto;
    }

    public ContactDetail toEntity(ContactDetailDto dto) {
        ContactDetail detail = new ContactDetail();
        detail.setId(dto.getId());
        detail.setOwner(dto.getOwnerId());
        detail.setComments(dto.getComments());
        detail.setType(dto.getType());
        detail.setValue(dto.getValue());
        return detail;
    }

    public List<ContactDetail> toEntity(List<ContactDetailDto> contactDetails) {
        if(contactDetails == null) { // TODO: use optional
            return null;
        }

        List<ContactDetail> details = new ArrayList<>(contactDetails.size());

        for (ContactDetailDto dto:contactDetails) {
            details.add(toEntity(dto));
        }

        return details;
    }
}
