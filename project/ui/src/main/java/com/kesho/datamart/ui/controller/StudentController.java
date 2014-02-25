package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.domain.LeaverStatus;
import com.kesho.datamart.domain.LevelOfSupport;
import com.kesho.datamart.domain.SponsorshipStatus;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.FormActionListener;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.SystemEventListener;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import com.kesho.ui.control.NumericTextField;
import com.kesho.ui.control.calendar.FXCalendar;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentController implements FormActionListener {
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
    private Tab studentDetailsTab;

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
    @FXML
    private Button saveButton;

    private SimpleObjectProperty<StudentDto> selected = new SimpleObjectProperty<>();

    @Inject
    private StudentsController parentController;
    @Inject
    private FamilyRepository familyRepository;

    private Map<String, Node> validationFields = new HashMap<>();


    public StudentController() {
        calendar.setDateTextWidth(Double.valueOf(200));
        WindowsUtil.getInstance().autowire(this);
    }

    @FXML
    private void initialize() {
        selected.addListener(new ChangeListener<StudentDto>() {
            @Override
            public void changed(ObservableValue<? extends StudentDto> observableValue, StudentDto studentDto, StudentDto studentDto2) {
                saveButton.setDisable(studentDto2 == null);
            }
        });

        selected.setValue(null);
        family.setUserData(null);

        dateControlBox.getChildren().add(calendar);
        Util.initializeYesNoGroup(hasDisability, sponsored, currentStudent, topupNeeded);

        Util.initializeComboBoxValues(gender, EnumSet.allOf(Gender.class));
        Util.initializeComboBoxValues(leaverStatus, EnumSet.allOf(LeaverStatus.class));
        Util.initializeComboBoxValues(sponsorshipStatus, EnumSet.allOf(SponsorshipStatus.class));
        Util.initializeComboBoxValues(levelOfSupport, EnumSet.allOf(LevelOfSupport.class));

        WindowsUtil.getInstance().getEventBus().registerListener(Event.STUDENT_SELECTED, new SystemEventListener() {
            @Override
            public void handle() {
                if(parentController.getSelectedItem() != null) {
                    itemSelected(parentController.getSelectedItem());
                }
            }
        });

//        studentDetailsTab.setOnSelectionChanged(new EventHandler<javafx.event.Event>() {
//            @Override
//            public void handle(javafx.event.Event event) {
//                if (studentDetailsTab.isSelected() && parentController.getSelectedItem() != null) {
//                    parentController.disableButton(false, TabButton.DELETE);
//                }
//            }
//        });
    }

    @Override
    public void newFired() {
        resetForm();
        selected.set(new StudentDto());
    }

    private void itemSelected(StudentDto student) {
//        if(student != null) {
//            parentController.disableButton(false, TabButton.NEW, TabButton.DELETE);
//        } else {
//            parentController.disableButton(true, TabButton.DELETE);
//        }

        if(student == null) {
            resetForm();
            return;
        }

        selected.setValue(student);

        if (student.getStartDate() != null) {
            calendar.setValue(student.getStartDate().toDate());
        }

        firstName.setText(student.getFirstName());

        family.setText(student.getFamily().getFamilyName());
        family.setUserData(student.getFamily());

        gender.getSelectionModel().select(student.getGender());

        yearOfBirth.setText(Util.safeToStringValue(student.getYearOfBirth(), null));

        contactNumber.setText(student.getMobileNumber());
        homeLocation.setText(student.getHomeLocation());

        Util.setYesNoToggleState(currentStudent, student.isActiveStudent());
        Util.setYesNoToggleState(hasDisability, student.hasDisability());
        Util.setYesNoToggleState(sponsored, student.isSponsored());
        Util.setYesNoToggleState(topupNeeded, student.isTopupNeeded());

        sponsorshipStatus.getSelectionModel().select(student.getSponsorshipStatus());
        email.setText(student.getEmail());
        facebook.setText(student.getFacebookAddress());

        levelOfSupport.getSelectionModel().select(student.getLevelOfSupport());

        shortfall.setText(Util.safeToStringValue(student.getShortfall(), null));

        alumniNumber.setText(Util.safeToStringValue(student.getAlumniNumber(), null));

        leaverStatus.getSelectionModel().select(student.getLeaverStatus());
    }

    @Override
    public void deleteFired(Long id) {
        resetForm();
    }

    @FXML
    private void selectFamily() {
        FamilyDto dto = WindowsUtil.getInstance().familySelector();
        if(dto != null) {
            family.setUserData(dto);
            family.setText(dto.getFamilyName());
        }
    }

    @FXML
    private void save() {
        StudentDto dto = buildDto();
        if (isInputValid(dto)) {
            boolean isNew = dto.getId() == null;

            dto = studentsRepository.save(dto);  //looks like it generate too many sqls
            //refresh the table
            selected.get().withName(firstName.getText());
            selected.get().setFamily(dto.getFamily());

            if(isNew) { // fire event so table can be reloaded
                WindowsUtil.getInstance().getEventBus().fireEvent(Event.STUDENT_ADDED);
            }

            parentController.refresh();
        }
    }

    private StudentDto buildDto() {
        StudentDto dto = selected.get();
        dto.withName(firstName.getText())
                .withFamily((FamilyDto) family.getUserData())
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
            dto.withShortfall(Integer.valueOf(shortfall.getText()));
        }

        if (StringUtils.isNotBlank(alumniNumber.getText())) {
            dto.withAlumniNumber(Integer.valueOf(alumniNumber.getText()));
        }

        if (calendar.getValue() != null) {
            dto.withStartDate(LocalDate.fromDateFields(calendar.getValue()));
        }

        if (StringUtils.isNotBlank(yearOfBirth.getText())) {
            dto.withYearOfBirth(Integer.valueOf(yearOfBirth.getText()));
        }

        return dto;
    }

    private void resetForm() {
        calendar.clear();
        firstName.clear();
        family.clear();
        family.setUserData(null);
        gender.getSelectionModel().clearSelection();
        gender.valueProperty().setValue(null);

        yearOfBirth.clear();
        contactNumber.clear();
        homeLocation.clear();

        currentStudent.getToggles().get(0).setSelected(true);

        hasDisability.getToggles().get(0).setSelected(true);

        sponsored.getToggles().get(0).setSelected(true);

        topupNeeded.getToggles().get(0).setSelected(true);

        sponsorshipStatus.getSelectionModel().clearSelection();
        email.clear();
        facebook.clear();
        levelOfSupport.getSelectionModel().clearSelection();
        shortfall.clear();
        alumniNumber.clear();
        leaverStatus.getSelectionModel().clearSelection();
    }

    private boolean isInputValid(StudentDto dto) {
        String validation = FormValidator.validate(dto, getFields());

        if(StringUtils.isNotBlank(validation)) {
            WindowsUtil.getInstance().showErrorDialog(validation);
            return false;
        } else {
            return true;
        }

    }

    private Map<String, Node> getFields() {

        if(validationFields.isEmpty()) {
            validationFields.put("firstName", firstName);
            validationFields.put("family", family);
            validationFields.put("gender", gender);
            validationFields.put("yearOfBirth", yearOfBirth);
        }

        return validationFields;
    }
}
