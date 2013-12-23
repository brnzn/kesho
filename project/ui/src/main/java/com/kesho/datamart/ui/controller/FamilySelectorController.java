package com.kesho.datamart.ui.controller;

import com.google.common.collect.Lists;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.Event;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import np.com.ngopal.control.AutoFillTextBox;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;

/**
 */
@Named
public class FamilySelectorController {
    @Inject
    private FamilyRepository repository;
    private ObservableList<FamilyDto> data;
    @FXML
    private AutoFillTextBox<FamilyDto> family;

    private boolean okClicked = false;
	private Stage dialogStage;
	private FamilyDto dto;
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        okClicked = false;
        data = FXCollections.observableArrayList(repository.getFamilies());
        family.setData(data);

        family.getListview().getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                if (!change.getList().isEmpty()) {
                    dto = (FamilyDto)change.getList().get(0);
                }
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

    public FamilyDto getSelected() {
        return dto;
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
