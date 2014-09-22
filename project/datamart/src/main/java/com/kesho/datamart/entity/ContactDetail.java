package com.kesho.datamart.entity;

import com.kesho.datamart.domain.ContactType;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/16/14
 * Time: 7:39 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "CONTACT_DETAIL")
public class ContactDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ContactType type;

    @Column(name = "OWNER_ID")
    private Long ownerId;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "VALUE")
    private String value;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public ContactType getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments() {
        return comments;
    }

    public void setOwner(Long owner) {
        this.ownerId = owner;
    }

    public Long getOwner() {
        return ownerId;
    }
}
