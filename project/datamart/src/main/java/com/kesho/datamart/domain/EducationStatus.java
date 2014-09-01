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
    Nursery ("Nursery") {
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }

        @Override
        public EnumSet<EducationYear> getClasses() {
            return EnumSet.of(EducationYear.KG1, EducationYear.KG2, EducationYear.KG3);
        }
    },
    Primary ("Primary"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EnumSet.of(SubEducationStatus.Boarding,
                    SubEducationStatus.Day);
        }

        @Override
        public EnumSet<EducationYear> getClasses() {
            return EnumSet.of(EducationYear.Class1, EducationYear.Class2, EducationYear.Class3, EducationYear.Class4, EducationYear.Class5, EducationYear.Class6, EducationYear.Class7, EducationYear.Class8);
        }
    },
    Secondary ("Secondary"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EnumSet.of(SubEducationStatus.District,
                    SubEducationStatus.Provincial,
                    SubEducationStatus.National);
        }

        @Override
        public EnumSet<EducationYear> getClasses() {
            return EnumSet.of(EducationYear.Form1, EducationYear.Form2, EducationYear.Form3, EducationYear.Form4);
        }
    },
    GapSchoolLeaver ("Gap School Leaver"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }

        @Override
        public EnumSet<EducationYear> getClasses() {
            return EnumSet.noneOf(EducationYear.class);
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

        @Override
        public EnumSet<EducationYear> getClasses() {
            return EnumSet.of(EducationYear.Year1, EducationYear.Year2, EducationYear.Year3, EducationYear.Year4);
        }
    },
    University ("University"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }

        @Override
        public EnumSet<EducationYear> getClasses() {
            return EnumSet.of(EducationYear.Year1, EducationYear.Year2, EducationYear.Year3, EducationYear.Year4, EducationYear.Year5, EducationYear.Year6);
        }
    },
    GapAfterTertiary ("Gap After Tertiary"){
        @Override
        public EnumSet<SubEducationStatus> getChildren() {
            return EMPTY_SET;
        }

        @Override
        public EnumSet<EducationYear> getClasses() {
            return EnumSet.of(EducationYear.Class1);
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
    public abstract EnumSet<EducationYear> getClasses();
}
