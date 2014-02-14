package com.kesho.datamart.service;

import com.google.common.collect.Lists;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.entity.Family;
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
    private FamilyAssembler assembler = new FamilyAssembler();

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
                .withFamily(assembler.toDto(student.getFamily()))
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
                .withLeaverStatus(student.getLeaverStatus())
                .withSponsorStatus(student.getSponsorshipStatus())
                .withLevelOfSupport(student.getLevelOfSupport())
                .withTopupNeeded(student.isTopupNeeded())
                .withShortfall(student.getShortfall())
                .withAlumniNumber(student.getAlumniNumber())   ;

        return dto;
    }

    //TODO: properties names should be the same
    public Student toStudent(StudentDto dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setFirstName(dto.getName());
        student.setGender(dto.getGender());
        student.setHasDisability(dto.hasDisability());
        student.setHomeLocation(dto.getHomeLocation());
        student.setStartDate(dto.getStartDate())       ;
        student.setContactNumber(dto.getMobileNumber());
        student.setYearOfBirth(dto.getYearOfBirth());
        student.setActive(dto.isActiveStudent());
        student.setSponsored(dto.isSponsored());
        student.setEmail(dto.getEmail());
        student.setFacebookAddress(dto.getFacebookAddress());
        student.setLeaverStatus(dto.getLeaverStatus());
        student.setSponsorshipStatus(dto.getSponsorshipStatus());
        student.setLevelOfSupport(dto.getLevelOfSupport());
        student.setTopupNeeded(dto.isTopupNeeded());
        student.setShortfall(dto.getShortfall());
        student.setAlumniNumber(dto.getAlumniNumber());
        student.setTopupNeeded(dto.isTopupNeeded());
        return student;
    }
}
