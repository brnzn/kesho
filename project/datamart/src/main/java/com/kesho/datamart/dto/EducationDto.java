package com.kesho.datamart.dto;

import com.kesho.datamart.domain.EducationStatus;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/5/13
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class EducationDto {
    private InstitutionDto institution;
    private LocalDate date;
    private Integer year;
    private String course;
    private EducationStatus educationalStatus;
    private String secondaryEducationStatus1;
    private String secondaryEducationStatus2;
    private Long id;
    private Long studentId;

    public Long getStudentId() {
        return  studentId;
    }

//    public Long getInstitutionId() {
//        return institutionId;
//    }

    public EducationDto withId(Long id) {
        this.id = id;
        return this;
    }

    public EducationDto withInstitution(InstitutionDto institution) {
        this.institution = institution;
        return this;
    }

    public EducationDto withYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getInstitutionName() {
        return institution.getName();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getEducationLevel() {
        return formEducationLevel();
    }

    private String formEducationLevel() {
        String level = "%s (%s)";
        if (educationalStatus != null && StringUtils.isNotBlank(secondaryEducationStatus1)) {
            return String.format(level, educationalStatus, secondaryEducationStatus1);
        } else if (educationalStatus != null) {
            return educationalStatus.name();
        }

        return "";
    }

    public Integer getYear() {
        return year;
    }

    public String getCourse() {
        return course;
    }

    public EducationStatus getEducationalStatus() {
        return educationalStatus;
    }

    public String getSecondaryEducationStatus1() {
        return secondaryEducationStatus1;
    }

    public String getSecondaryEducationStatus2() {
        return secondaryEducationStatus2;
    }

    public InstitutionDto getInstitution() {
        return institution;
    }

    public EducationDto withEducationDate(LocalDate educationDate) {
        this.date = educationDate;
        return this;
    }

    public EducationDto withEducationalStatus(EducationStatus educationalStatus) {
        this.educationalStatus = educationalStatus;
        return this;
    }

    public EducationDto withCourse(String course) {
        this.course = course;
        return this;
    }

    public EducationDto withSecondaryStatus1(String status) {
        this.secondaryEducationStatus1 = status;
        return this;
    }

    public EducationDto withSecondaryStatus2(String status) {
        this.secondaryEducationStatus2 = status;
        return this;
    }

    public Long getId() {
        return id;
    }

//    public EducationDto withInstitutionId(Long id) {
//        this.institutionId = id;
//        return this;
//    }

    public EducationDto withStudentId(long id) {
        this.studentId = id;
        return this;
    }

}
