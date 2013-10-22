package com.kesho.crm.ui.controller;

import com.kesho.crm.dto.StudentDto;
import com.kesho.crm.ui.WindowsUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 10/19/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class NewStudentController {
    @FXML
    private TextField firstName;
    @FXML
    private TextField familyName;
    @FXML
    private TextField gender;
    @FXML
    private TextField yearOfBirth;
    @FXML
    private TextField contactNumber;
    @FXML
    private TextField homeLocation;
    @FXML
    private ToggleGroup currentStudentGroup;
    @FXML
    private ComboBox<String> comboBox;


    @FXML
    private void initialize() {
        comboBox.getItems().clear();
        comboBox.getItems().addAll("Yes", "No");
        currentStudentGroup.getToggles().get(0).setSelected(true);
//        yes.setUserData("yes");
//        no.setUserData("no");
    }

    @FXML
    private void save() {
        System.out.println(comboBox.getSelectionModel().getSelectedItem());
        System.out.println(currentStudentGroup.getSelectedToggle());
        StudentDto student = new StudentDto();
        student.setName(firstName.getText());
        student.setFamilyName(familyName.getText());
        WindowsUtil.getInstance().getControllers().getStudentsController().add(student);

        try {
            WindowsUtil.getInstance().showStudentsTable();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @FXML
    private void cancel() throws IOException {
        WindowsUtil.getInstance().showStudentsTable();
    }

}
