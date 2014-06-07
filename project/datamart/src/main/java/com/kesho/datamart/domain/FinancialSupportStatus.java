package com.kesho.datamart.domain;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/19/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public enum FinancialSupportStatus {
    GAP_YEAR("Gap Year") {
        @Override
        public EnumSet<FinancialSupportStatusDetails> getChildren() {
            return EnumSet.of(FinancialSupportStatusDetails.POST_COLLEGE, FinancialSupportStatusDetails.POST_PRIMARY,
                    FinancialSupportStatusDetails.POST_SECONDARY, FinancialSupportStatusDetails.POST_UNI);
        }
    },
    GRADUATED("Graduated more than 2 years ago") {
        @Override
        public EnumSet<FinancialSupportStatusDetails> getChildren() {
            return EnumSet.of(FinancialSupportStatusDetails.COLLEGE, FinancialSupportStatusDetails.PRIMARY,
                    FinancialSupportStatusDetails.SECONDARY, FinancialSupportStatusDetails.UNI);
        }
    },
    WITHDRAWN("Sponsorship Withdrawn") {
        @Override
        public EnumSet<FinancialSupportStatusDetails> getChildren() {
            return EnumSet.of(FinancialSupportStatusDetails.ABLE_TO_PAY, FinancialSupportStatusDetails.DISHONESTY,
                    FinancialSupportStatusDetails.DROPPED_OUT, FinancialSupportStatusDetails.GOT_ANOTHER_SPONSOR,
                    FinancialSupportStatusDetails.PARENTS_DECISION, FinancialSupportStatusDetails.POOR_COOPERATION,
                    FinancialSupportStatusDetails.POOR_PERFORMANCE, FinancialSupportStatusDetails.PREGNANT, FinancialSupportStatusDetails.REFERRED_TO_ANOTHER_SPONSOR);
        }
    },
    DONATION_EXPIRY("Expiry of One Off Donation") {
        @Override
        public EnumSet<FinancialSupportStatusDetails> getChildren() {
            return EnumSet.of(FinancialSupportStatusDetails.COLLEGE, FinancialSupportStatusDetails.PRIMARY,
                    FinancialSupportStatusDetails.SECONDARY, FinancialSupportStatusDetails.UNI);
        }
    },
    OTHER("Other") {
        @Override
        public EnumSet<FinancialSupportStatusDetails> getChildren() {
            return EnumSet.noneOf(FinancialSupportStatusDetails.class);
        }
    };

    private final String display;

    private FinancialSupportStatus(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }

    public abstract EnumSet<FinancialSupportStatusDetails> getChildren();

}
