package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.ui.repository.InstitutionRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
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
    private InstitutionRepository institutionRepository;

    @FXML
    private TextField institutionName;
    @FXML
    private TableColumn<InstitutionDto, String> institutionNameCol;

    @FXML
    private TableView<InstitutionDto> institutionsTable;

    private ObservableList<InstitutionDto> institutionsModel = FXCollections.observableArrayList();

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
        institutionNameCol.setCellValueFactory(new PropertyValueFactory<InstitutionDto, String>("name"));
        institutionsModel.addAll(institutionRepository.getInstitutions());
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
            institutionRepository.create(new InstitutionDto(null, institutionName.getText()));
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
	private boolean isInputValid() {
		if (StringUtils.isBlank(institutionName.getText())) {
            // Show the error message
            Dialogs.showErrorDialog(dialogStage, "Please enter Institution Name");
            return false;
		}

        return true;
	}
}
