package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/19/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Gender {
    M("Male"),
    F("Female");

    private final String disaply;

    private Gender(String display) {
        this.disaply = display;
    }

    public String getDisaply() {
        return disaply;
    }
}
