package com.kesho.datamart.entity;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.Collections;
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
@Table(name = "STUDENTS")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    
    @Column(name = "NAME")
    private String firstName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="student")
    private List<StudentLog> logs = newArrayList();

// unidirectional   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(updatable = false, name="STUDENT_ID", referencedColumnName="ID")    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="student")
    private Set<EducationHistory> educationHistory = newHashSet();    
    
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

	public void addLog(StudentLog log) {
		log.setStudent(this);
		this.logs.add(log);
	}

	public void addToEducationHistory(EducationHistory educationHistory) {
		educationHistory.setStudent(this);
		this.educationHistory.add(educationHistory);
	}
	
	public List<StudentLog> getLogs() {
		return logs;
	}

	public Set<EducationHistory> getEducationHistory() {
		return Collections.unmodifiableSet(educationHistory);
	}
}
