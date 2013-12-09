package com.kesho.datamart.ui.controller;

import calendar.FXCalendar;
import com.kesho.datamart.domain.EducationStatus;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.ui.repository.InstitutionRepository;
import com.kesho.datamart.ui.util.Util;
import javafx.beans.property.ObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;

/**
 */
@Named
public class EducationDialogController {
    @FXML
    private HBox dateControlBox;
    private final FXCalendar calendar = new FXCalendar();
    @FXML
    private ComboBox<InstitutionDto> institutions;
    @FXML
    private TextField educationYear;
    @FXML
    private TextField course;
    @FXML
    private ComboBox<EducationStatus> educationalStatus;
    @FXML
    private ComboBox<String> secondaryStatus1;
    @FXML
    private ComboBox<String> secondaryStatus2;

	private Stage dialogStage;
	private EducationDto dto;
	private boolean okClicked = false;

    @Inject
    private InstitutionRepository institutionRepository;

    public EducationDialogController() {
        calendar.setDateTextWidth(Double.valueOf(100));
    }
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        dateControlBox.getChildren().add(calendar);
        Util.initializeComboBoxValues(educationalStatus, EnumSet.allOf(EducationStatus.class));
	}
	
	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPerson(EducationDto dto) {
        populateInstitutions();

        this.dto = dto;
        if(dto.getDate() != null) {
            calendar.setValue(dto.getDate().toDate());
        }

        educationYear.setText(Util.safeToStringValue(dto.getYear(), null));
        course.setText(dto.getCourse());
        secondaryStatus1.getSelectionModel().select(dto.getSecondaryEducationStatus1());
        secondaryStatus2.getSelectionModel().select(dto.getSecondaryEducationStatus2());

        if (dto.getEducationalStatus() != null) {
            educationalStatus.getSelectionModel().select(dto.getEducationalStatus());
        }
	}

    private void populateInstitutions() {
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        institutions.getItems().clear();
                        institutions.getItems().addAll(institutionRepository.getInstitutions());
                        institutions.getSelectionModel().select(new InstitutionDto(8L, "school1"));
                        return null;
                    }
                };
            }
        }.start();
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
            dto.withYear(Util.safeToIntegerValue(educationYear.getText(), null))
               .withEducationalStatus(educationalStatus.getSelectionModel().getSelectedItem())
               .withCourse(course.getText())
               .withSecondaryStatus1(secondaryStatus1.getSelectionModel().getSelectedItem())
               .withSecondaryStatus2(secondaryStatus2.getSelectionModel().getSelectedItem())
                .withInstitution(institutions.getSelectionModel().getSelectedItem());

            if(calendar.getValue() != null) {
                dto.withEducationDate(LocalDate.fromDateFields(calendar.getValue()));
            }

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

		if (institutions.getSelectionModel().isEmpty()) {
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
