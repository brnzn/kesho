package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.repository.StudentsRepository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 */
@Named
public class StudentSelectorController extends SelectorController<StudentDto> {
    @Inject
    private StudentsRepository repository;

    @Override
    public Collection<StudentDto> getData() {
        return repository.getStudents();
    }
//    private ObservableList<StudentDto> data;
//    @FXML
//    private TextField student;
//
//    private boolean okClicked = false;
//	private Stage dialogStage;
//
//    @FXML
//    private Button okButton;
//
//    private AutoCompletionBinding<StudentDto> binding;
//    private SimpleObjectProperty<StudentDto> selected = new SimpleObjectProperty<>();
//
//
//    /**
//	 * Initializes the controller class. This method is automatically called
//	 * after the fxml file has been loaded.
//	 */
//	@FXML
//	private void initialize() {
//        okClicked = false;
//        okButton.setDisable(true);
//        data = FXCollections.observableArrayList(repository.getStudents());
//        binding = TextFields.bindAutoCompletion(student, data);
//
//        binding.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<StudentDto>>() {
//            @Override
//            public void handle(AutoCompletionBinding.AutoCompletionEvent<StudentDto> studentDtoAutoCompletionEvent) {
//                selected.set(studentDtoAutoCompletionEvent.getCompletion());
//            }
//        });
//
//        selected.addListener(new ChangeListener<StudentDto>() {
//            @Override
//            public void changed(ObservableValue<? extends StudentDto> observableValue, StudentDto dto, StudentDto dto2) {
//                if(dto2 == null) {
//                    okButton.setDisable(true);
//                } else {
//                    okButton.setDisable(false);
//                }
//            }
//        });
//	}
//
//	/**
//	 * Sets the stage of this dialog.
//	 * @param dialogStage
//	 */
//	public void setDialogStage(Stage dialogStage) {
//		this.dialogStage = dialogStage;
//	}
//
//    public StudentDto getSelected() {
//        return null; //student.getSelected();
//    }
//	/**
//	 * Called when the user clicks ok.
//	 */
//	@FXML
//	private void handleOk() {
//        okClicked = true;
//        dialogStage.close();
//	}
//
//	/**
//	 * Called when the user clicks cancel.
//	 */
//	@FXML
//	private void handleCancel() {
//		dialogStage.close();
//	}
	
}
