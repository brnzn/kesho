package com.kesho.datamart.ui.controller;

import calendar.FXCalendar;
import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.domain.LevelOfSupport;
import com.kesho.datamart.domain.SponsorshipStatus;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.domain.LeaverStatus;
import custom.NumericTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 10/19/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class NewStudentController {
    @FXML
    private TextField firstName;
    @FXML
    private TextField surname;
    @FXML
    private ComboBox<Gender> gender;
    @FXML
    private NumericTextField yearOfBirth;
    @FXML
    private TextField contactNumber;
    @FXML
    private TextField homeLocation;
    @FXML
    private ToggleGroup currentStudent;
    @FXML
    private ToggleGroup hasDisability;

    @FXML
    private ToggleGroup sponsored;
    @FXML
    private HBox dateControlBox;
    private final FXCalendar calendar = new FXCalendar();

    @FXML
    private ComboBox<SponsorshipStatus> sponsorshipStatus;
    @FXML
    private TextField email;
    @FXML
    private TextField facebook;
    @FXML
    private ComboBox<LevelOfSupport> levelOfSupport;
    @FXML
    private ToggleGroup topupNeeded;
    @FXML
    private NumericTextField shortfall;
    @FXML
    private NumericTextField alumniNumber;
    @FXML
    private ComboBox<LeaverStatus> leaverStatus;

    private Long currentId;

    @Inject
    private StudentsRepository studentsRepository;

    public NewStudentController() {
        calendar.setDateTextWidth(Double.valueOf(100));
    }

    public void setSelectedStudent(StudentDto student) {
        if(student == null) {
            return;
        }

        initializeForm(student);
    }

    @FXML
    private void initialize() {
        dateControlBox.getChildren().add(calendar);
        currentId = null;

        //TODO: numeric fields

        initializeComboBoxValues(gender, EnumSet.allOf(Gender.class));
        initializeComboBoxValues(leaverStatus, EnumSet.allOf(LeaverStatus.class));
        initializeComboBoxValues(sponsorshipStatus, EnumSet.allOf(SponsorshipStatus.class));
        initializeComboBoxValues(levelOfSupport, EnumSet.allOf(LevelOfSupport.class));

        initializeYesNoGroup(currentStudent, hasDisability, sponsored, topupNeeded);
    }

    @FXML
    private void save() {
        studentsRepository.save(buildDto());

        try {
            WindowsUtil.getInstance().showStudentsTable();
        } catch (IOException e) {
            //TODO: error handling
            e.printStackTrace();
        }
    }

    private StudentDto buildDto() {
        StudentDto student = new StudentDto();
        student.withId(currentId)
                .withName(firstName.getText())
                .withFamilyName(surname.getText())
                .withMobileNumber(contactNumber.getText())
                .withHomeLocation(homeLocation.getText())
                .activeStudent((Boolean) currentStudent.getSelectedToggle().getUserData())
                .withHasDisability((Boolean) hasDisability.getSelectedToggle().getUserData())
                .sponsored((Boolean)sponsored.getSelectedToggle().getUserData())
                .withEmail(email.getText())
                .withFacebookAddress(facebook.getText())
                .withTopupNeeded((Boolean)topupNeeded.getSelectedToggle().getUserData())
                .withLeaverStatus(leaverStatus.getSelectionModel().getSelectedItem())
                .withSponsorStatus(sponsorshipStatus.getSelectionModel().getSelectedItem())
                .withLevelOfSupport(levelOfSupport.getSelectionModel().getSelectedItem())
                .withGender(gender.getSelectionModel().getSelectedItem());

        if(StringUtils.isNotBlank(shortfall.getText())) {
            student.withShortfall(Integer.valueOf(shortfall.getText()));
        }

        if(StringUtils.isNotBlank(alumniNumber.getText())) {
            student.withAlumniNumber(Integer.valueOf(alumniNumber.getText()));
        }

        if(calendar.getValue() != null) {
            student.withStartDate(LocalDate.fromDateFields(calendar.getValue()));
        }

        if(StringUtils.isNotBlank(yearOfBirth.getText())) {
            student.withYearOfBirth(Integer.valueOf(yearOfBirth.getText()));
        }

        return student;
    }

    @FXML
    private void cancel() throws IOException {
        WindowsUtil.getInstance().showStudentsTable();
    }

    private void initializeForm(StudentDto student) {
        if(student.getStartDate() != null) {
            calendar.setValue(student.getStartDate().toDate());//new FXCalendarUtility().convertStringtoDate("02/02/2001");
        }

        currentId = student.getId();
        firstName.setText(student.getName());
        surname.setText(student.getSurname());
        gender.getSelectionModel().select(student.getGender());

        yearOfBirth.setText(safeToStringValue(student.getYearOfBirth(), null));

        contactNumber.setText(student.getMobileNumber());
        homeLocation.setText(student.getHomeLocation());

        setState(currentStudent, student.isActiveStudent());
        setState(hasDisability, student.hasDisability());
        setState(sponsored, student.isSponsored());
        setState(topupNeeded, student.isTopupNeeded());

        sponsorshipStatus.getSelectionModel().select(student.getSponsorshipStatus());
        email.setText(student.getEmail());
        facebook.setText(student.getFacebookAddress());

        levelOfSupport.getSelectionModel().select(student.getLevelOfSupport());

        shortfall.setText(safeToStringValue(student.getShortfall(), null));

        alumniNumber.setText(safeToStringValue(student.getAlumniNumber(), null));

        leaverStatus.getSelectionModel().select(student.getLeaverStatus());
    }

    public String safeToStringValue(Object value, String defaultValue) {
        return value != null ? value.toString() : (defaultValue != null ? defaultValue : "");
    }

    private <T extends Enum> void initializeComboBoxValues(ComboBox<T> target, EnumSet source) {
        target.getItems().clear();
        target.getItems().addAll(source);
    }

    private void initializeYesNoGroup(ToggleGroup ...groups) {
        for (ToggleGroup group:groups) {
            group.getToggles().get(0).setUserData(Boolean.TRUE);
            group.getToggles().get(1).setUserData(Boolean.FALSE);
            group.getToggles().get(0).setSelected(true);
        }
    }

    private void setState(ToggleGroup group, boolean value) {
        if(value) {
            group.getToggles().get(0).setSelected(true);
        } else {
            group.getToggles().get(1).setSelected(true);
        }
    }
}
