package com.kesho.datamart.dto;

import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.domain.LeaverStatus;
import org.joda.time.LocalDate;

/**
 * Model class for a StudentDto.
 *
 * @author Marco Jakob
 */
public class StudentDto {
    private Long id;
    private String name;
    private String surname;
    private Gender gender;
    private Integer yearOfBirth;
    private String mobileNumber;
    private String homeLocation;
    private Boolean isActiveStudent;
    private Boolean hasDisability;
    private Boolean isSponsored;
    private LocalDate startDate;
    private String email;
    private String facebookAddress;
    private LeaverStatus leaverStatus;
    private String sponsorshipStatus;
    private String levelOfSupport;
    private Boolean topupNeeded;
    private Integer shortfall;
    private Integer alumniNumber;

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

    public String getSurname() {
        return surname;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public Boolean isActiveStudent() {
        return isActiveStudent;
    }

    public Boolean hasDisability() {
        return hasDisability;
    }

    public Boolean isSponsored() {
        return isSponsored;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getEmail() {
        return email;
    }

    public String getFacebookAddress() {
        return facebookAddress;
    }

    public LeaverStatus getLeaverStatus() {
        return leaverStatus;
    }

    public String getSponsorshipStatus() {
        return sponsorshipStatus;
    }

    public String getLevelOfSupport() {
        return levelOfSupport;
    }

    public Boolean isTopupNeeded() {
        return topupNeeded;
    }

    public Integer getShortfall() {
        return shortfall;
    }

    public Integer getAlumniNumber() {
        return alumniNumber;
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
        this.surname = name;
        return this;
    }

    public StudentDto withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public StudentDto withYearOfBirth(Integer yearOfBirth) {
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

    public StudentDto activeStudent(Boolean isActive) {
        this.isActiveStudent = isActive;
        return this;
    }

    public StudentDto withHasDisability(Boolean hasDisability) {
        this.hasDisability = hasDisability;
        return this;
    }

    public StudentDto sponsored(Boolean sponsored) {
        this.isSponsored = sponsored;
        return this;
    }

    public StudentDto withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public StudentDto withEmail(String email) {
        this.email = email;
        return this;
    }

    public StudentDto withFacebookAddress(String facebookAddress) {
        this.facebookAddress = facebookAddress;
        return this;
    }

    public StudentDto withLeaverStatus(LeaverStatus leaverStatus) {
        this.leaverStatus = leaverStatus;
        return this;
    }

    public StudentDto withSponsorStatus(String sponsorStatus) {
        this.sponsorshipStatus = sponsorStatus;
        return this;
    }

    public StudentDto withLevelOfSupport(String levelOfSupport) {
        this.levelOfSupport = levelOfSupport;
        return this;
    }

    public StudentDto withTopupNeeded(Boolean topupNeeded) {
        this.topupNeeded = topupNeeded;
        return this;
    }

    public StudentDto withShortfall(Integer shortfall) {
        this.shortfall = shortfall;
        return this;
    }

    public StudentDto withAlumniNumber(Integer alumniNumber) {
        this.alumniNumber = alumniNumber;
        return this;
    }
}
