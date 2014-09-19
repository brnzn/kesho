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
public class StudentContactAssembler {

    //TODO: function
    public List<ContactDetailDto> toDto(List<ContactDetail> contacts) {
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

//    public Student toStudent(StudentDto dto) {
//        Student student = new Student();
//        student.setVersion(dto.getVersion());
//        student.setId(dto.getId());
//        student.setFirstName(dto.getFirstName());
//        student.setGender(dto.getGender());
//        student.setHasDisability(dto.hasDisability());
//        student.setHomeLocation(dto.getHomeLocation());
//        student.setStartDate(dto.getStartDate())       ;
//        student.setContactNumber(dto.getMobileNumber());
//        student.setYearOfBirth(dto.getYearOfBirth());
//        student.setFinancialSupport(dto.hasFinancialSupport());
//
//        if(FinancialSupportStatus.EMPTY.name().equals(dto.getFinancialSupportStatusDetails())) {
//            student.setFinancialSupportStatusDetails(null);
//        } else {
//            student.setFinancialSupportStatusDetails(dto.getFinancialSupportStatusDetails());
//        }
//
//        student.setFinancialSupportStatusSubDetails(StringUtils.isBlank(dto.getFinancialSupportStatusSubDetails()) ? null : dto.getFinancialSupportStatusSubDetails());
//        student.setEmail(dto.getEmail());
//        student.setFacebookAddress(dto.getFacebookAddress());
//        student.setLeaverStatus(dto.getLeaverStatus());
//
//        if(FinancialSupportStatus.EMPTY == dto.getFinancialSupportStatus()) {
//            student.setFinancialSupportStatus(null);
//        } else {
//            student.setFinancialSupportStatus(dto.getFinancialSupportStatus());
//        }
//        student.setLevelOfSupport(dto.getLevelOfSupport());
//        //student.setAlumniNumber(dto.getAlumniNumber());
//        student.setEnrichmentSupport(dto.getEnrichmentSupport());
//        student.setTotalSponsorshipRequired(dto.getTotalSponsorshipRequired());
//        student.setEndDate(dto.getEndDate());
//        return student;
//    }
}
