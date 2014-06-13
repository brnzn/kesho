package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.*;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.FormActionListener;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: After New and Update need to refresh table, but leave form as is
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
    private HBox genderBox;

    @FXML
    private ToggleGroup gender;
    @FXML
    private TextField yearOfBirth; //numeric
    @FXML
    private TextField contactNumber;
    @FXML
    private TextField homeLocation;
    @FXML
    private ToggleGroup hasDisability;
    @FXML
    private ToggleGroup enrichmentSupport;

    @FXML
    private ToggleGroup financialSupport;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<FinancialSupportStatus> financialSupportStatus;
    @FXML
    private ComboBox<FinancialSupportStatusDetails> financialSupportStatusDetails;
    @FXML
    private TextField otherFinancialSupportStatusDetails;

    @FXML
    private TextField email;
    @FXML
    private TextField facebook;
    @FXML
    private ComboBox<LevelOfSupport> levelOfSupport;
    @FXML
    private ToggleGroup topupNeeded;
    @FXML
    private TextField shortfall;    // numeric
    @FXML
    private TextField alumniNumber; // numeric
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
        WindowsUtil.getInstance().autowire(this);
    }

    @FXML
    private void initialize() {
        Util.decorateNumericInput(yearOfBirth, shortfall, alumniNumber);

        selected.addListener(new ChangeListener<StudentDto>() {
            @Override
            public void changed(ObservableValue<? extends StudentDto> observableValue, StudentDto studentDto, StudentDto studentDto2) {
                saveButton.setDisable(studentDto2 == null);
            }
        });

        selected.setValue(null);
        family.setUserData(null);

        Util.initializeYesNoGroup(hasDisability, financialSupport, topupNeeded, enrichmentSupport);

        gender.getToggles().get(0).setUserData(Gender.F);
        gender.getToggles().get(1).setUserData(Gender.M);

        Util.initializeComboBoxValues(leaverStatus, EnumSet.allOf(LeaverStatus.class));
        Util.initializeComboBoxValues(financialSupportStatus, EnumSet.allOf(FinancialSupportStatus.class));

        financialSupportStatus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FinancialSupportStatus>() {
            @Override
            public void changed(ObservableValue<? extends FinancialSupportStatus> observableValue, FinancialSupportStatus s, FinancialSupportStatus s2) {
                financialSupportStatusDetails.getItems().clear();
                if(s2 != null) {
                    if(FinancialSupportStatus.OTHER != s2) {
                        financialSupportStatusDetails.getItems().addAll(s2.getChildren());
                        financialSupportStatusDetails.setVisible(true);
                        otherFinancialSupportStatusDetails.setVisible(false);
                        otherFinancialSupportStatusDetails.clear();
                    } else {
                        financialSupportStatusDetails.setVisible(false);
                        financialSupportStatusDetails.getSelectionModel().clearSelection();
                        otherFinancialSupportStatusDetails.setVisible(true);
                    }
                }
            }
        });

        Util.initializeComboBoxValues(levelOfSupport, EnumSet.allOf(LevelOfSupport.class));
    }

    @Override
    public void newFired() {
        resetForm();
        selected.set(new StudentDto());
    }

    void itemSelected(StudentDto student) {
        if(student == null) {
            resetForm();
            return;
        }

        selected.setValue(student);

        startDate.valueProperty().set(Util.toJavaDate(student.getStartDate()));

        firstName.setText(student.getFirstName());

        family.setText(student.getFamily().getFamilyName());
        family.setUserData(student.getFamily());

        if (Gender.F.equals(student.getGender())) {
            gender.getToggles().get(0).setSelected(true);
        } else if (Gender.M.equals(student.getGender())) {
            gender.getToggles().get(1).setSelected(true);
        }

        yearOfBirth.setText(Util.safeToStringValue(student.getYearOfBirth(), null));

        contactNumber.setText(student.getMobileNumber());
        homeLocation.setText(student.getHomeLocation());

        Util.setYesNoToggleState(hasDisability, student.hasDisability());
        Util.setYesNoToggleState(financialSupport, student.hasFinancialSupport());
        Util.setYesNoToggleState(enrichmentSupport, student.getEnrichmentSupport());
        Util.setYesNoToggleState(topupNeeded, student.isTopupNeeded());


        financialSupportStatus.getSelectionModel().select(student.getFinancialSupportStatus());
        if(student.getFinancialSupportStatus() != null && FinancialSupportStatus.OTHER != student.getFinancialSupportStatus() && student.getFinancialSupportStatusDetails() != null) {
            financialSupportStatusDetails.getSelectionModel().select(FinancialSupportStatusDetails.valueOf(student.getFinancialSupportStatusDetails()));
        } else if (FinancialSupportStatus.OTHER == student.getFinancialSupportStatus()){
            otherFinancialSupportStatusDetails.setText(student.getFinancialSupportStatusDetails());
        }

        email.setText(student.getEmail());
        facebook.setText(student.getFacebookAddress());

        levelOfSupport.getSelectionModel().select(student.getLevelOfSupport());

        shortfall.setText(Util.safeToStringValue(student.getShortfall(), null));

        alumniNumber.setText(Util.safeToStringValue(student.getAlumniNumber(), null));

        leaverStatus.getSelectionModel().select(student.getLeaverStatus());
    }

    @Override
    public void deleteFired(Long id) {
        //Student cannot be deleted
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
                .withHasDisability((Boolean) hasDisability.getSelectedToggle().getUserData())
                .withEnrichmentSupport((Boolean) enrichmentSupport.getSelectedToggle().getUserData())
                .withFinancialSupport((Boolean) financialSupport.getSelectedToggle().getUserData())
                .withEmail(email.getText())
                .withFacebookAddress(facebook.getText())
                .withTopupNeeded((Boolean) topupNeeded.getSelectedToggle().getUserData())
                .withLeaverStatus(leaverStatus.getSelectionModel().getSelectedItem())
                .withFinancialSupportStatus(financialSupportStatus.getSelectionModel().getSelectedItem())
                .withFinancialSupportStatusDetails(getFinancialSupportStatusDetails())
                .withLevelOfSupport(levelOfSupport.getSelectionModel().getSelectedItem())
                .withGender((Gender) gender.getSelectedToggle().getUserData())
                .withStartDate(Util.toJodaDate(startDate.getValue()));

        if (StringUtils.isNotBlank(shortfall.getText())) {
            dto.withShortfall(Integer.valueOf(shortfall.getText()));
        }

        if (StringUtils.isNotBlank(alumniNumber.getText())) {
            dto.withAlumniNumber(Integer.valueOf(alumniNumber.getText()));
        }

        if (StringUtils.isNotBlank(yearOfBirth.getText())) {
            dto.withYearOfBirth(Integer.valueOf(yearOfBirth.getText()));
        }

        return dto;
    }

    private String getFinancialSupportStatusDetails() {
        return FinancialSupportStatus.OTHER == financialSupportStatus.getSelectionModel().getSelectedItem() ?
               otherFinancialSupportStatusDetails.getText() :
               financialSupportStatusDetails.getSelectionModel().getSelectedItem() != null ?
                       financialSupportStatusDetails.getSelectionModel().getSelectedItem().name() : null;

    }
    private void resetForm() {
        startDate.valueProperty().setValue(null);
        firstName.clear();
        family.clear();
        family.setUserData(null);
        gender.getToggles().get(0).setSelected(true);

        yearOfBirth.clear();
        contactNumber.clear();
        homeLocation.clear();

        hasDisability.getToggles().get(0).setSelected(true);
        enrichmentSupport.getToggles().get(0).setSelected(true);
        financialSupport.getToggles().get(0).setSelected(true);

        topupNeeded.getToggles().get(0).setSelected(true);

        financialSupportStatus.getSelectionModel().clearSelection();
        email.clear();
        facebook.clear();
        levelOfSupport.getSelectionModel().clearSelection();
        shortfall.clear();
        alumniNumber.clear();
        leaverStatus.getSelectionModel().clearSelection();
    }

    private boolean isInputValid(StudentDto dto) {
        List<String> validation = FormValidator.validate(dto, getFields());

        if(validation.size() > 0) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save Student details", FormValidator.reduce(validation));
            return false;
        } else {
            return true;
        }

    }

    private Map<String, Node> getFields() {

        if(validationFields.isEmpty()) {
            validationFields.put("firstName", firstName);
            validationFields.put("family", family);
            validationFields.put("gender", genderBox);
            validationFields.put("yearOfBirth", yearOfBirth);
        }

        return validationFields;
    }
}
