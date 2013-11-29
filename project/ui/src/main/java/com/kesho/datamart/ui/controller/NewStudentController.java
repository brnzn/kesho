package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.service.StudentService;
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
    private ToggleGroup currentStudentGroup;       //TODO: use value true/false
    @FXML
    private ComboBox<String> comboBox;
    private Long currentId;

    @Inject
    private StudentsRepository studentsRepository;

    public void setSelectedStudent(StudentDto student) {
        if(student == null) {
            return;
        }

        currentId = student.getId();
        firstName.setText(student.getName());
        surname.setText(student.getFamilyName());

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
                currentStudentGroup.getToggles().get(0).setSelected(true);
            } else {
                currentStudentGroup.getToggles().get(1).setSelected(true);
            }
        }
    }

    @FXML
    private void initialize() {
        currentId = null;
        gender.getItems().clear();
        gender.getItems().addAll(Gender.values());
        comboBox.getItems().clear();
        comboBox.getItems().addAll("Yes", "No");
        currentStudentGroup.getToggles().get(0).setUserData(Boolean.TRUE);
        currentStudentGroup.getToggles().get(1).setUserData(Boolean.FALSE);
        currentStudentGroup.getToggles().get(0).setSelected(true);
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
                .activeStudent((Boolean)currentStudentGroup.getSelectedToggle().getUserData());

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
