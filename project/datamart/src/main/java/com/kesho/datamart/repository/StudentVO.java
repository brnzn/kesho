package com.kesho.datamart.repository;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 10/7/14
 * Time: 5:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentVO {
    private final Long id;
    private final String firstName;
    private final String surname;

    public StudentVO(Long id, String firstName, String surname) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }
}
