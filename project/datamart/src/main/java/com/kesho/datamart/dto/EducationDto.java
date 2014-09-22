package com.kesho.datamart.dto;

import com.kesho.datamart.domain.EducationStatus;
import com.kesho.datamart.domain.EducationYear;
import com.kesho.datamart.domain.SubEducationStatus;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/5/13
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class EducationDto {
    private SchoolDto institution;
    @NotNull(message = "Date is mandatory")
    private LocalDate date;
    private EducationYear year;
    private String course;
    @NotNull(message = "Education Status is mandatory")
    private EducationStatus educationalStatus;
    private SubEducationStatus secondaryEducationStatus1;
    private SubEducationStatus secondaryEducationStatus2;
    private Long id;
    private Long studentId;
    private String comments;
    private LocalDate predictedEndDate;
    private Integer version;

    public EducationDto(){}

    public EducationDto(EducationStatus status, LocalDate date) {
        this.educationalStatus = status;
        this.date = date;
    }

    public Integer getVersion() {
        return version;
    }

    public Long getStudentId() {
        return  studentId;
    }

    public EducationDto withId(Long id) {
        this.id = id;
        return this;
    }

    public EducationDto withInstitution(SchoolDto institution) {
        this.institution = institution;
        return this;
    }

    public EducationDto withYear(EducationYear year) {
        this.year = year;
        return this;
    }

    public String getInstitutionName() {
        return institution != null ? institution.getName() : null;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getPredictedEndDate() {
        return predictedEndDate;
    }

    public String getEducationLevel() {
        return formEducationLevel();
    }

    private String formEducationLevel() {
        String level = "%s (%s)";
        if (educationalStatus != null && secondaryEducationStatus1 != null) {
            return String.format(level, educationalStatus, secondaryEducationStatus1);
        } else if (educationalStatus != null) {
            return educationalStatus.name();
        }

        return "";
    }

    public EducationYear getYear() {
        return year;
    }

    public String getCourse() {
        return course;
    }

    public EducationStatus getEducationalStatus() {
        return educationalStatus;
    }

    public SubEducationStatus getSecondaryEducationStatus1() {
        return secondaryEducationStatus1;
    }

    public SubEducationStatus getSecondaryEducationStatus2() {
        return secondaryEducationStatus2;
    }

    public SchoolDto getInstitution() {
        return institution;
    }

    public String getComments() {
        return comments;
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

    public EducationDto withSecondaryStatus1(SubEducationStatus status) {
        this.secondaryEducationStatus1 = status;
        return this;
    }

    public EducationDto withSecondaryStatus2(SubEducationStatus status) {
        this.secondaryEducationStatus2 = status;
        return this;
    }

    public Long getId() {
        return id;
    }

    public EducationDto withStudentId(long id) {
        this.studentId = id;
        return this;
    }

    public EducationDto withComments(String comments) {
        this.comments = comments;
        return this;
    }

    public EducationDto withPredictedEndDate(LocalDate endDate) {
        this.predictedEndDate = endDate;

        return this;
    }

    public EducationDto withVersion(Integer version) {
        this.version = version;
        return this;
    }
}
