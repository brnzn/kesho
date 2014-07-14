package com.kesho.datamart.service;

import com.kesho.datamart.domain.FinancialSupportStatus;
import com.kesho.datamart.domain.FinancialSupportStatusDetails;
import com.kesho.datamart.dto.StudentDto;
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
public class StudentsAssembler {
    private FamilyAssembler assembler = new FamilyAssembler();

    //TODO: function
    public List<StudentDto> toDto(List<Student> students) {
        List<StudentDto> dtos = new ArrayList<>();
        for (Student student : students) {
            dtos.add(toDto(student));
        }

        return dtos;
    }

    public StudentDto toDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.withName(student.getFirstName())
                .withId(student.getId())
                .withFamily(assembler.toDto(student.getFamily()))
                .withGender(student.getGender())
                .withHasDisability(student.hasDisability())
                .withHomeLocation(student.getHomeLocation())
                .withStartDate(student.getStartDate())
                .withMobileNumber(student.getContactNumber())
                .withYearOfBirth(student.getYearOfBirth())
                .withFinancialSupport(student.hasFinancialSupport())
                .withFinancialSupportStatus(student.getFinancialSupportStatus() != null ? student.getFinancialSupportStatus() : FinancialSupportStatus.EMPTY)
                .withFinancialSupportStatusDetails(student.getFinancialSupportStatusDetails() == null ? FinancialSupportStatusDetails.EMPTY.name() : student.getFinancialSupportStatusDetails())
                .withEmail(student.getEmail())
                .withFacebookAddress(student.getFacebookAddress())
                .withLeaverStatus(student.getLeaverStatus())
                .withLevelOfSupport(student.getLevelOfSupport())
                .withEnrichmentSupport(student.getEnrichmentSupport())
                .withTopupNeeded(student.isTopupNeeded())
                .withShortfall(student.getShortfall())
                //.withAlumniNumber(student.getAlumniNumber())
                .withTotalSponsorshipRequired(student.getTotalSponsorshipRequired())
                .withEndDate(student.getEndDate())
                .withFinancialSupportStatusSubDetails(student.getFinancialSupportStatusSubDetails())
        ;

        return dto;
    }

    //TODO: properties names should be the same
    public Student toStudent(StudentDto dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getFirstName());
        student.setGender(dto.getGender());
        student.setHasDisability(dto.hasDisability());
        student.setHomeLocation(dto.getHomeLocation());
        student.setStartDate(dto.getStartDate())       ;
        student.setContactNumber(dto.getMobileNumber());
        student.setYearOfBirth(dto.getYearOfBirth());
        student.setFinancialSupport(dto.hasFinancialSupport());

        if(FinancialSupportStatus.EMPTY.name().equals(dto.getFinancialSupportStatusDetails())) {
            student.setFinancialSupportStatusDetails(null);
        } else {
            student.setFinancialSupportStatusDetails(dto.getFinancialSupportStatusDetails());
        }

        student.setFinancialSupportStatusSubDetails(StringUtils.isBlank(dto.getFinancialSupportStatusSubDetails()) ? null : dto.getFinancialSupportStatusSubDetails());
        student.setEmail(dto.getEmail());
        student.setFacebookAddress(dto.getFacebookAddress());
        student.setLeaverStatus(dto.getLeaverStatus());

        if(FinancialSupportStatus.EMPTY == dto.getFinancialSupportStatus()) {
            student.setFinancialSupportStatus(null);
        } else {
            student.setFinancialSupportStatus(dto.getFinancialSupportStatus());
        }
        student.setLevelOfSupport(dto.getLevelOfSupport());
        student.setTopupNeeded(dto.isTopupNeeded());
        student.setShortfall(dto.getShortfall());
        //student.setAlumniNumber(dto.getAlumniNumber());
        student.setTopupNeeded(dto.isTopupNeeded());
        student.setEnrichmentSupport(dto.getEnrichmentSupport());
        student.setTotalSponsorshipRequired(dto.getTotalSponsorshipRequired());
        student.setEndDate(dto.getEndDate());
        return student;
    }
}
