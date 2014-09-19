package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.ui.FormActionListener;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javax.inject.Inject;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyDetailsController extends AbstractFamilyDetailsController<FamilyDto> implements FormActionListener {
    @FXML
    private Button saveButton;

    private Map<String, Node> validationFields = new HashMap<>();


    @Inject
    protected FamiliesController parentController;
    @Inject
    protected FamilyRepository repository;

    public FamilyDetailsController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @FXML
    private void initialize() {
        Util.initializeYesNoGroup(isMarried, isPhoneOwner);
        Util.initializeComboBoxValues(homeLocation, EnumSet.allOf(Location.class));
        Util.decorateNumericInput(numOfWives, aliveParents, numOfAdultsAtAddress, numOfChildrenAtAddress);
    }

    @Override
    public void newFired() {
        resetForm();
        selected.unbind();
        selected.set(new FamilyDto());
    }

    @Override
    public void deleteFired(Long id) {
    }

    @Override
    public void refresh(FamilyDto dto) {
        saveButton.setDisable(dto == null);

        if (dto == null) {
            resetForm();
            return;
        }

        FamilyDto familyDto = dto;//dto.getFamily();
        familyName.setText(familyDto.getFamilyName());
        homeLocation.setValue(familyDto.getHomeLocation());
        homeSubLocation.setText(familyDto.getHomeSubLocation());
        homeClusterId.setText(familyDto.getHomeClusterId());
        aliveParents.setText(Util.safeToStringValue(familyDto.getAliveParents(), null));
        Util.setYesNoToggleState(isMarried, familyDto.getMarried());
        numOfChildrenAtAddress.setText(Util.safeToStringValue(familyDto.getNumOfChildrenAtAddress(), null));
        numOfWives.setText(Util.safeToStringValue(familyDto.getNumOfWives(), null));
        primaryCaretaker.setText(familyDto.getPrimaryCaretaker());
        mainContactName.setText(familyDto.getMainContactName());
        mobileNumber.setText(familyDto.getMobileNumber());
        Util.setYesNoToggleState(isPhoneOwner, familyDto.getMarried());
        phoneOwnerName.setText(familyDto.getPhoneOwnerName());
        profile.setText(familyDto.getProfile());
        numOfAdultsAtAddress.setText(Util.safeToStringValue(familyDto.getNumOfAdultsAtAddress(), null));
    }

    private void resetForm() {
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

    @Override
    protected Map<String, Node> getValidateableFields() {
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

    @Override
    protected void doSave() {
        FamilyDto family = buildDto();
        if (isInputValid(family)) {
            boolean isNew = family.getId() == null;

            FamilyDto dto = repository.save(family);
            selected.get().withVersion(dto.getVersion());

            if(isNew) { // fire event so childrenTable can be reloaded
                WindowsUtil.getInstance().getEventBus().fireEvent(Event.FAMILY_ADDED);
            }

            parentController.refresh();
        }
    }

    private FamilyDto buildDto() {
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

    private boolean isInputValid(FamilyDto dto) {
        List<String> validation = FormValidator.validate(dto, getValidateableFields());

        if(validation.size() > 0) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save Family details", FormValidator.reduce(validation));
            return false;
        } else {
            return true;
        }

    }
}
