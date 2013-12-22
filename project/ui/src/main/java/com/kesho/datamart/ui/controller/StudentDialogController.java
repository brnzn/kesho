package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.repository.InstitutionRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;

/**
 */
@Named
public class StudentDialogController {
    @FXML
    private TextField firstName;
    @FXML
    private ComboBox<FamilyDto> family;

	private Stage dialogStage;
	private StudentDto dto;
	private boolean okClicked = false;

    @Inject
    private FamilyRepository familyRepository;

    public StudentDialogController() {
    }
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        family.setButtonCell(new FamilyListCell());
        family.setCellFactory(new Callback<ListView<FamilyDto>, ListCell<FamilyDto>>() {
            @Override public ListCell<FamilyDto> call(ListView<FamilyDto> p) {
                return new FamilyListCell();
            }
        });

        WindowsUtil.getInstance().autowire(this);

        Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        family.getItems().clear();
                        family.getItems().addAll(familyRepository.getFamilies());
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

	public void setDto(StudentDto dto) {
        this.dto = dto;

        firstName.setText(dto.getName());
        family.getSelectionModel().select(dto.getFamily());
	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
	
	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
        if (isInputValid()) {
            dto.withFamily(family.getSelectionModel().getSelectedItem())
               .withName(firstName.getText());
			okClicked = true;
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

		if (StringUtils.isBlank(firstName.getText())) {
			errorMessage += "First Name!\n";
		}

        if (family.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Surname!\n";
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

//    private class FamilyListCell extends ListCell<FamilyDto> {
//        @Override
//        protected void updateItem(FamilyDto item, boolean empty) {
//            super.updateItem(item, empty);
//            if (item != null) {
//                setText(item.getName());
//            }
//        }
//    }
}
