package com.kesho.datamart.domain;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/24/13
 * Time: 8:47 PM
 * To change this template use File | Settings | File Templates.
 */
public enum FinancialArrangement {
    StandingOrder("Standing Order"),
    Annual("Annual"),
    OneOf("One off donation");

    private final String display;

    private FinancialArrangement(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
