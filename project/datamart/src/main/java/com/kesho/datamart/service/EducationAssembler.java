package com.kesho.datamart.service;

import com.kesho.datamart.dto.EducationDto;
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
public class EducationAssembler {
    //TODO: function
    public List<EducationDto> toDto(List<EducationHistory> logs) {
        List<EducationDto> dtos = new ArrayList<>();
        for (EducationHistory log : logs) {
            dtos.add(toDto(log));
        }

        return dtos;
    }

    public EducationDto toDto(EducationHistory log) {
        EducationDto dto = new EducationDto()
                .withId(log.getId())
                .withVersion(log.getVersion())
                .withCourse(log.getCourse())
                .withSecondaryStatus1(log.getSecondaryLevel1())
                .withSecondaryStatus2(log.getSecondaryLevel2())
                .withStudentId(log.getStudentId())
                .withInstitution(createSchool(log))
                .withEducationalStatus(log.getEducationStatus())
                .withYear(log.getYear())
                .withEducationDate(log.getStartDate())
                .withComments(log.getComments())
                .withPredictedEndDate(log.getPredictedEndDate());
        return dto;
    }

    public EducationHistory toLog(EducationDto dto) {
        EducationHistory log = new EducationHistory();
        log.setVersion(dto.getVersion());
        log.setId(dto.getId());
        log.setCourse(dto.getCourse());
        log.setEducationStatus(dto.getEducationalStatus());
        log.setStartDate(dto.getDate());
        log.setStudentId(dto.getStudentId());
        log.setSecondaryLevel1(dto.getSecondaryEducationStatus1());
        log.setSecondaryLevel2(dto.getSecondaryEducationStatus2());
        log.setYear(dto.getYear());
        log.setComments(dto.getComments());
        log.setPredictedEndDate(dto.getPredictedEndDate());
        return log;
    }

    private SchoolDto createSchool(final EducationHistory log) {
        School school = log.getSchool();
        if(school != null) {
            return new SchoolDto(school.getId()).withName(school.getName());
        } else {
            return null;
        }

    }
}
