package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/19/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public enum LevelOfSupport {
    FULL("Full"),
    PART("Part"),
    ONE_OF("One of donation"),
    ON_HOLD("On hold"),
    NONE("None");

    private final String display;

    private LevelOfSupport(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
