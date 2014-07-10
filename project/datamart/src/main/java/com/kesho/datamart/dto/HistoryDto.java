package com.kesho.datamart.dto;

import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 7/8/14
 * Time: 5:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class HistoryDto {
    private Long id;
    private Long ownerId;
    @NotNull(message = "Date is mandatory")
    private LocalDate date;
    @NotNull(message = "Comments is mandatory")
    private String comments;

    public HistoryDto(Long ownerId, String comments, LocalDate date) {
        this.comments = comments;
        //TODO: throw exception if owner is null - should never happen, but error message will not help
        this.ownerId = ownerId;
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
