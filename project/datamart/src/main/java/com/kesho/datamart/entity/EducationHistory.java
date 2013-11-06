package com.kesho.datamart.entity;

import com.kesho.datamart.domain.CLASS;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;


@Entity
@Table(name = "EDUCATION_HISTORY")
public class EducationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID" , insertable = true, updatable = false, nullable = false)
    private Student student;
//    unidirectional @Column(name = "STUDENT_ID", nullable=false) 
//    private Long studentId;
    
    //TODO:enum
    @Column(name = "LEVEL")
	private String level;

    @Column(name = "CLASS")
    @Enumerated(STRING)    
	private CLASS currentClass;

    @Column(name = "START_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate startDate;
    
    @Column(name = "PREDICTED_END_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate predictedEndDate;
    
    @ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "SCHOOL_ID" , insertable = false, updatable = false, nullable = false)
    private School school;
   
    @Column(name = "TERM", columnDefinition = "TINYINT")
    private Integer term;

    public Long getId() {
    	return id;
    }
    
	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return level;
	}

	public CLASS getCurrentClass() {
		return currentClass;
	}

	public void setCurrentClass(CLASS currentClass) {
		this.currentClass = currentClass;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getPredictedEndDate() {
		return predictedEndDate;
	}

	public void setPredictedEndDate(LocalDate predictedEndDate) {
		this.predictedEndDate = predictedEndDate;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public School getSchool() {
		return school;
	}	

}
