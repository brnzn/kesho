package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/16/14
 * Time: 7:43 AM
 * To change this template use File | Settings | File Templates.
 */
public enum ContactType {
    P("Phone"),
    E("Email");

    private final String display;

    private ContactType(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
