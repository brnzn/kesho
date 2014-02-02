package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.repository.StudentsRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.kesho.ui.control.autofilltextbox.AutoFillTextBox;

import javax.inject.Inject;
import javax.inject.Named;

/**
 */
@Named
public class StudentSelectorController {
    @Inject
    private StudentsRepository repository;
    private ObservableList<StudentDto> data;
    @FXML
    private AutoFillTextBox<StudentDto> student;

    private boolean okClicked = false;
	private Stage dialogStage;

    @FXML
    private Button okButton;

    /**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        okClicked = false;
        okButton.setDisable(true);
        data = FXCollections.observableArrayList(repository.getStudents());
        student.setData(data);

        student.selected().addListener(new ChangeListener<StudentDto>() {
            @Override
            public void changed(ObservableValue<? extends StudentDto> observableValue, StudentDto familyDto, StudentDto familyDto2) {
                if(familyDto2 == null) {
                    okButton.setDisable(true);
                } else {
                    okButton.setDisable(false);
                }
            }
        });
	}
	
	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

    public StudentDto getSelected() {
        return student.getSelected();
    }
	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
        okClicked = true;
        dialogStage.close();
	}
	
	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
}
