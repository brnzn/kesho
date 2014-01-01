package com.kesho.datamart.dto;

import com.kesho.datamart.domain.FinancialArrangement;
import com.kesho.datamart.entity.PaymentArrangement;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/25/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class PaymentArrangementDto {
    private Long id;
    private Long studentId;
    private Long sponsorId;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate startDate;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate endDate;
    private FinancialArrangement type;
    private BigDecimal amount;
    private String studentName;

    public PaymentArrangementDto() {

    }

    public PaymentArrangementDto(Long id, Long studentId, Long sponsorId, LocalDate startDate, LocalDate endDate,
                                 FinancialArrangement financialArrangement, BigDecimal amount, String name, String surname) {
        this.studentName = name + surname;
        this.id = id;
        this.studentId = studentId;
        this.sponsorId = sponsorId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = financialArrangement;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public FinancialArrangement getFinancialArrangement() {
        return type;
    }

    public void setFinancialArrangement(FinancialArrangement type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
