package com.kesho.datamart.dto;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchoolDto implements Dto {
    private final Long id;
    private String name;
    private String county;
    private String country;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String postcode;

    public SchoolDto(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public SchoolDto withName(String name) {
        this.name = name;
        return this;
    }

    public SchoolDto withCounty(String county) {
        this.county = county;
        return this;
    }

    public SchoolDto withCountry(String country) {
        this.country = country;
        return this;
    }

    public SchoolDto withAddressLine1(String add1) {
        this.addressLine1 = add1;
        return this;
    }

    public SchoolDto withAddressLine2(String add2) {
        this.addressLine2 = add2;
        return this;
    }

    public SchoolDto withAddressLine3(String add3) {
        this.addressLine3 = add3;
        return this;
    }

    public SchoolDto withPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public String getCounty() {
        return county;
    }

    public String getCountry() {
        return country;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public String getPostcode() {
        return postcode;
    }

    @Override
    public String toString() {
        return name;
    }


}
