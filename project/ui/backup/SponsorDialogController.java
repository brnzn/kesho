package backup;

import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.ui.WindowsUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;

import javax.inject.Named;

/**
 */
@Named
public class SponsorDialogController {
    @FXML
    private TextField firstName;
    @FXML
    private TextField surname;

	private Stage dialogStage;
	private SponsorDto dto;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        WindowsUtil.getInstance().autowire(this);
	}
	
	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setDto(SponsorDto dto) {
        this.dto = dto;

        firstName.setText(dto.getName());
        surname.setText(dto.getSurname());
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
            dto.setName(firstName.getText());
            dto.setSurname(surname.getText());
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

        if (StringUtils.isBlank(surname.getText())) {
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
}
