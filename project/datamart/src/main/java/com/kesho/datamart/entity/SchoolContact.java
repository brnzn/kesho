package com.kesho.datamart.entity;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/22/14
 * Time: 7:30 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "CONTACT")
public class SchoolContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SCHOOL_ID", nullable = false)
    private Long schoolId;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "JOB_TITLE", nullable = false)
    private String jobTitle;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true )
//    @JoinColumn(name="OWNER_ID",nullable = false, updatable = false)
//    @Fetch(FetchMode.SUBSELECT)
//    private List<ContactDetail> contactDetails;

//    public List<ContactDetail> getContactDetails() {
//        return contactDetails;
//    }
//
//    public void setContactDetails(List<ContactDetail> contactDetails) {
//        this.contactDetails = contactDetails;
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
