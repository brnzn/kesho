package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.ui.repository.FamilyRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.kesho.ui.control.autofilltextbox.AutoFillTextBox;

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
    @FXML
    private Button okButton;

    private boolean okClicked = false;
	private Stage dialogStage;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        okClicked = false;
        okButton.setDisable(true);
        data = FXCollections.observableArrayList(repository.getFamilies());
        family.setData(data);

        family.selected().addListener(new ChangeListener<FamilyDto>() {
            @Override
            public void changed(ObservableValue<? extends FamilyDto> observableValue, FamilyDto familyDto, FamilyDto familyDto2) {
                if(familyDto2 == null) {
                    okButton.setDisable(true);
                } else {
                    okButton.setDisable(false);
                }
            }
        });

//        family.getListview().itemsProperty().addListener(new ChangeListener(){
//            @Override
//            public void changed(ObservableValue ov, Object t, Object t1) {
//                System.out.println("My Changed:"+t1);
//                if(family.getListview().getItems().size() == 0){
//                    okButton.setDisable(true);
//                } else {
//                    okButton.setDisable(false);
//                }
//            }
//        });
//
//        family.getTextbox().textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
//                if(StringUtils.isBlank(s2)) {
//                    okButton.setDisable(true);
//                }
//            }
//        });
    }
	
	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

    public FamilyDto getSelected() {
        return family.getSelected();
    }
	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
//        if(!family.getSelected().getName().equals(family.getText())) {
//            System.out.println("invalid..................");
//            return;
//        }
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
