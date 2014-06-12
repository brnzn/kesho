package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.FamilyDtoBuilder;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.PagingUtil;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.controlsfx.dialog.Dialogs;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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
    private TextField aliveParents;
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
    @FXML
    private Button deleteButton;

    private ObservableList<FamilyDto> familiesModel = FXCollections.observableArrayList();
    private Map<String, Node> validationFields = new HashMap<>();

    @FXML
    private TableView<FamilyDto> familiesTable;
    @FXML
    private TableColumn<FamilyDto, String> familyNameColumn;
    @FXML
    private Pagination pagination;

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

        familiesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FamilyDto>() {
            @Override
            public void changed(ObservableValue<? extends FamilyDto> observableValue, FamilyDto dto1, FamilyDto dto2) {
                if(dto2 != null) {
                    deleteButton.setDisable(false);
                } else {
                    deleteButton.setDisable(true);
                }

                refreshForm(dto2);
            }
        });

        Util.decorateNumericInput(numOfWives, aliveParents, numOfAdultsAtAddress, numNonKeshoStudents);
    }

    private void refreshForm(FamilyDto dto) {
        if(dto == null) {
            reset();
            return;
        }

        this.id = dto.getId();
        familyName.setText(dto.getFamilyName());
        homeLocation.setValue(dto.getHomeLocation());
        homeSubLocation.setText(dto.getHomeSubLocation());
        homeClusterId.setText(dto.getHomeClusterId());
        aliveParents.setText(Util.safeToStringValue(dto.getAliveParents(), null));
        Util.setYesNoToggleState(isMarried, dto.getMarried());
        numNonKeshoStudents.setText(Util.safeToStringValue(dto.getNumNonKeshoStudents(), null));
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
        this.id = null;
        familyName.clear();
        homeLocation.setValue(null);
        homeSubLocation.clear();
        homeClusterId.clear();
        aliveParents.clear();
        numNonKeshoStudents.clear();
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
        reset();
    }

    @FXML
    private void delete() {
        if(id != null) {
            repository.delete(id);
            refreshTable();
        }
    }

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void save() {
        FamilyDto family = buildDto();
        if (isInputValid(family)) {
            repository.save(family);
            WindowsUtil.getInstance().getEventBus().fireEvent(Event.FAMILY_ADDED);
			dialogStage.close();
		}
	}

    private FamilyDto buildDto() {
        FamilyDtoBuilder builder = new FamilyDtoBuilder(id, this.familyName.getText());
        return builder.withHomeLocation(homeLocation.getValue())
                .withHomeSubLocation(homeSubLocation.getText())
                .withHomeClusterId(homeClusterId.getText())
                .withAliveParents(Util.safeToIntegerValue(aliveParents.getText(), null))
                .isMarried((Boolean)isMarried.getSelectedToggle().getUserData())
                .withNumNonKeshoStudents(Util.safeToIntegerValue(numNonKeshoStudents.getText(), null))
                .withNumOfWives(Util.safeToIntegerValue(numOfWives.getText(), null))
                .withPrimaryCaretaker(primaryCaretaker.getText())
                .withMainContactName(mainContactName.getText())
                .withMobileNumber(mobileNumber.getText())
                .isPhoneOwner((Boolean)isPhoneOwner.getSelectedToggle().getUserData())
                .withPhoneOwnerName(phoneOwnerName.getText())
                .withProfile(profile.getText())
                .withNumOfAdultsAtAddress(Util.safeToIntegerValue(numOfAdultsAtAddress.getText(), null))
                .build();
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
        String validation = FormValidator.validate(dto, getFields());

        if(StringUtils.isNotBlank(validation)) {
            Dialogs.create()
                    .owner( dialogStage)
                    .title("Invalid Fields")
                    .masthead("Please correct invalid fields")
                    .message(validation)
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
            validationFields.put("numNonKeshoStudents", numNonKeshoStudents);
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

    private void refreshTable() {
        initTable();
        PagingUtil.initPagination(repository, familiesModel, pagination);
    }
}
