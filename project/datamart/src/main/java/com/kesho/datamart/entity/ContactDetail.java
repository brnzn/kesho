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

    //@Column(name = "OWNER_ID", insertable = false, updatable = false) if we have unidirectional relationship
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactDetail that = (ContactDetail) o;

        if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
        if (!ownerId.equals(that.ownerId)) return false;
        if (type != that.type) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + ownerId.hashCode();
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
