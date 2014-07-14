package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 6/7/14
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
public enum FinancialSupportStatusDetails {
    EMPTY(""),
    POST_PRIMARY("Post Primary"),
    POST_SECONDARY("Post Secondary"),
    POST_COLLEGE("Post College"),
    POST_UNI("Post University"),
    NURSERY("Nursery"),
    PRIMARY("Primary"),
    SECONDARY("Secondary"),
    COLLEGE("College"),
    UNI("University"),
    POOR_COOPERATION("Poor Cooperation"),
    POOR_PERFORMANCE("Poor Performance"),
    DROPPED_OUT("Dropped Out"),
    GOT_ANOTHER_SPONSOR("Family found another sponsor"),
    REFERRED_TO_ANOTHER_SPONSOR("Referred by Kesho to new sponsor"),
    ABLE_TO_PAY("Family able to pay"),
    PARENTS_DECISION("Parents Decision"),
    DISHONESTY("Dishonesty"),
    PREGNANT("Pregnant / Married"),
    OTHER("Other")
    ;

    private final String display;

    private FinancialSupportStatusDetails(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
