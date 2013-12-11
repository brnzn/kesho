package com.kesho.datamart.domain;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/9/13
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
public enum EducationStatus {
    Nursery {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    Primary {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EnumSet.of(SubEducationStatus.Boarding,
                    SubEducationStatus.Day);
        }
    },
    Secondary {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EnumSet.of(SubEducationStatus.District,
                    SubEducationStatus.Provincial,
                    SubEducationStatus.National);
        }
    },
    GapSchoolLeaver {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    College {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EnumSet.of(SubEducationStatus.Diploma,
                    SubEducationStatus.Certifcate,
                    SubEducationStatus.Bridging,
                    SubEducationStatus.ShortTermCourse);
        }
    },
    University {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    },
    GapAfterTertiary {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }
    };

    private static EnumSet<SubEducationStatus> EMPTY_SET = EnumSet.noneOf(SubEducationStatus.class);

    public abstract EnumSet<SubEducationStatus> getChildren();
}
