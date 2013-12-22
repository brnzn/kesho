package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.Event;
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
public class FamilySelectorController {
    @Inject
    private FamilyRepository repository;
    @FXML
    private ComboBox<FamilyDto> family;

    private boolean okClicked = false;
	private Stage dialogStage;
	private EducationDto dto;
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        okClicked = false;
        family.setButtonCell(new FamilyListCell());
        family.setCellFactory(new Callback<ListView<FamilyDto>, ListCell<FamilyDto>>() {
            @Override public ListCell<FamilyDto> call(ListView<FamilyDto> p) {
                return new FamilyListCell();
            }
        });
        family.getItems().clear();
        family.getItems().addAll(repository.getFamilies());

	}
	
	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

    public FamilyDto getSelected() {
        return okClicked ? family.getSelectionModel().getSelectedItem() : null;
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
