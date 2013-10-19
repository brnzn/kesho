package com.kesho.crm.ui.controller;

import com.kesho.crm.dto.StudentDto;
import com.kesho.crm.ui.WindowsUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
    private void save() {
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
