package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.Util;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;

/**
 */
@Named
public class FamilyDialogController {
    @Inject
    private FamilyRepository repository;
    private Long id;

    @FXML
    private TextField familyName;
    private ComboBox<Location> homeLocation;
    private TextField homeSubLocation;
    private TextField homeClusterId;
    private Integer aliveParents;
    private ToggleGroup isMarried;
    private TextField numNonKeshoStudents;
    private TextField numOfWives;
    private TextField primaryCaretaker;
    private TextField mainContactName;
    private TextField mobileNumber;
    private ToggleGroup isPhoneOwner;
    private TextField phoneOwnerName;
    private TextField numOfStudentsAtAddress;
    private TextArea profile;
    private TextField numOfAdultsAtAddress;


    private Stage dialogStage;
	private EducationDto dto;
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        Util.initializeYesNoGroup(isMarried, isPhoneOwner);
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
	private void handleOk() {
        if (isInputValid()) {
            FamilyDto family = new FamilyDto(null, this.familyName.getText());
            repository.save(family);
            WindowsUtil.getInstance().getEventBus().fireEvent(Event.FAMILY_ADDED);
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

		if (StringUtils.isBlank(familyName.getText())) {
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
