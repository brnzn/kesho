package com.kesho.datamart.dto;

import com.kesho.datamart.domain.FoundUs;
import com.kesho.datamart.domain.LevelOfParticipation;
import com.kesho.datamart.domain.PayeeType;
import com.kesho.datamart.entity.Sponsor;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDate;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/23/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SponsorDto implements Dto {
    private Long id;
    private Boolean active;
    private Boolean anonymous;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Family Name is mandatory")
    private String surname;
    private String addressLine1;
    private String addressLine2;
    private String county;
    private String postcode;
    private String country;
    private String email1;
    private String email2;
    private String phone;
    private PayeeType payeeType;
    private FoundUs howFoundUs;
    private LocalDate startDate;
    private LocalDate endDate;
    private LevelOfParticipation levelOfParticipation;

    public SponsorDto() {}
    
    public SponsorDto(Sponsor sponsor) {
        id = sponsor.getId();
        name = sponsor.getName();
        startDate = sponsor.getStartDate();       ;
        active = sponsor.getActive();
        addressLine1 = sponsor.getAddressLine1();
        addressLine2 = sponsor.getAddressLine2();
        anonymous = sponsor.getAnonymous();
        country = sponsor.getCountry();
        county = sponsor.getCounty();
        endDate = sponsor.getEndDate();
        postcode = sponsor.getPostcode();
        payeeType = sponsor.getPayeeType();
        phone = sponsor.getPhone();
        levelOfParticipation = sponsor.getLevelOfParticipation();
        howFoundUs = sponsor.getHowFoundUs();
        surname = sponsor.getSurname();
        email1 = sponsor.getEmail1();
        email2 = sponsor.getEmail2();
    }

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
}
