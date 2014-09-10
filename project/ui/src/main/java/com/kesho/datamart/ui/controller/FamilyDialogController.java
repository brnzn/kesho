package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.PagingUtil;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import org.springframework.dao.OptimisticLockingFailureException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Named
public class FamilyDialogController {
    @Inject
    private FamilyRepository repository;

    @FXML
    private TextField familyName;
    @FXML
    private ComboBox<Location> homeLocation;
    @FXML
    private TextField homeSubLocation;
    @FXML
    private TextField homeClusterId;
    @FXML
    private TextField aliveParents;
    @FXML
    private ToggleGroup isMarried;
    @FXML
    private TextField numOfChildrenAtAddress;
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
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;

    private ObservableList<FamilyDto> familiesModel = FXCollections.observableArrayList();
    private Map<String, Node> validationFields = new HashMap<>();

    @FXML
    private TableView<FamilyDto> familiesTable;
    @FXML
    private TableColumn<FamilyDto, String> familyNameColumn;
    @FXML
    private Pagination pagination;
    private SimpleObjectProperty<FamilyDto> selected = new SimpleObjectProperty();

    private Stage dialogStage;

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        refreshTable();
        familyNameColumn.setCellValueFactory(new PropertyValueFactory<FamilyDto, String>("familyName"));

        Util.initializeYesNoGroup(isMarried, isPhoneOwner);
        Util.initializeComboBoxValues(homeLocation, EnumSet.allOf(Location.class));

        selected.addListener(new ChangeListener<FamilyDto>() {
            @Override
            public void changed(ObservableValue<? extends FamilyDto> observable, FamilyDto oldValue, FamilyDto newValue) {
                saveButton.setDisable(newValue == null);
                deleteButton.setDisable(newValue == null);
            }
        });

        familiesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FamilyDto>() {
            @Override
            public void changed(ObservableValue<? extends FamilyDto> observableValue, FamilyDto dto1, FamilyDto dto2) {
                selected.set(dto2);
                refreshForm();
            }
        });

        Util.decorateNumericInput(numOfWives, aliveParents, numOfAdultsAtAddress, numOfChildrenAtAddress);
    }

    private void refreshForm() {
        FamilyDto dto = selected.getValue();
        if(dto == null) {
            reset();
            return;
        }

        familyName.setText(dto.getFamilyName());
        homeLocation.setValue(dto.getHomeLocation());
        homeSubLocation.setText(dto.getHomeSubLocation());
        homeClusterId.setText(dto.getHomeClusterId());
        aliveParents.setText(Util.safeToStringValue(dto.getAliveParents(), null));
        Util.setYesNoToggleState(isMarried, dto.getMarried());
        numOfChildrenAtAddress.setText(Util.safeToStringValue(dto.getNumOfChildrenAtAddress(), null));
        numOfWives.setText(Util.safeToStringValue(dto.getNumOfWives(), null));
        primaryCaretaker.setText(dto.getPrimaryCaretaker());
        mainContactName.setText(dto.getMainContactName());
        mobileNumber.setText(dto.getMobileNumber());
        Util.setYesNoToggleState(isPhoneOwner, dto.getMarried());
        phoneOwnerName.setText(dto.getPhoneOwnerName());
        profile.setText(dto.getProfile());
        numOfAdultsAtAddress.setText(Util.safeToStringValue(dto.getNumOfAdultsAtAddress(), null));
    }

    private void reset() {
        familiesTable.getSelectionModel().clearSelection();
        familyName.clear();
        homeLocation.setValue(null);
        homeSubLocation.clear();
        homeClusterId.clear();
        aliveParents.clear();
        numOfChildrenAtAddress.clear();
        numOfWives.clear();
        primaryCaretaker.clear();
        mainContactName.clear();
        mobileNumber.clear();
        phoneOwnerName.clear();
        profile.clear();
        numOfAdultsAtAddress.clear();
    }

    @FXML
    private void doNew() {
        selected.set(new FamilyDto());
        reset();
        saveButton.setDisable(false);
    }

    //TODO: delete with version (entity)
    @FXML
    private void delete() {
        repository.delete(selected.get().getId());
        refreshTable();
    }

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void save() {
        FamilyDto family = buildDto();
        if (isInputValid(family)) {
            try {
                FamilyDto dto = repository.save(family);
                selected.get().withVersion(dto.getVersion());
                refreshTable();
            } catch(OptimisticLockingFailureException e) {
                WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save", "The record has been modified or deleted by someone else! Please refresh the table and re-select to update.");
            }
		}
	}

    private FamilyDto buildDto() {
        //FamilyDtoBuilder builder = new FamilyDtoBuilder(id, this.familyName.getText());
        FamilyDto dto = selected.get();
        dto.withFamilyName(familyName.getText())
            .withHomeLocation(homeLocation.getValue())
            .withHomeSubLocation(homeSubLocation.getText())
            .withHomeClusterId(homeClusterId.getText())
            .withAliveParents(Util.safeToIntegerValue(aliveParents.getText(), null))
            .isMarried((Boolean) isMarried.getSelectedToggle().getUserData())
            .withNumOfChildrenAtAddress(Util.safeToIntegerValue(numOfChildrenAtAddress.getText(), null))
            .withNumOfWives(Util.safeToIntegerValue(numOfWives.getText(), null))
            .withPrimaryCaretaker(primaryCaretaker.getText())
            .withMainContactName(mainContactName.getText())
            .withMobileNumber(mobileNumber.getText())
            .isPhoneOwner((Boolean) isPhoneOwner.getSelectedToggle().getUserData())
            .withPhoneOwnerName(phoneOwnerName.getText())
            .withProfile(profile.getText())
            .withNumOfAdultsAtAddress(Util.safeToIntegerValue(numOfAdultsAtAddress.getText(), null))
            ;

        return dto;
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
	private boolean isInputValid(FamilyDto dto) {
        List<String> validation = FormValidator.validate(dto, getFields());

        if(validation.size() > 0) {
            Dialogs.create()
                    .owner( dialogStage)
                    .title("Invalid Fields")
                    .masthead("Please correct invalid fields")
                    .message(FormValidator.reduce(validation))
                    .showError();
            return false;
        } else {
            return true;
        }
	}

    private Map<String, Node> getFields() {
        if(validationFields.isEmpty()) {
            validationFields.put("familyName", familyName);
            validationFields.put("homeLocation", homeLocation);
            validationFields.put("homeSubLocation", homeSubLocation);
            validationFields.put("homeClusterId", homeClusterId);
            validationFields.put("aliveParents", aliveParents);
            validationFields.put("numOfChildrenAtAddress", numOfChildrenAtAddress);
            validationFields.put("numOfWives", numOfWives);
            validationFields.put("primaryCaretaker", primaryCaretaker);
            validationFields.put("mainContactName", mainContactName);
            validationFields.put("mobileNumber", mobileNumber);
            validationFields.put("phoneOwnerName", phoneOwnerName);
            validationFields.put("numOfAdultsAtAddress", numOfAdultsAtAddress);
            validationFields.put("profile", profile);
        }
        return validationFields;
    }

    private void initTable() {
        familiesModel.clear();
        familiesTable.setItems(familiesModel);
    }

    @FXML
    private void refreshTable() {
        selected.set(null);
        initTable();
        PagingUtil.initPagination(repository, familiesModel, pagination);
    }
}
