package com.kesho.crm.dto;

import java.util.Calendar;

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

    public StudentDto withId(Long id) {
        this.id = id;
        return this;
    }

    public StudentDto withName(String name) {
        this.name = name;
        return this;
    }
}
