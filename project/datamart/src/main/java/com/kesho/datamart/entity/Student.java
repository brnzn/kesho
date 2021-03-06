package com.kesho.datamart.entity;

import com.kesho.datamart.domain.*;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "STUDENTS")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @Column(name = "NAME")
    private String firstName;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "FAMILY_ID" , nullable = false, referencedColumnName = "ID")
    private Family family;

    @Column(name = "GENDER", columnDefinition = "CHAR")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "DISABILITY", columnDefinition = "BIT")
    private Boolean hasDisability;
    @Column(name = "HOME_LOCATION", nullable=true)
    @Enumerated(EnumType.STRING)
    private Location homeLocation;
    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;
    @Column(name = "START_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;

    @Column(name = "YEAR_OF_BIRTH", columnDefinition = "SMALLINT")
    private Integer yearOfBirth;
//
// unidirectional
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @JoinColumn(name="STUDENT_ID", referencedColumnName="ID", updatable = false, insertable = false)
    //// bidirectional
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="student")
//    private List<EducationHistory> educationHistory = new ArrayList<>();  //TODO: need to do batch delete instead of one by one

    @Column(name= "EMAIL")
    private String email;

    @Column(name = "FACEBOOK")
    private String facebookAddress;

    @Column(name = "LEAVER_STATUS")
    @Enumerated(EnumType.STRING)
    private LeaverStatus leaverStatus;

    @Column(name = "FINANCIAL_SUPPORT", columnDefinition = "BIT")
    private Boolean financialSupport;

    @Column(name = "ENRICHMENT_SUPPORT", columnDefinition = "BIT")
    private Boolean enrichmentSupport;

    @Column(name = "FINANCIAL_SUPPORT_STATUS")
    @Enumerated(EnumType.STRING)
    private FinancialSupportStatus financialSupportStatus;

    @Column(name = "FINANCIAL_SUPPORT_STATUS_DETAILS")
    private String financialSupportStatusDetails;

    @Column(name = "FINANCIAL_SUPPORT_STATUS_SUB_DETAILS")
    private String financialSupportStatusSubDetails;

    @Column(name = "LEVEL_OF_SUPPORT")
    @Enumerated(EnumType.STRING)
    private LevelOfSupport levelOfSupport;

//    @Column(name = "ALUMNI_MEMBER")
//    private Integer alumniNumber;

    @Column(name = "TOTAL_SPONSORSHIP_REQUIRED")
    private Integer totalSRequired;

    @Column(name = "END_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate endDate;

    @Column(name="VERSION")
    @Version
    private Integer version;


    public Boolean getEnrichmentSupport() {
        return enrichmentSupport;
    }

    public void setEnrichmentSupport(Boolean enrichmentSupport) {
        this.enrichmentSupport = enrichmentSupport;
    }

    public String getFinancialSupportStatusDetails() {
        return financialSupportStatusDetails;
    }

    public void setFinancialSupportStatusDetails(String financialSupportStatusDetails) {
        this.financialSupportStatusDetails = financialSupportStatusDetails;
    }


    public String getFinancialSupportStatusSubDetails() {
        return financialSupportStatusSubDetails;
    }

    public void setFinancialSupportStatusSubDetails(String financialSupportStatusSubDetails) {
        this.financialSupportStatusSubDetails = financialSupportStatusSubDetails;
    }

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

    public Integer getTotalSponsorshipRequired() {
        return totalSRequired;
    }

    public void setTotalSponsorshipRequired(Integer totalSRequired) {
        this.totalSRequired = totalSRequired;
    }

    //	public void addToEducationHistory(EducationHistory educationHistory) {
        //bidirectional
//		educationHistory.setStudent(this);
//		this.educationHistory.add(educationHistory);
//	}
	
//	public List<EducationHistory> getEducationHistory() {
//		return Collections.unmodifiableList(educationHistory);
//	}

    public void setFamily(Family family) {
        this.family = family;
    }

    public Family getFamily() {
        return family;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setHasDisability(Boolean hasDisability) {
        this.hasDisability = hasDisability;
    }

    public Boolean hasDisability() {
        return hasDisability;
    }

    public void setHomeLocation(Location homeLocation) {
        this.homeLocation = homeLocation;
    }

    public Location getHomeLocation() {
        return homeLocation;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setFinancialSupport(Boolean financialSupport) {
        this.financialSupport = financialSupport;
    }

    public Boolean hasFinancialSupport() {
        return financialSupport;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookAddress() {
        return facebookAddress;
    }

    public void setFacebookAddress(String facebookAddress) {
        this.facebookAddress = facebookAddress;
    }

    public LeaverStatus getLeaverStatus() {
        return leaverStatus;
    }

    public void setLeaverStatus(LeaverStatus leaverStatus) {
        this.leaverStatus = leaverStatus;
    }

    public FinancialSupportStatus getFinancialSupportStatus() {
        return financialSupportStatus;
    }

    public void setFinancialSupportStatus(FinancialSupportStatus financialSupportStatus) {
        this.financialSupportStatus = financialSupportStatus;
    }


    public LevelOfSupport getLevelOfSupport() {
        return levelOfSupport;
    }

    public void setLevelOfSupport(LevelOfSupport levelOfSupport) {
        this.levelOfSupport = levelOfSupport;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
//    public Integer getAlumniNumber() {
//        return alumniNumber;
//    }
//
//    public void setAlumniNumber(Integer alumniNumber) {
//        this.alumniNumber = alumniNumber;
//    }
}
