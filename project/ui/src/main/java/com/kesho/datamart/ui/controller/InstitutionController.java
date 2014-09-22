package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.ui.repository.SchoolRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class InstitutionController {
    @Inject
    private SchoolRepository schoolRepository;

    @FXML
    private TextField institutionName;
    @FXML
    private TableColumn<SchoolDto, String> institutionNameCol;

    @FXML
    private TableView<SchoolDto> institutionsTable;

    private ObservableList<SchoolDto> institutionsModel = FXCollections.observableArrayList();

    private Stage dialogStage;

    public InstitutionController() {
    }

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        initTable();
    }

    private void initTable() {
        institutionsModel.clear();
        institutionNameCol.setCellValueFactory(new PropertyValueFactory<SchoolDto, String>("name"));
        institutionsModel.addAll(schoolRepository.getAllSchools());
        institutionsTable.setItems(institutionsModel);
    }
	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void addInstitution() {
        if (isInputValid()) {
            schoolRepository.create(new SchoolDto(null, institutionName.getText()));
            new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            initTable();
                            return null;
                        }
                    };
                }
            }.start();

            institutionName.clear();
		}
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
    //TODO: bind button to text so no need to validate
	private boolean isInputValid() {
		if (StringUtils.isBlank(institutionName.getText())) {
            // Show the error message
//            Dialogs.showErrorDialog(dialogStage, "Please enter Institution Name");
            return false;
		}

        return true;
	}
}
