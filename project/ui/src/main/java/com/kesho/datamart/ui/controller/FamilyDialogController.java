package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.*;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
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

    private ObservableList<FamilyDto> familiesModel = FXCollections.observableArrayList();
    private Effect invalidEffect = new DropShadow(BlurType.GAUSSIAN, Color.RED, 4, 0.0, 0, 0);


    @FXML
    private TableView<FamilyDto> familiesTable;
    @FXML
    private TableColumn<FamilyDto, String> familyNameColumn;
    @FXML
    private Pagination pagination;

    private Stage dialogStage;
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        refreshTable();
        familyNameColumn.setCellValueFactory(new PropertyValueFactory<FamilyDto, String>("name"));

        Util.initializeYesNoGroup(isMarried, isPhoneOwner);
        Util.initializeComboBoxValues(homeLocation, EnumSet.allOf(Location.class));

        familiesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FamilyDto>() {
            @Override
            public void changed(ObservableValue<? extends FamilyDto> observableValue, FamilyDto dto1, FamilyDto dto2) {
                refreshForm(dto2);
            }
        });

    }

    private void refreshForm(FamilyDto dto) {
        if(dto == null) {
            reset();
            return;
        }

        this.id = dto.getId();
        familyName.setText(dto.getName());
        homeLocation.setValue(dto.getHomeLocation());
        homeSubLocation.setText(dto.getHomeSubLocation());
        homeClusterId.setText(dto.getHomeClusterId());
        aliveParents.setText(Util.safeToStringValue(dto.getAliveParents(), null));
        setState(isMarried, dto.getMarried());
        numNonKeshoStudents.setText(Util.safeToStringValue(dto.getNumNonKeshoStudents(), null));
        numOfWives.setText(Util.safeToStringValue(dto.getNumOfWives(), null));
        primaryCaretaker.setText(dto.getPrimaryCaretaker());
        mainContactName.setText(dto.getMainContactName());
        mobileNumber.setText(dto.getMobileNumber());
        setState(isPhoneOwner, dto.getMarried());
        phoneOwnerName.setText(dto.getPhoneOwnerName());
        profile.setText(dto.getProfile());
        numOfAdultsAtAddress.setText(Util.safeToStringValue(dto.getNumOfAdultsAtAddress(), null));
    }

    private void reset() {
        familiesTable.getSelectionModel().clearSelection();
        this.id = null;
        familyName.setText(null);
        homeLocation.setValue(null);
        homeSubLocation.setText(null);
        homeClusterId.setText(null);
        aliveParents.setText(null);
        numNonKeshoStudents.setText(null);
        numOfWives.setText(null);
        primaryCaretaker.setText(null);
        mainContactName.setText(null);
        mobileNumber.setText(null);
        phoneOwnerName.setText(null);
        profile.setText(null);
        numOfAdultsAtAddress.setText(null);
    }
    /**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

    private void setState(ToggleGroup group, Boolean value) {
        if (value == null || value) {
            group.getToggles().get(0).setSelected(true);
        } else {
            group.getToggles().get(1).setSelected(true);
        }
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
        if (isInputValid()) {
            FamilyDto family = create();
            repository.save(family);
            WindowsUtil.getInstance().getEventBus().fireEvent(Event.FAMILY_ADDED);
			dialogStage.close();
		}
	}

    private FamilyDto create() {
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
	private boolean isInputValid() {
		String errorMessage = "";

		if (StringUtils.isBlank(familyName.getText())) {
			errorMessage += "Family Name is mandatory!\n";
            familyName.setEffect(invalidEffect);
		} else {
            familyName.setEffect(null);
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

    private void initTable() {
        familiesModel.clear();
        familiesTable.setItems(familiesModel);

        familiesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FamilyDto>() {
            @Override
            public void changed(ObservableValue<? extends FamilyDto> observableValue, FamilyDto dto1, FamilyDto dto2) {
                System.out.println("====================");
            }
        });
    }

    private void initPagination() {
        Page p = getPage(0, 10);
        if(p != null) {
            familiesModel.addAll(p.getContent());    //
            pagination.setPageCount(p.getTotalPages() > 0 ? p.getTotalPages() : 1);
            pagination.currentPageIndexProperty().setValue(0);
        }

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Page<FamilyDto> p = getPage(newValue.intValue(), 10);
                pagination.setPageCount(p.getTotalPages());
                familiesModel.clear();
                familiesModel.addAll(p.getContent());
            }
        });
    }

    private Page<FamilyDto> getPage(final int page, final int pageSize) {
        return repository.getPage(page, pageSize);
    }

    private void refreshTable() {
        initTable();
        initPagination();
    }
}
