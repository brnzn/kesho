package com.kesho.datamart.dto;

import com.kesho.datamart.domain.ContactType;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/16/14
 * Time: 7:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactDetailDto implements Dto {
    private Long id;
    private Long ownerId;
    private ContactType type;
    private String value;
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public ContactType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getComments() {
        return comments;
    }

    public ContactDetailDto withId(Long id) {
        this.id = id;
        return this;
    }

    public ContactDetailDto withType(ContactType type) {
        this.type = type;
        return this;
    }

    public ContactDetailDto withOwnerId(Long owner) {
        this.ownerId = owner;
        return this;
    }

    public ContactDetailDto withValue(String value) {
        this.value = value;
        return this;
    }

    public ContactDetailDto withComments(String comments) {
        this.comments = comments;
        return this;
    }
}
