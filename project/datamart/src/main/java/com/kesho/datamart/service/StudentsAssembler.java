package com.kesho.datamart.service;

import com.google.common.collect.Lists;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.Student;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 11:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentsAssembler {
    //TODO: function
    public List<StudentDto> toDto(List<Student> students) {
        List<StudentDto> dtos = Lists.newArrayList();
        for (Student student : students) {
            dtos.add(toDto(student));
        }

        return dtos;
    }

    public StudentDto toDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.withName(student.getFirstName())
                .withId(student.getId())
                .withFamilyName(student.getSurname())
                .withGender(student.getGender())
                .withHasDisability(student.hasDisability())
                .withHomeLocation(student.getHomeLocation())
                .withStartDate(student.getStartDate())
                .withMobileNumber(student.getContactNumber())
                .withYearOfBirth(student.getYearOfBirth())
                .activeStudent(student.isActive())
                .sponsored(student.isSponsored())
                .withEmail(student.getEmail())
                .withFacebookAddress(student.getFacebookAddress())
                .withStatus(student.getStatus())
                .withSponsorStatus(student.getSponsorshipStatus())
                .withLevelOfSupport(student.getLevelOfSupport())
                .withTopupNeeded(student.isTopupNeeded())
                .withShortfall(student.getShortfall())
                .withAlumniNumber(student.getAlumniNumber())   ;

        return dto;
    }
}
