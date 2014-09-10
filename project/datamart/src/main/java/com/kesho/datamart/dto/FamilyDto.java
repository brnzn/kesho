package com.kesho.datamart.dto;

import com.kesho.datamart.domain.Location;
import org.hibernate.validator.constraints.NotBlank;

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
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String familyName;
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
    private Integer numOfChildrenAtAddress;
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
    private Integer version;

    public Long getId() {
        return id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public Location getHomeLocation() {
        return homeLocation;
    }

    public String getHomeSubLocation() {
        return homeSubLocation;
    }

    public String getHomeClusterId() {
        return homeClusterId;
    }

    public Integer getAliveParents() {
        return aliveParents;
    }

    public Boolean getMarried() {
        return isMarried;
    }

    public Integer getNumOfChildrenAtAddress() {
        return numOfChildrenAtAddress;
    }

    public Integer getNumOfWives() {
        return numOfWives;
    }

    public String getPrimaryCaretaker() {
        return primaryCaretaker;
    }

    public String getMainContactName() {
        return mainContactName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Boolean getPhoneOwner() {
        return isPhoneOwner;
    }

    public String getPhoneOwnerName() {
        return phoneOwnerName;
    }

    public String getProfile() {
        return profile;
    }

    public Integer getNumOfAdultsAtAddress() {
        return numOfAdultsAtAddress;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public FamilyDto withFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
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

    public FamilyDto withHomeLocation(Location location) {
        this.homeLocation = location;
        return  this;
    }

    public FamilyDto withHomeSubLocation(String homeSubLocation) {
        this.homeSubLocation = homeSubLocation;
        return this;
    }

    public FamilyDto withHomeClusterId(String homeClusterId) {
        this.homeClusterId = homeClusterId;
        return this;
    }

    public FamilyDto withAliveParents(Integer aliveParents) {
        this.aliveParents = aliveParents;
        return this;
    }

    public FamilyDto isMarried(Boolean isMarried) {
        this.isMarried = isMarried;
        return this;
    }

    public FamilyDto withNumOfChildrenAtAddress(Integer numOfChildren) {
        this.numOfChildrenAtAddress = numOfChildren;
        return this;
    }

    public FamilyDto withNumOfWives(Integer numOfWives) {
        this.numOfWives = numOfWives;
        return this;
    }

    public FamilyDto withPrimaryCaretaker(String primaryCaretaker) {
        this.primaryCaretaker = primaryCaretaker;
        return this;
    }

    public FamilyDto withMainContactName(String mainContactName) {
        this.mainContactName = mainContactName;
        return this;
    }

    public FamilyDto withMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }

    public FamilyDto isPhoneOwner(Boolean isPhoneOwner) {
        this.isPhoneOwner = isPhoneOwner;
        return this;
    }

    public FamilyDto withPhoneOwnerName(String phoneOwnerName) {
        this.phoneOwnerName = phoneOwnerName;
        return this;
    }

    public FamilyDto withProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public FamilyDto withNumOfAdultsAtAddress(Integer numOfAdultsAtAddress) {
        this.numOfAdultsAtAddress = numOfAdultsAtAddress;
        return this;
    }

    public FamilyDto withVersion(Integer version) {
        this.version = version;
        return this;
    }

    public FamilyDto withId(Long id) {
        this.id = id;
        return this;
    }
}
