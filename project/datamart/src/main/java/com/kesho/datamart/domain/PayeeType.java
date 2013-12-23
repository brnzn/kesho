package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/23/13
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PayeeType {
    Individual("Individual"),
    Club("Club"),
    Corporate("Corporate"),
    FoundationTrust("Foundation / Trust"),
    GiftAid("Gift Aid"),
    Fundraising("Fundraising Event"),
    KeshoAlumni("Kesho Alumni"),
    Other("Other");

    private final String display;

    private PayeeType(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
