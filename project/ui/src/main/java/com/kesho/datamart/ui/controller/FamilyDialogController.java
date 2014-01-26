package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.FoundUs;
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
import java.util.EnumSet;

/**
 */
@Named
public class FamilyDialogController {
    @Inject
    private FamilyRepository repository;
    private Long id;

    @FXML
    private TextField familyName;
    @FXML
    private ComboBox<Location> homeLocation;
    @FXML
    private TextField homeSubLocation;
    @FXML
    private TextField homeClusterId;
    @FXML
    private Integer aliveParents;
    @FXML
    private ToggleGroup isMarried;
    @FXML
    private TextField numNonKeshoStudents;
    @FXML
    private TextField numOfWives;
    @FXML
    private TextField primaryCaretaker;
    @FXML
    private TextField mainContactName;
    @FXML
    private TextField mobileNumber;
    @FXML
    private ToggleGroup isPhoneOwner;
    @FXML
    private TextField phoneOwnerName;
    @FXML
    private TextArea profile;
    @FXML
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
        Util.initializeComboBoxValues(homeLocation, EnumSet.allOf(Location.class));
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
