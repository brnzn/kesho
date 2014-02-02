package com.kesho.datamart.dto;

import com.kesho.datamart.domain.Location;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyDto implements Comparable {
    private final Long id;
    private final String name;
    private Location homeLocation;
    private String homeSubLocation;
    private String homeClusterId;
    private Integer aliveParents;
    private Boolean isMarried;
    private Integer numNonKeshoStudents;
    private Integer numOfWives;
    private String primaryCaretaker;
    private String mainContactName;
    private String mobileNumber;
    private Boolean isPhoneOwner;
    private String phoneOwnerName;
    private String profile;
    private Integer numOfAdultsAtAddress;

    public FamilyDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
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
        if (name != null ? !name.equals(familyDto.name) : familyDto.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        return this.getName().compareTo( ((FamilyDto)o).getName() );
    }

    @Override
    public String toString() {
        return name;
    }
}
