package com.kesho.datamart.entity;

import com.kesho.datamart.domain.FoundUs;
import com.kesho.datamart.domain.LevelOfParticipation;
import com.kesho.datamart.domain.PayeeType;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/23/13
 * Time: 7:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SPONSORS")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ACTIVE", columnDefinition = "BIT")
    private Boolean active;

    @Column(name = "ANONYMOUS", columnDefinition = "BIT")
    private Boolean anonymous;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;

    @Column(name = "COUNTY")
    private String county;

    @Column(name = "POSTCODE")
    private String postcode;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "EMAIL1")
    private String email1;

    @Column(name = "EMAIL2")
    private String email2;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PAYEE_TYPE")
    @Enumerated(EnumType.STRING)
    private PayeeType payeeType;

    @Column(name = "HOW_SPONSOR_FOUND_US")
    @Enumerated(EnumType.STRING)
    private FoundUs howFoundUs;

    @Column(name = "START_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate endDate;

    @Column(name = "LEVEL_OF_PARTICIPATION")
    @Enumerated(EnumType.STRING)
    private LevelOfParticipation levelOfParticipation;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PayeeType getPayeeType() {
        return payeeType;
    }

    public void setPayeeType(PayeeType payeeType) {
        this.payeeType = payeeType;
    }

    public FoundUs getHowFoundUs() {
        return howFoundUs;
    }

    public void setHowFoundUs(FoundUs howFoundUs) {
        this.howFoundUs = howFoundUs;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LevelOfParticipation getLevelOfParticipation() {
        return levelOfParticipation;
    }

    public void setLevelOfParticipation(LevelOfParticipation levelOfParticipation) {
        this.levelOfParticipation = levelOfParticipation;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
