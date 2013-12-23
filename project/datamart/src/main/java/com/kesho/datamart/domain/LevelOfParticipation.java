package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/23/13
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
public enum LevelOfParticipation {
    Usual("Usual"),
    Anonymous("Anonymous"),
    Regular("Regular Correspondence");

    private final String display;

    private LevelOfParticipation(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
