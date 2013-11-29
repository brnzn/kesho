package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.StudentsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.apache.commons.lang.StringUtils;

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

    private Long currentId;

    @Inject
    private StudentsRepository studentsRepository;

    public void setSelectedStudent(StudentDto student) {
        if(student == null) {
            return;
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

    }

    @FXML
    private void initialize() {
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
    }

    @FXML
    private void save() {
//        System.out.println(comboBox.getSelectionModel().getSelectedItem());
//        System.out.println("===> " + currentStudentGroup.getSelectedToggle().getUserData());
//
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
                .sponsored((Boolean)sponsored.getSelectedToggle().getUserData());

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
