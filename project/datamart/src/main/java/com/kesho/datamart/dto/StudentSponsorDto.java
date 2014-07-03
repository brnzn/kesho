package com.kesho.datamart.dto;

import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 7/2/14
 * Time: 8:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class StudentSponsorDto {
    private Long sponsorId;
    private Boolean anonymity;
    private LocalDate endOfCommitment;
    private String surname;
    private String name;
    private BigDecimal amount;
    private Boolean active;

    public StudentSponsorDto(Long sponsorId, BigDecimal amount, Boolean anonymity, LocalDate endOfCommitment, String surname, String name, Boolean active) {
        this.anonymity = anonymity;
        this.endOfCommitment = endOfCommitment;
        this.surname = surname;
        this.name = name;
        this.amount = amount;
        this.sponsorId = sponsorId;
        this.active = active;
    }

    public Boolean isActive() {
        return active;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public Boolean getAnonymity() {
        return anonymity;
    }

    public LocalDate getEndOfCommitment() {
        return endOfCommitment;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
