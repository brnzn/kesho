package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.ui.repository.InstitutionRepository;
import com.kesho.datamart.ui.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class InstitutionController {
    @Inject
    private InstitutionRepository institutionRepository;

    @FXML
    private TextField institutionName;

	private Stage dialogStage;
//	private boolean okClicked = false;

    public InstitutionController() {
    }

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}
	
	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

//	/**
//	 * Returns true if the user clicked OK, false otherwise.
//	 * @return
//	 */
//	public boolean isOkClicked() {
//		return okClicked;
//	}
//
	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
        if (isInputValid()) {
            institutionRepository.create(new InstitutionDto(null, institutionName.getText()));
//			okClicked = true;
			dialogStage.close();
		}
	}
	
	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (StringUtils.isBlank(institutionName.getText())) {
			errorMessage += "Institution Name!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message
			Dialogs.showErrorDialog(dialogStage, errorMessage,
					"Please correct invalid fields", "Invalid Fields");
			return false;
		}
	}
}
