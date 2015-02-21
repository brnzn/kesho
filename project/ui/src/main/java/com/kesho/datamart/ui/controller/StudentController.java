package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.domain.LeaverStatus;
import com.kesho.datamart.domain.Location;
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

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentController extends AbstractEditableController<StudentDto> implements FormActionListener {
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
    private ComboBox<Location> homeLocation;
    @FXML
    private ToggleGroup hasDisability;
    @FXML
    private TextField email;
    @FXML
    private TextField facebook;
    @FXML
    private Button saveButton;


    @Inject
    private StudentsController parentController;
    @Inject
    private FamilyRepository familyRepository;

    private Map<String, Node> validationFields = new HashMap<>();

    public StudentController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @Override
    public void refresh() {
        saveButton.setDisable(selected.getValue() == null);
        itemSelected(selected.get());
    }


    @FXML
    private void initialize() {
        Util.decorateNumericInput(yearOfBirth);

        family.setUserData(null);

        Util.initializeYesNoGroup(hasDisability);

        gender.getToggles().get(0).setUserData(Gender.F);
        gender.getToggles().get(1).setUserData(Gender.M);
        Util.initializeComboBoxValues(homeLocation, EnumSet.allOf(Location.class));
    }

    @Override
    public void newFired() {
        resetForm();
        selected.unbind();
        selected.set(new StudentDto());
    }

    private  void itemSelected(StudentDto student) {
        if(student == null) {
            resetForm();
            return;
        }

        firstName.setText(student.getFirstName());

        if(student.getFamily() != null) {
            family.setText(student.getFamily().getFamilyName());
        } else {
            family.setText(null);
        }

        family.setUserData(student.getFamily());

        if (Gender.F.equals(student.getGender())) {
            gender.getToggles().get(0).setSelected(true);
        } else if (Gender.M.equals(student.getGender())) {
            gender.getToggles().get(1).setSelected(true);
        }

        yearOfBirth.setText(Util.safeToStringValue(student.getYearOfBirth(), null));

        contactNumber.setText(student.getMobileNumber());
        setHomeLocation();
        Util.setYesNoToggleState(hasDisability, student.hasDisability());

        email.setText(student.getEmail());
        facebook.setText(student.getFacebookAddress());
    }

    @Override
    public void deleteFired(Long id) {
        //Student cannot be deleted
    }

    private void setHomeLocation() {
        StudentDto dto = selected.get();
        if(dto.getHomeLocation() == null && dto.getFamily() != null) {
            homeLocation.setValue(dto.getFamily().getHomeLocation());
        } else {
            homeLocation.setValue(dto.getHomeLocation());
        }
    }

    @FXML
    private void selectFamily() {
        FamilyDto dto = WindowsUtil.getInstance().familySelector();
        if(dto != null) {
            family.setUserData(dto);
            family.setText(dto.getFamilyName());
        }
    }

    protected void doSave() {
        StudentDto dto = buildDto();
        if (isInputValid(dto)) {
            boolean isNew = dto.getId() == null;

            dto = studentsRepository.save(dto);  //looks like it generate too many sqls

            selected.get().withVersion(dto.getVersion());

            if(isNew) { // fire event so childrenTable can be reloaded
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
                .withHasDisability((Boolean) hasDisability.getSelectedToggle().getUserData())
                .withEmail(email.getText())
                .withFacebookAddress(facebook.getText())
                .withGender((Gender) gender.getSelectedToggle().getUserData())
                //.withAlumniNumber(Util.safeToIntegerValue(alumniNumber.getText(), null))
                .withYearOfBirth(Util.safeToIntegerValue(yearOfBirth.getText(), null))
        ;

        if(dto.getFamily() != null) {
            if(dto.getFamily().getHomeLocation() != homeLocation.getValue()) {
                dto.withHomeLocation(homeLocation.getValue());
            } else if(dto.getHomeLocation() != homeLocation.getValue() && dto.getFamily().getHomeLocation() == homeLocation.getValue()) {
                //home location changed and now match family home location, so no need to store it on student childrenTable
                dto.withHomeLocation(null);
            }
        }

        return dto;
    }

    private void resetForm() {
        firstName.clear();
        family.clear();
        family.setUserData(null);
        gender.getToggles().get(0).setSelected(true);

        yearOfBirth.clear();
        contactNumber.clear();
        homeLocation.setValue(null);

        hasDisability.getToggles().get(0).setSelected(true);
        email.clear();
        facebook.clear();
        //alumniNumber.clear();
    }

    private boolean isInputValid(StudentDto dto) {
        List<String> validation = FormValidator.validate(dto, getValidateableFields());

        if(validation.size() > 0) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save Student details", FormValidator.reduce(validation));
            return false;
        } else {
            return true;
        }

    }

    protected Map<String, Node> getValidateableFields() {

        if(validationFields.isEmpty()) {
            validationFields.put("firstName", firstName);
            validationFields.put("family", family);
            validationFields.put("gender", genderBox);
            validationFields.put("yearOfBirth", yearOfBirth);
        }

        return validationFields;
    }
}
