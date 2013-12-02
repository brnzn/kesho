package com.kesho.datamart.ui.controller;

import calendar.FXCalendar;
import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.StudentsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

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
    private TextField yearOfBirth;
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
    private ComboBox<String> sponsorshipStatus;
    @FXML
    private TextField email;
    @FXML
    private TextField facebook;
    @FXML
    private ComboBox<String> levelOfSupport;
    @FXML
    private ToggleGroup topupNeeded;
    @FXML
    private TextField shortfall;
    @FXML
    private TextField alumniNumber;
    @FXML
    private ComboBox<String> leaverStatus;

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

        if(student.getStartDate() != null) {
            calendar.setValue(student.getStartDate().toDate());//new FXCalendarUtility().convertStringtoDate("02/02/2001");
        }

        currentId = student.getId();
        firstName.setText(student.getName());
        surname.setText(student.getSurname());

        if(student.getGender() != null) {
            gender.getSelectionModel().select(student.getGender());
        }

        if(student.getYearOfBirth() != null) {
            yearOfBirth.setText(student.getYearOfBirth().toString());
        }

        contactNumber.setText(student.getMobileNumber());

        homeLocation.setText(student.getHomeLocation());

        if(student.isActiveStudent() != null) {
            if (student.isActiveStudent()) {
                currentStudent.getToggles().get(0).setSelected(true);
            } else {
                currentStudent.getToggles().get(1).setSelected(true);
            }
        }

        if(student.hasDisability() != null) {
            if (student.hasDisability()) {
                hasDisability.getToggles().get(0).setSelected(true);
            } else {
                hasDisability.getToggles().get(1).setSelected(true);
            }
        }

        if(student.isSponsored() != null) {
            if (student.isSponsored()) {
                sponsored.getToggles().get(0).setSelected(true);
            } else {
                sponsored.getToggles().get(1).setSelected(true);
            }
        }


        sponsorshipStatus.getSelectionModel().select(student.getSponsorshipStatus());
        email.setText(student.getEmail());
        facebook.setText(student.getFacebookAddress());
        if(student.isTopupNeeded() != null && student.isTopupNeeded()) {
            topupNeeded.getToggles().get(0).setSelected(true);
        } else {
            topupNeeded.getToggles().get(1).setSelected(true);
        }

        levelOfSupport.getSelectionModel().select(student.getLevelOfSupport());
        if(student.getShortfall() != null) {
            shortfall.setText(student.getShortfall().toString());
        } else {
            shortfall.setText("");
        }

        if(student.getAlumniNumber() != null) {
           alumniNumber.setText(student.getAlumniNumber().toString());
        } else {
            alumniNumber.setText("");
        }

        leaverStatus.getSelectionModel().select(student.getLeaverStatus());
    }

    @FXML
    private void initialize() {
        dateControlBox.getChildren().add(calendar);
        currentId = null;
        gender.getItems().clear();
        gender.getItems().addAll(Gender.values());

        currentStudent.getToggles().get(0).setUserData(Boolean.TRUE);
        currentStudent.getToggles().get(1).setUserData(Boolean.FALSE);
        currentStudent.getToggles().get(0).setSelected(true);

        hasDisability.getToggles().get(0).setUserData(Boolean.TRUE);
        hasDisability.getToggles().get(1).setUserData(Boolean.FALSE);
        hasDisability.getToggles().get(0).setSelected(true);

        sponsored.getToggles().get(0).setUserData(Boolean.TRUE);
        sponsored.getToggles().get(1).setUserData(Boolean.FALSE);
        sponsored.getToggles().get(0).setSelected(true);

        topupNeeded.getToggles().get(0).setUserData(Boolean.TRUE);
        topupNeeded.getToggles().get(1).setUserData(Boolean.FALSE);
        topupNeeded.getToggles().get(0).setSelected(true);
    }

    @FXML
    private void save() {
        studentsRepository.save(buildDto());

        try {
            WindowsUtil.getInstance().showStudentsTable();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
                .withLeaverStatus(leaverStatus.getSelectionModel().getSelectedItem());

        if(shortfall.getText() != null) {
            student.withShortfall(Integer.valueOf(shortfall.getText()));
        }

        student.withSponsorStatus(sponsorshipStatus.getSelectionModel().getSelectedItem());
        student.withLevelOfSupport(levelOfSupport.getSelectionModel().getSelectedItem());

        if(shortfall.getText() != null) {
            student.withShortfall(Integer.valueOf(shortfall.getText()));
        }

        if(alumniNumber.getText() != null) {
            student.withAlumniNumber(Integer.valueOf(alumniNumber.getText()));
        }

        if(calendar.getValue() != null) {
            student.withStartDate(LocalDate.fromDateFields(calendar.getValue()));
        }

        student.withGender(gender.getSelectionModel().getSelectedItem() );

        if(StringUtils.isNotBlank(yearOfBirth.getText()) && StringUtils.isNumeric(yearOfBirth.getText())) {
            student.withYearOfBirth(Integer.valueOf(yearOfBirth.getText()));
        }


        return student;
    }

    @FXML
    private void cancel() throws IOException {
        WindowsUtil.getInstance().showStudentsTable();
    }

}
