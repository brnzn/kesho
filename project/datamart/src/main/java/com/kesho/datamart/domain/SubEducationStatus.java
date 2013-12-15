package com.kesho.datamart.domain;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/11/13
 * Time: 8:18 AM
 * To change this template use File | Settings | File Templates.
 */
public enum SubEducationStatus {
    Day {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    Boarding {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    District {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EnumSet.of(Day, Boarding);
        }
    },
    Provincial {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    Bridging {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    ShortTermCourse {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    National {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    Diploma {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    Certifcate {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    };

    private static EnumSet<SubEducationStatus> EMPTY_SET = EnumSet.noneOf(SubEducationStatus.class);

    public abstract EnumSet<SubEducationStatus> getChildren();

}
