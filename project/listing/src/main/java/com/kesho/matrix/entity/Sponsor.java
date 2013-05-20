package com.kesho.matrix.entity;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.List;
import java.util.Set;

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

//	public void addLog(StudentLog log) {
//		log.setStudent(this);
//		this.logs.add(log);
//	}
//
//	public List<StudentLog> getLogs() {
//		return logs;
//	}
}
