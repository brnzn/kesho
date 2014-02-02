package com.kesho.datamart.dto;

import com.kesho.datamart.domain.Location;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 2/1/14
 * Time: 9:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyDtoBuilder {
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
    private Integer numOfStudentsAtAddress;
    private String profile;
    private Integer numOfAdultsAtAddress;

    public FamilyDtoBuilder(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FamilyDto build() {
        FamilyDto dto = new FamilyDto(id, name);
        dto.setHomeLocation(homeLocation);
        dto.setHomeSubLocation(homeSubLocation);
        dto.setHomeClusterId(homeClusterId);
        dto.setAliveParents(aliveParents);
        dto.setMarried(isMarried);
        dto.setNumNonKeshoStudents(numNonKeshoStudents);
        dto.setNumOfWives(numOfWives);
        dto.setPrimaryCaretaker(primaryCaretaker);
        dto.setMainContactName(mainContactName);
        dto.setMobileNumber(mobileNumber);
        dto.setPhoneOwner(isPhoneOwner);
        dto.setPhoneOwnerName(phoneOwnerName);
        dto.setProfile(profile);
        dto.setNumOfAdultsAtAddress(numOfAdultsAtAddress);

        return dto;
    }

    public FamilyDtoBuilder withHomeLocation(Location location) {
        this.homeLocation = location;
        return this;
    }

    public FamilyDtoBuilder withHomeSubLocation(String homeSubLocation) {
        this.homeSubLocation = homeSubLocation;
        return this;
    }

    public FamilyDtoBuilder withHomeClusterId(String homeClusterId) {
        this.homeClusterId = homeClusterId;
        return this;
    }
    public FamilyDtoBuilder withAliveParents(Integer aliveParents) {
        this.aliveParents = aliveParents;
        return this;
    }
    public FamilyDtoBuilder isMarried(Boolean isMarried) {
        this.isMarried = isMarried;
        return this;
    }
    public FamilyDtoBuilder withNumNonKeshoStudents(Integer numNonKeshoStudents) {
        this.numNonKeshoStudents = numNonKeshoStudents;
        return this;
    }
    public FamilyDtoBuilder withNumOfWives(Integer numOfWives) {
        this.numOfWives = numOfWives;
        return this;
    }
    public FamilyDtoBuilder withPrimaryCaretaker(String primaryCaretaker) {
        this.primaryCaretaker = primaryCaretaker;
        return this;
    }
    public FamilyDtoBuilder withMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        return this;
    }
    public FamilyDtoBuilder isPhoneOwner(Boolean isPhoneOwner) {
        this.isPhoneOwner = isPhoneOwner;
        return this;
    }
    public FamilyDtoBuilder withMainContactName(String mainContactName) {
        this.mainContactName = mainContactName;
        return this;
    }

    public FamilyDtoBuilder withPhoneOwnerName(String phoneOwnerName) {
        this.phoneOwnerName = phoneOwnerName;
        return this;
    }

    public FamilyDtoBuilder withNumOfStudentsAtAddress(Integer numOfStudentsAtAddress) {
        this.numOfStudentsAtAddress = numOfStudentsAtAddress;
        return this;
    }
    public FamilyDtoBuilder withProfile(String profile) {
        this.profile = profile;
        return this;
    }
    public FamilyDtoBuilder withNumOfAdultsAtAddress(Integer numOfAdultsAtAddress) {
        this.numOfAdultsAtAddress = numOfAdultsAtAddress;
        return this;
    }
}
