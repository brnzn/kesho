package com.kesho.datamart.dto;

import com.kesho.datamart.domain.Location;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyDto implements Comparable {
    private final Long id;
    @NotBlank(message = "Name is mandatory")
    private final String familyName;
    @NotNull(message = "Home location is mandatory")
    private Location homeLocation;
    @NotBlank(message = "Home sub location is mandatory")
    private String homeSubLocation;
    private String homeClusterId;
    @NotNull(message = "Number of parents alive is mandatory")
    @Min(value = 0, message = "Number of parents alive cannot be negative")
    private Integer aliveParents;
    private Boolean isMarried;
    @NotNull(message = "Number of non Kesho students in the house is mandatory")
    @Min(value = 0, message = "Number of non Kesho students in the house cannot be negative")
    private Integer numNonKeshoStudents;
    @Min(value = 0, message = "Number of wives cannot be negative")
    private Integer numOfWives;
    private String primaryCaretaker;
    private String mainContactName;
    private String mobileNumber;
    private Boolean isPhoneOwner;
    private String phoneOwnerName;
    @NotBlank(message = "Profile is mandatory")
    private String profile;
    @NotNull(message = "Number of adults in the house is mandatory")
    @Min(value = 0, message = "Number of adults in the house cannot be negative")
    private Integer numOfAdultsAtAddress;

    public FamilyDto(Long id, String name) {
        this.id = id;
        this.familyName = name;
    }

    public Long getId() {
        return id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public Location getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Location homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getHomeSubLocation() {
        return homeSubLocation;
    }

    public void setHomeSubLocation(String homeSubLocation) {
        this.homeSubLocation = homeSubLocation;
    }

    public String getHomeClusterId() {
        return homeClusterId;
    }

    public void setHomeClusterId(String homeClusterId) {
        this.homeClusterId = homeClusterId;
    }

    public Integer getAliveParents() {
        return aliveParents;
    }

    public void setAliveParents(Integer aliveParents) {
        this.aliveParents = aliveParents;
    }

    public Boolean getMarried() {
        return isMarried;
    }

    public void setMarried(Boolean married) {
        isMarried = married;
    }

    public Integer getNumNonKeshoStudents() {
        return numNonKeshoStudents;
    }

    public void setNumNonKeshoStudents(Integer numNonKeshoStudents) {
        this.numNonKeshoStudents = numNonKeshoStudents;
    }

    public Integer getNumOfWives() {
        return numOfWives;
    }

    public void setNumOfWives(Integer numOfWives) {
        this.numOfWives = numOfWives;
    }

    public String getPrimaryCaretaker() {
        return primaryCaretaker;
    }

    public void setPrimaryCaretaker(String primaryCaretaker) {
        this.primaryCaretaker = primaryCaretaker;
    }

    public String getMainContactName() {
        return mainContactName;
    }

    public void setMainContactName(String mainContactName) {
        this.mainContactName = mainContactName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Boolean getPhoneOwner() {
        return isPhoneOwner;
    }

    public void setPhoneOwner(Boolean phoneOwner) {
        isPhoneOwner = phoneOwner;
    }

    public String getPhoneOwnerName() {
        return phoneOwnerName;
    }

    public void setPhoneOwnerName(String phoneOwnerName) {
        this.phoneOwnerName = phoneOwnerName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getNumOfAdultsAtAddress() {
        return numOfAdultsAtAddress;
    }

    public void setNumOfAdultsAtAddress(Integer numOfAdultsAtAddress) {
        this.numOfAdultsAtAddress = numOfAdultsAtAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamilyDto familyDto = (FamilyDto) o;

        if (id != null ? !id.equals(familyDto.id) : familyDto.id != null) return false;
        if (familyName != null ? !familyName.equals(familyDto.familyName) : familyDto.familyName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (familyName != null ? familyName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return this.getFamilyName().compareTo( ((FamilyDto)o).getFamilyName() );
    }

    @Override
    public String toString() {
        return familyName;
    }
}
