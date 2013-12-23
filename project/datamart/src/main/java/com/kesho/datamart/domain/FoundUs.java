package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/23/13
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
public enum FoundUs {
    MemberOfStaff("Member of Staff / Committee"),
    KeshoMember("Kesho Member or Friend"),
    AnotherSponsor("Another Sponsor"),
    Visitor("Visitor to Kilifi"),
    KEMRI("KEMRI"),
    Website("Website"),
    PublicityEvent("Publicity Event"),
    KeshoAlumni("Kesho Alumni"),
    LocalContacts("Local contacts e.g. rotary club/government");

    private final String display;

    private FoundUs(String display) {
        this.display = display;
    }
}
