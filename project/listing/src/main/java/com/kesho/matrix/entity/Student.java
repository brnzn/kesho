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
import javax.persistence.JoinColumn;
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
    
    @Column(name = "FIRST_NAME")
    private String firstName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="student")
    private List<StudentLog> logs = newArrayList();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(updatable = false, name="STUDENT_ID", referencedColumnName="ID")    
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
		logs.add(log);
	}

	public List<StudentLog> getLogs() {
		return logs;
	}

	public Set<EducationHistory> getEducationHistory() {
		return educationHistory;
	}

	public void setEducationHistory(Set<EducationHistory> educationHistory) {
		this.educationHistory = educationHistory;
	}

	public void addEducationHistory(EducationHistory eh) {
		this.educationHistory.add(eh);
	}
}
