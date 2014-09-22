package com.kesho.datamart.service;

import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.entity.EducationHistory;
import com.kesho.datamart.entity.School;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/18/13
 * Time: 11:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchoolAssembler {
    public List<SchoolDto> toDto(List<School> schools) {
        List<SchoolDto> dtos = new ArrayList<>();
        for (School school : schools) {
            dtos.add(toDto(school));
        }

        return dtos;
    }

    public SchoolDto toDto(School school) {
        return new SchoolDto(school.getId(), school.getName())
                .withCounty(school.getCounty())
                .withCountry(school.getCountry())
                .withAddressLine1(school.getAddressLine1())
                .withAddressLine2(school.getAddressLine2())
                .withAddressLine3(school.getAddressLine3())
                .withPostcode(school.getPostcode());
    }

    public School toEntity(SchoolDto dto) {
        School school = new School();
        school.setName(dto.getName());
        school.setCounty(dto.getCounty());
        school.setCountry(dto.getCountry());
        school.setAddressLine1(dto.getAddressLine1());
        school.setAddressLine2(dto.getAddressLine2());
        school.setAddressLine3(dto.getAddressLine3());
        school.setPostcode(dto.getPostcode());
        return school;
    }

    private SchoolDto createSchool(final EducationHistory log) {
        School school = log.getSchool();
        if(school != null) {
            return new SchoolDto(school.getId(), school.getName());
        } else {
            return null;
        }

    }
}
