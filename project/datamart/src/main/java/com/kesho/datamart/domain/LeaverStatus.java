package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/2/13
 * Time: 10:36 PM
 */
public enum LeaverStatus {
    DEPARTED_BEFORE_COMPLETE("Departed before completing"),
    SPONSORSHIP_GREATER_THAN_2_YEARS("Sponsorship ended more than 2 years ago");

    private final String display;

    private LeaverStatus(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
