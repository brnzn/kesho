package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/19/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public enum SponsorshipStatus {
    REVIEW("On review - fees suspended"),
    RECENT_SECONDARY_LEAVER("Recent Secondary School Leaver (Gap Year)"),
    RECENT_PRIMARY_LEAVER("Recent Primary school leaver");

    private final String display;

    private SponsorshipStatus(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
