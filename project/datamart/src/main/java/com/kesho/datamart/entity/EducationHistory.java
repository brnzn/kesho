package com.kesho.datamart.entity;

import com.kesho.datamart.domain.EducationStatus;
import com.kesho.datamart.domain.EducationYear;
import com.kesho.datamart.domain.SubEducationStatus;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;


@Entity
@Table(name = "EDUCATION_HISTORY")
public class EducationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    //bidirectional
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "STUDENT_ID" , nullable = false)
//    private Student student;
//    unidirectional
    @Column(name = "STUDENT_ID", nullable=false)
    private Long studentId;
    
    @Column(name = "LEVEL")
    @Enumerated(EnumType.STRING)
    private EducationStatus educationStatus;

    @Column(name = "CLASS")
    @Enumerated(EnumType.STRING)
	private EducationYear year;

    @Column(name = "START_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startDate;

    @Column(name = "PREDICTED_END_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate predictedEndDate;

    @Column(name = "COURSE")
	private String course;

    @Column(name = "SECONDARY_LEVEL_1")
    @Enumerated(EnumType.STRING)
    private SubEducationStatus secondaryLevel1;

    @Column(name = "SECONDARY_LEVEL_2")
    @Enumerated(EnumType.STRING)
    private SubEducationStatus secondaryLevel2;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID")
    private School school;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name="VERSION")
    @Version
    private Integer version;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDate getPredictedEndDate() {
        return predictedEndDate;
    }

    public void setPredictedEndDate(LocalDate predictedEndDate) {
        this.predictedEndDate = predictedEndDate;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
    	return id;
    }
    
	public void setEducationStatus(EducationStatus status) {
		this.educationStatus = status;
	}

	public EducationStatus getEducationStatus() {
		return educationStatus;
	}

	public EducationYear getYear() {
		return year;
	}

	public void setYear(EducationYear year) {
		this.year = year;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

    public SubEducationStatus getSecondaryLevel1() {
        return secondaryLevel1;
    }

    public void setSecondaryLevel1(SubEducationStatus secondaryLevel1) {
        this.secondaryLevel1 = secondaryLevel1;
    }

    public SubEducationStatus getSecondaryLevel2() {
        return secondaryLevel2;
    }

    public void setSecondaryLevel2(SubEducationStatus secondaryLevel2) {
        this.secondaryLevel2 = secondaryLevel2;
    }

    //	public void setStudent(Student student) {
//		this.student = student;
//	}

	public void setSchool(School school) {
		this.school = school;
	}

	public School getSchool() {
		return school;
	}

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }
}
