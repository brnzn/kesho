package com.kesho.crm.dto;

import java.util.Calendar;
import java.util.Date;

/**
 * Model class for a StudentDto.
 *
 * @author Marco Jakob
 */
public class StudentDto {
    private Long id;
    private String name;
    private String familyName;
    private String gender;
    private String yearOfBirth;
    private String mobileNumber;
    private String homeLocation;
    private boolean isActiveStudent;
    private boolean hasDisability;
    private boolean isSponsored;
    private Date startDate;
    /**
	 * Default constructor.
	 */
	public StudentDto() {
	}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGender() {
        return gender;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public boolean isActiveStudent() {
        return isActiveStudent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public void setActiveStudent(boolean activeStudent) {
        isActiveStudent = activeStudent;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isHasDisability() {
        return hasDisability;
    }

    public void setHasDisability(boolean hasDisability) {
        this.hasDisability = hasDisability;
    }

    public boolean isSponsored() {
        return isSponsored;
    }

    public void setSponsored(boolean sponsored) {
        isSponsored = sponsored;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public StudentDto withId(Long id) {
        this.id = id;
        return this;
    }

    public StudentDto withName(String name) {
        this.name = name;
        return this;
    }

    public StudentDto withFamilyName(String name) {
        this.familyName = name;
        return this;
    }

    public StudentDto withGender(String gender) {
        this.gender = gender;
        return this;
    }

    public StudentDto withYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
        return this;
    }

    public StudentDto withMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public StudentDto withHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
        return this;
    }

    public StudentDto isActiveStudent(boolean isActive) {
        this.isActiveStudent = isActive;
        return this;
    }

    public StudentDto hasDisability(boolean hasDisability) {
        this.hasDisability = hasDisability;
        return this;
    }

    public StudentDto isSponsored(boolean sponsored) {
        this.isSponsored = sponsored;
        return this;
    }

    public StudentDto withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }
}
