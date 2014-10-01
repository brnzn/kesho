package com.kesho.datamart.dto;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/19/14
 * Time: 10:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactDto implements Dto {
    private String name;
    private String surname;
    public String title;
    private String jobTitle;
    private List<ContactDetailDto> contactDetails;
    private Long id;
    private Long schoolId;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public List<ContactDetailDto> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(List<ContactDetailDto> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public long getSchoolId() {
        return schoolId;
    }
}
