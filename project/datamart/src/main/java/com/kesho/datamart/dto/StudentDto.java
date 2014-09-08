package com.kesho.datamart.dto;

import com.kesho.datamart.domain.*;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Model class for a StudentDto.
 *
 * @author Marco Jakob
 */
public class StudentDto implements Comparable, Dto {
    private String uuid = UUID.randomUUID().toString();
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String firstName;
    @NotNull(message = "Family is mandatory")
    private FamilyDto family;
    @NotNull(message = "Gender is mandatory")
    private Gender gender;
    @NotNull(message = "Year Of Birth is mandatory")
    @Min(value = 1980, message = "Year of Birth cannot be less than 1980") // TODO: what should be the min year?? convert to date to we can validate past/future
    private Integer yearOfBirth;
    private String mobileNumber;
    private Location homeLocation;
    private Boolean hasDisability;
    private Boolean financialSupport;
    private Boolean enrichmentSupport;
    private LocalDate startDate;
    private String email;
    private String facebookAddress;
    private LeaverStatus leaverStatus;
    private FinancialSupportStatus financialSupportStatus;
    private String financialSupportStatusDetails;
    private LevelOfSupport levelOfSupport;
//    private Integer alumniNumber;
    private Integer totalSRequired;
    private LocalDate endDate;
    private String financialSupportStatusSubDetails;
    private Integer version;

    /**
	 * Default constructor.
	 */
	public StudentDto() {
	}

    public Long getId() {
        return id;
    }

    public Integer getTotalSponsorshipRequired() {
        return totalSRequired;
    }

    public String getFirstName() {
        return firstName;
    }

    public FamilyDto getFamily() {
        return family;
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

    public Location getHomeLocation() {
        return homeLocation;
    }

    public Boolean hasDisability() {
        return hasDisability;
    }

    public Boolean hasFinancialSupport() {
        return financialSupport;
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

    public FinancialSupportStatus getFinancialSupportStatus() {
        return financialSupportStatus;
    }

    public String getFinancialSupportStatusDetails() {
        return financialSupportStatusDetails;
    }

    public LevelOfSupport getLevelOfSupport() {
        return levelOfSupport;
    }

//    public Integer getAlumniNumber() {
//        return alumniNumber;
//    }

    public Boolean getEnrichmentSupport() {
        return enrichmentSupport;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public StudentDto withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public StudentDto withId(Long id) {
        this.id = id;
        return this;
    }

    public StudentDto withName(String name) {
        this.firstName = name;
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

    public StudentDto withHomeLocation(Location homeLocation) {
        this.homeLocation = homeLocation;
        return this;
    }

    public StudentDto withHasDisability(Boolean hasDisability) {
        this.hasDisability = hasDisability;
        return this;
    }

    public StudentDto withFinancialSupport(Boolean financialSupport) {
        this.financialSupport = financialSupport;
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

    public StudentDto withLevelOfSupport(LevelOfSupport levelOfSupport) {
        this.levelOfSupport = levelOfSupport;
        return this;
    }

    public StudentDto withFamily(FamilyDto family) {
        this.family = family;
        return this;
    }

//    public StudentDto withAlumniNumber(Integer alumniNumber) {
//        this.alumniNumber = alumniNumber;
//        return this;
//    }

    public StudentDto withTotalSponsorshipRequired(Integer amount) {
        this.totalSRequired = amount;
        return this;
    }

    public StudentDto withFinancialSupportStatus(FinancialSupportStatus status) {
        this.financialSupportStatus = status;
        return this;
    }

    public StudentDto withFinancialSupportStatusDetails(String details) {
        this.financialSupportStatusDetails = details;
        return this;
    }

    public StudentDto withFinancialSupportStatusSubDetails(String details) {
        this.financialSupportStatusSubDetails = details;
        return this;
    }

    public StudentDto withEnrichmentSupport(Boolean value) {
        this.enrichmentSupport = value;
        return this;
    }

    public void setFamily(FamilyDto family) {
        this.family = family;
    }

    //For auto complete text
    @Override
    public String toString() {
        return getDisplay();
    }

    //For auto complete text
    @Override
    public int compareTo(Object o) {
        return this.getDisplay().compareTo(((StudentDto)o).getDisplay());
    }

    //TODO: cannot remember why hashcode/equals have been implemented....

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentDto that = (StudentDto) o;

        if (family != null ? !family.equals(that.family) : that.family != null) return false;
        if (gender != that.gender) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (yearOfBirth != null ? !yearOfBirth.equals(that.yearOfBirth) : that.yearOfBirth != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (family != null ? family.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (yearOfBirth != null ? yearOfBirth.hashCode() : 0);
        return result;
    }


    private String getDisplay() {
        return firstName + " " + family.getFamilyName();
    }

    public String getFinancialSupportStatusSubDetails() {
        return financialSupportStatusSubDetails;
    }

    public StudentDto withVersion(Integer version) {
        this.version = version;
        return this;
    }

    public Integer getVersion() {
        return version;
    }
}
