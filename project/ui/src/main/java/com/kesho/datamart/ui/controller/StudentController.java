package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.domain.LeaverStatus;
import com.kesho.datamart.domain.LevelOfSupport;
import com.kesho.datamart.domain.SponsorshipStatus;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.SystemEventListener;
import com.kesho.datamart.ui.util.Util;
import com.kesho.ui.control.NumericTextField;
import com.kesho.ui.control.calendar.FXCalendar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Inject;
import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentController {
    @Autowired
    private StudentsRepository studentsRepository;

    @FXML
    private TextField firstName;
    @FXML
    private TextField family;

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
    private TabPane studentTab;

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

    private StudentDto selected;
    @Inject
    @Qualifier("StudentsController")
    private Selectable<StudentDto> selectedStudent;
    @Inject
    private FamilyRepository familyRepository;

    public StudentController() {
        calendar.setDateTextWidth(Double.valueOf(200));
        WindowsUtil.getInstance().autowire(this);
    }

    @FXML
    private void initialize() {

        selected = null;
        family.setUserData(null);

        dateControlBox.getChildren().add(calendar);
        Util.initializeYesNoGroup(hasDisability, sponsored, currentStudent);

        Util.initializeComboBoxValues(gender, EnumSet.allOf(Gender.class));
        Util.initializeComboBoxValues(leaverStatus, EnumSet.allOf(LeaverStatus.class));
        Util.initializeComboBoxValues(sponsorshipStatus, EnumSet.allOf(SponsorshipStatus.class));
        Util.initializeComboBoxValues(levelOfSupport, EnumSet.allOf(LevelOfSupport.class));

        WindowsUtil.getInstance().getEventBus().registerListener(Event.STUDENT_SELECTED, new SystemEventListener() {
            @Override
            public void handle() {
                //      System.out.println("777777 " +selectedStudent.getSelectedItem().getId());
                if (selectedStudent.getSelectedItem() != null) {
                    initializeForm(selectedStudent.getSelectedItem());
                }
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WindowsUtil.getInstance().getControllers().studentsController().registerNewChangeListener("studentDetailsTab", new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        add();
                    }
                });
            }
        });

    }

    private void add() {
        StudentDto dto = new StudentDto();
        boolean isOK = WindowsUtil.getInstance().studentForm(dto);

        if (isOK) {
            studentsRepository.save(dto);
            WindowsUtil.getInstance().getEventBus().fireEvent(Event.STUDENT_ADDED);
        }
    }

    @FXML
    private void selectFamily() {
        FamilyDto dto = WindowsUtil.getInstance().familySelector();
        if(dto != null) {
            family.setUserData(dto);
            family.setText(dto.getName());
            selectedStudent.getSelectedItem().setFamily(dto);
        }
    }

    @FXML
    private void save() {
        StudentDto dto = buildDto();
        dto = studentsRepository.save(dto);
        selected.withName(firstName.getText());
        selectedStudent.refresh();
    }

    private StudentDto buildDto() {
        selected.withName(firstName.getText())
                .withFamily((FamilyDto)family.getUserData())
                .withMobileNumber(contactNumber.getText())
                .withHomeLocation(homeLocation.getText())
                .activeStudent((Boolean) currentStudent.getSelectedToggle().getUserData())
                .withHasDisability((Boolean) hasDisability.getSelectedToggle().getUserData())
                .sponsored((Boolean) sponsored.getSelectedToggle().getUserData())
                .withEmail(email.getText())
                .withFacebookAddress(facebook.getText())
                .withTopupNeeded((Boolean) topupNeeded.getSelectedToggle().getUserData())
                .withLeaverStatus(leaverStatus.getSelectionModel().getSelectedItem())
                .withSponsorStatus(sponsorshipStatus.getSelectionModel().getSelectedItem())
                .withLevelOfSupport(levelOfSupport.getSelectionModel().getSelectedItem())
                .withGender(gender.getSelectionModel().getSelectedItem());

        if (StringUtils.isNotBlank(shortfall.getText())) {
            selected.withShortfall(Integer.valueOf(shortfall.getText()));
        }

        if (StringUtils.isNotBlank(alumniNumber.getText())) {
            selected.withAlumniNumber(Integer.valueOf(alumniNumber.getText()));
        }

        if (calendar.getValue() != null) {
            selected.withStartDate(LocalDate.fromDateFields(calendar.getValue()));
        }

        if (StringUtils.isNotBlank(yearOfBirth.getText())) {
            selected.withYearOfBirth(Integer.valueOf(yearOfBirth.getText()));
        }

        return selected;
    }

    private void initializeForm(StudentDto student) {
        selected = student;

        if (student.getStartDate() != null) {
            calendar.setValue(student.getStartDate().toDate());
        }

        firstName.setText(student.getName());

        family.setText(student.getFamily().getName());
        family.setUserData(student.getFamily());

        gender.getSelectionModel().select(student.getGender());

        yearOfBirth.setText(Util.safeToStringValue(student.getYearOfBirth(), null));

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

        shortfall.setText(Util.safeToStringValue(student.getShortfall(), null));

        alumniNumber.setText(Util.safeToStringValue(student.getAlumniNumber(), null));

        leaverStatus.getSelectionModel().select(student.getLeaverStatus());
    }

    private void setState(ToggleGroup group, Boolean value) {
        if (value == null || value) {
            group.getToggles().get(0).setSelected(true);
        } else {
            group.getToggles().get(1).setSelected(true);
        }
    }


}
