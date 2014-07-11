package com.kesho.datamart.domain;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
//TODO: provide display text
public enum EducationStatus {
    Nursery ("Nursery") {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    Primary ("Primary"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EnumSet.of(SubEducationStatus.Boarding,
                    SubEducationStatus.Day);
        }
    },
    Secondary ("Secondary"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EnumSet.of(SubEducationStatus.District,
                    SubEducationStatus.Provincial,
                    SubEducationStatus.National);
        }
    },
    GapSchoolLeaver ("Gap School Leaver"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    College ("College"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EnumSet.of(SubEducationStatus.Diploma,
                    SubEducationStatus.Certifcate,
                    SubEducationStatus.Bridging,
                    SubEducationStatus.ShortTermCourse);
        }
    },
    University ("University"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    GapAfterTertiary ("GapAfterTertiary"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    };

    private final String display;

    private EducationStatus(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
    private static EnumSet<SubEducationStatus> EMPTY_SET = EnumSet.noneOf(SubEducationStatus.class);

    public abstract EnumSet<SubEducationStatus> getChildren();
}
