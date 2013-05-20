package com.kesho.matrix.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
//TODO: use embeded with student log
@Entity
@Table(name = "SPONSOR_LOG")
public class SponsorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "SPONSOR_ID" , insertable = true, updatable = false, nullable = false)
    private Student student;

    @Column(name="CREATED_DATE")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate createdDate = LocalDate.now();
    
    @Column(name = "LOG")
	private String comment;
    
	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getComment() {
		return comment;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}	


}
