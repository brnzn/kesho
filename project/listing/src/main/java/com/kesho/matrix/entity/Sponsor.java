package com.kesho.matrix.entity;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//TODO: batch insert
@Entity
@Table(name = "SPONSORS")
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "SURNAME")
    private String surname;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="student")
    private List<SponsorLog> logs = newArrayList();

    @Column(name = "TITLE")
    private String title;
  
    //TODO: can we have multiple addresses??
    @Column(name="ADDRESS_LINE_1")
    private String addressLin1;

    @Column(name="ADDRESS_LINE_2")
    private String addressLin2;
    
    @Column(name="ADDRESS_LINE_3")
    private String addressLin3;
    
    @Column(name="COUNTY")
    private String county;
  
    @Column(name="POSTCODE")
    private String postcode;
    
    @Column(name="COUNTRY")
    private String country;

    @Column(name="PHONE")
    private String phone;

    @Column(name="EMAIL")
    private String email;
    
    @Column(name="GIFT_AID", columnDefinition = "BIT")
    private Boolean giftAid;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<SponsorLog> getLogs() {
		return logs;
	}

	public void setLogs(List<SponsorLog> logs) {
		this.logs = logs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddressLin1() {
		return addressLin1;
	}

	public void setAddressLin1(String addressLin1) {
		this.addressLin1 = addressLin1;
	}

	public String getAddressLin2() {
		return addressLin2;
	}

	public void setAddressLin2(String addressLin2) {
		this.addressLin2 = addressLin2;
	}

	public String getAddressLin3() {
		return addressLin3;
	}

	public void setAddressLin3(String addressLin3) {
		this.addressLin3 = addressLin3;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getGiftAid() {
		return giftAid;
	}

	public void setGiftAid(Boolean giftAid) {
		this.giftAid = giftAid;
	}

	
//	public void addLog(StudentLog log) {
//		log.setStudent(this);
//		this.logs.add(log);
//	}
//
//	public List<StudentLog> getLogs() {
//		return logs;
//	}
}
