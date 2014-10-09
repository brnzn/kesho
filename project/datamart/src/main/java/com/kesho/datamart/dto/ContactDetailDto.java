package com.kesho.datamart.dto;

import com.kesho.datamart.domain.ContactType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/16/14
 * Time: 7:59 AM
 * To change this template use File | Settings | File Templates.
 */
public class ContactDetailDto implements Dto {
    private Long id;
    @NotNull(message = "Owner Id is mandatory")
    private Long ownerId;
    @NotNull(message = "Contact type is mandatory")
    private ContactType type;
    @NotNull(message = "Value is mandatory")
    @Size(max=40, message = "Value cannot be more than 40 characters")
    private String value;
    @NotNull(message = "Comments is mandatory")
    @Size(max=45, message = "Comments cannot be more than 45 characters")
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
