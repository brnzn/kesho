package com.kesho.datamart.entity;

import com.kesho.datamart.domain.Location;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 5:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "FAMILY")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name="FAMILY_NAME", nullable=false)
    private String name;

    @Column(name="HOME_LOCATION", nullable=true)
    @Enumerated(EnumType.STRING)
    private Location homeLocation;

    @Column(name="HOME_SUB_LOCATION", nullable=true)
    private String homeSubLocation;

    @Column(name="HOME_CLUSTER_ID", nullable=true)
    private String homeClusterId;

    @Column(name="ALIVE_PARENTS", columnDefinition = "BIT", nullable=true)
    private Integer aliveParents;

    @Column(name="MARTIAL_STATUS", columnDefinition = "BIT", nullable=true)
    private Boolean isMarried;

    @Column(name="NUM_NON_KESHO_STUDENTS_AT_ADDRS", columnDefinition = "BIT", nullable=true)
    private Integer numNonKeshoStudents;

    @Column(name="NUM_OF_WIVES", columnDefinition = "BIT", nullable=true)
    private Integer numOfWives;

    @Column(name="PRIMARY_CARETAKER", nullable=true)
    private String primaryCaretaker;

    @Column(name="MAIN_CONTACT_NAME", nullable=true)
    private String mainContactName;

    @Column(name="MOBLIE", nullable=true)
    private String mobileNumber;

    @Column(name="IS_PHONE_OWNER", columnDefinition = "BIT", nullable=true)
    private Boolean isPhoneOwner;

    @Column(name="PHONE_OWNER_NAME", nullable=true)
    private String phoneOwnerName;

    @Column(name="FAMILY_PROFILE", nullable=true, length = 65535,columnDefinition="Text")
    private String profile;

    @Column(name="NUM_ADULTS_AT_ADDRS", columnDefinition = "BIT", nullable=true)
    private Integer numOfAdultsAtAddress;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "family")
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean isMarried() {
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

    public Boolean isPhoneOwner() {
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
