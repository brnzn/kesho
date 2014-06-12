package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.FoundUs;
import com.kesho.datamart.domain.LevelOfParticipation;
import com.kesho.datamart.domain.PayeeType;
import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.SponsorsRepository;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SponsorController {
    @Autowired
    private SponsorsRepository sponsorsRepository;

    @FXML
    private TextField firstName;
    @FXML
    private TextField surname;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
//    @FXML
//    private HBox startDateBox;
//    private final FXCalendar startDateCalendar = new FXCalendar();
//    @FXML
//    private HBox endDateBox;
//    private final FXCalendar endDateCalendar = new FXCalendar();
    @FXML
    private ToggleGroup active;
    @FXML
    private ToggleGroup anonymous;
    @FXML
    private TextField addressLine1;
    @FXML
    private TextField addressLine2;
    @FXML
    private TextField postcode;
    @FXML
    private TextField county;
    @FXML
    private TextField country;
    @FXML
    private TextField email1;
    @FXML
    private TextField email2;
    @FXML
    private TextField phone;
    @FXML
    private ComboBox<PayeeType> payeeType;
    @FXML
    private ComboBox<FoundUs> howFoundUs;
    @FXML
    private ComboBox<LevelOfParticipation> participationLevel;
//    @FXML
//    private Tab sponsorDetailsTab;
    @FXML
    private Button saveButton;

    private Map<String, Node> validationFields = new HashMap<>();
    private SimpleObjectProperty<SponsorDto> selected = new SimpleObjectProperty<>();

    @Inject
    private SponsorsController parentController;

    public SponsorController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @FXML
    private void initialize() {
        selected.bind(parentController.getSelectedProperty());

        selected.addListener(new ChangeListener<SponsorDto>() {
            @Override
            public void changed(ObservableValue<? extends SponsorDto> observableValue, SponsorDto dto, SponsorDto dto1) {
                initializeForm();
                saveButton.setDisable(dto1 == null);
            }
        });

        surname.setUserData(null);

        Util.initializeYesNoGroup(active, anonymous);
        Util.initializeComboBoxValues(participationLevel, EnumSet.allOf(LevelOfParticipation.class));
        Util.initializeComboBoxValues(howFoundUs, EnumSet.allOf(FoundUs.class));
        Util.initializeComboBoxValues(payeeType, EnumSet.allOf(PayeeType.class));
    }

    @FXML
    private void save() {
        SponsorDto dto = buildDto();

        String validation = FormValidator.validate(dto, getFields());

        if(StringUtils.isNotBlank(validation)) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save Sponsor details", validation);
        } else {
            dto = sponsorsRepository.save(dto);
            selected.bind(parentController.getSelectedProperty());
            parentController.valueChanged();
        }
    }

    private SponsorDto buildDto() {
        SponsorDto current = selected.get();
        current.setName(firstName.getText());
        current.setSurname(surname.getText());
        current.setStartDate(Util.toJodaDate(startDate.getValue()));
        current.setActive((Boolean) active.getSelectedToggle().getUserData());
        current.setAddressLine1(addressLine1.getText());
        current.setAddressLine2(addressLine2.getText());
        current.setAnonymous((Boolean) anonymous.getSelectedToggle().getUserData());
        current.setCountry(country.getText());
        current.setCounty(county.getText());

        current.setEndDate(Util.toJodaDate(endDate.getValue()));

        current.setPostcode(postcode.getText());
        current.setPayeeType(payeeType.getValue());
        current.setPhone(phone.getText());
        current.setLevelOfParticipation(participationLevel.getValue());
        current.setHowFoundUs(howFoundUs.getValue());
        current.setEmail1(email1.getText());
        current.setEmail2(email2.getText());

        return current;
    }

    private void initializeForm() {
        SponsorDto dto = selected.get();

        if(dto == null) {
            resetForm();
            return;
        }

        firstName.setText(dto.getName());
        surname.setText(dto.getSurname());
        Util.setYesNoToggleState(anonymous, dto.getActive());

        startDate.valueProperty().set(Util.toJavaDate(dto.getStartDate()));

        Util.setYesNoToggleState(active, dto.getActive());
        addressLine1.setText(dto.getAddressLine1());
        addressLine2.setText(dto.getAddressLine2());
        country.setText(dto.getCountry());
        county.setText(dto.getCounty());

        endDate.valueProperty().set(Util.toJavaDate(dto.getEndDate()));

        postcode.setText(dto.getPostcode());
        payeeType.setValue(dto.getPayeeType());
        phone.setText(dto.getPhone());
        participationLevel.setValue(dto.getLevelOfParticipation());
        howFoundUs.setValue(dto.getHowFoundUs());
        email1.setText(dto.getEmail1());
        email2.setText(dto.getEmail2());
    }

    private void resetForm() {
        firstName.clear();
        surname.clear();
        anonymous.getToggles().get(0).setSelected(true);
        startDate.valueProperty().setValue(null);
        active.getToggles().get(0).setSelected(true);
        addressLine1.clear();
        addressLine2.clear();
        country.clear();
        county.clear();
        endDate.valueProperty().setValue(null);
        postcode.clear();
        payeeType.setValue(null);
        phone.clear();
        participationLevel.setValue(null);
        howFoundUs.setValue(null);
        email1.clear();
        email2.clear();
    }

    void newFired() {
        resetForm();
        //Must unbind in order to set new value
        selected.unbind();
        selected.set(new SponsorDto());
    }

    void deleteFired(Long id) {
        resetForm();
    }

    private Map<String, Node> getFields() {
        if(validationFields.isEmpty()) {
            validationFields.put("name", firstName);
            validationFields.put("surname", surname);
        }

        return validationFields;
    }

}
