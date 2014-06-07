package com.kesho.datamart.entity;

import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.domain.LeaverStatus;
import com.kesho.datamart.domain.LevelOfSupport;
import com.kesho.datamart.domain.SponsorshipStatus;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO: batch insert, validations
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

//    @Column(name = "CURRENT_STUDENT", columnDefinition = "BIT")
    @Transient
    private Boolean active;
    @Column(name = "GENDER", columnDefinition = "CHAR")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "DISABILITY", columnDefinition = "BIT")
    private Boolean hasDisability;
    @Column(name = "HOME_LOCATION")
    private String homeLocation;
    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;
    @Column(name = "START_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;
//    @Column(name = "SPONSORED", columnDefinition = "BIT")
    @Transient
    private Boolean sponsored;
    @Column(name = "YEAR_OF_BIRTH", columnDefinition = "SMALLINT")
    private Integer yearOfBirth;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="student")
//    private List<StudentLog> logs = newArrayList();
//
// unidirectional
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name="STUDENT_ID", referencedColumnName="ID", updatable = false, insertable = false)
    //// bidirectional
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="student")
    private List<EducationHistory> educationHistory = new ArrayList<>();  //TODO: need to do batch delete instead of one by one
    @Column(name= "EMAIL")
    private String email;
    @Column(name = "FACEBOOK")
    private String facebookAddress;
//    @Column(name = "STUDENT_STATUS")
//    @Enumerated(EnumType.STRING)
    @Transient
    private LeaverStatus leaverStatus;
 //   @Column(name = "SPONSORSHIP_STATUS")
 //   @Enumerated(EnumType.STRING)
    @Transient
    private SponsorshipStatus sponsorshipStatus;
    @Column(name = "LEVEL_OF_SUPPORT")
    @Enumerated(EnumType.STRING)
    private LevelOfSupport levelOfSupport;
    @Column(name = "TOPUP_NEEDED", columnDefinition = "BIT")
    private Boolean topupNeeded;
    @Column(name = "SHORTFALL")
    private Integer shortfall;
    @Column(name = "ALUMNI_MEMBER")
    private Integer alumniNumber;


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

	public void addToEducationHistory(EducationHistory educationHistory) {
        //bidirectional
//		educationHistory.setStudent(this);
		this.educationHistory.add(educationHistory);
	}
	
//	public List<StudentLog> getLogs() {
//		return logs;
//	}

	public List<EducationHistory> getEducationHistory() {
		return Collections.unmodifiableList(educationHistory);
	}

    public void setFamily(Family family) {
        this.family = family;
    }

    public Family getFamily() {
        return family;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isActive() {
        return active;
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

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getHomeLocation() {
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

    public void setSponsored(Boolean sponsored) {
        this.sponsored = sponsored;
    }

    public Boolean isSponsored() {
        return sponsored;
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

    public SponsorshipStatus getSponsorshipStatus() {
        return sponsorshipStatus;
    }

    public void setSponsorshipStatus(SponsorshipStatus sponsorshipStatus) {
        this.sponsorshipStatus = sponsorshipStatus;
    }


    public LevelOfSupport getLevelOfSupport() {
        return levelOfSupport;
    }

    public void setLevelOfSupport(LevelOfSupport levelOfSupport) {
        this.levelOfSupport = levelOfSupport;
    }

    public Boolean isTopupNeeded() {
        return topupNeeded;
    }

    public void setTopupNeeded(Boolean topupNeeded) {
        this.topupNeeded = topupNeeded;
    }

    public Integer getShortfall() {
        return shortfall;
    }

    public void setShortfall(Integer shortfall) {
        this.shortfall = shortfall;
    }

    public Integer getAlumniNumber() {
        return alumniNumber;
    }

    public void setAlumniNumber(Integer alumniNumber) {
        this.alumniNumber = alumniNumber;
    }
}
