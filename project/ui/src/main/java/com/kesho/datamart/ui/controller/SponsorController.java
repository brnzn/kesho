package com.kesho.datamart.ui.controller;

import com.kesho.ui.control.calendar.FXCalendar;
import com.kesho.datamart.domain.FoundUs;
import com.kesho.datamart.domain.LevelOfParticipation;
import com.kesho.datamart.domain.PayeeType;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.SponsorsRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.SystemEventListener;
import com.kesho.datamart.ui.util.Util;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.EnumSet;

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
    private HBox startDateBox;
    private final FXCalendar startDateCalendar = new FXCalendar();
    @FXML
    private HBox endDateBox;
    private final FXCalendar endDateCalendar = new FXCalendar();
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
    @FXML
    private Tab sponsorDetailsTab;


    private SponsorDto selected;

    @Inject
    private SponsorsController parentController;

    public SponsorController() {
        startDateCalendar.setDateTextWidth(Double.valueOf(200));
        endDateCalendar.setDateTextWidth(Double.valueOf(200));
        WindowsUtil.getInstance().autowire(this);
    }

    @FXML
    private void initialize() {
        selected = null;
        surname.setUserData(null);

        startDateBox.getChildren().add(startDateCalendar);
        endDateBox.getChildren().add(endDateCalendar);

        Util.initializeYesNoGroup(active, anonymous);

        Util.initializeComboBoxValues(participationLevel, EnumSet.allOf(LevelOfParticipation.class));
        Util.initializeComboBoxValues(howFoundUs, EnumSet.allOf(FoundUs.class));
        Util.initializeComboBoxValues(payeeType, EnumSet.allOf(PayeeType.class));

        WindowsUtil.getInstance().getEventBus().registerListener(Event.SPONSOR_SELECTED, new SystemEventListener() {
            @Override
            public void handle() {
                if (sponsorDetailsTab.isSelected()) {
                    initializeForm(parentController.getSelectedItem());
                }
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WindowsUtil.getInstance().getControllers().sponsorsController().registerNewChangeListener("sponsorDetailsTab", new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        add();
                    }
                });
            }
        });

    }

    private void add() {
        SponsorDto dto = new SponsorDto();
        initializeForm(dto);
    }

    @FXML
    private void save() {
        SponsorDto dto = buildDto();
        dto = sponsorsRepository.save(dto);
        WindowsUtil.getInstance().getEventBus().fireEvent(Event.SPONSOR_ADDED);
    }

    private SponsorDto buildDto() {
        selected.setName(firstName.getText());
        selected.setSurname(surname.getText());
        selected.setStartDate(LocalDate.fromDateFields(startDateCalendar.getValue()));
        selected.setActive((Boolean) active.getSelectedToggle().getUserData());
        selected.setAddressLine1(addressLine1.getText());
        selected.setAddressLine2(addressLine2.getText());
        selected.setAnonymous((Boolean) anonymous.getSelectedToggle().getUserData());
        selected.setCountry(country.getText());
        selected.setCounty(county.getText());
        if(endDateCalendar.getValue() != null) {
            selected.setEndDate(LocalDate.fromDateFields(endDateCalendar.getValue()));
        }

        selected.setPostcode(postcode.getText());
        selected.setPayeeType(payeeType.getValue());
        selected.setPhone(phone.getText());
        selected.setLevelOfParticipation(participationLevel.getValue());
        selected.setHowFoundUs(howFoundUs.getValue());
        selected.setEmail1(email1.getText());
        selected.setEmail2(email2.getText());

        return selected;
    }

    private void initializeForm(SponsorDto dto) {
        selected = dto;

        if(dto == null) {
            resetForm();
            return;
        }

        firstName.setText(dto.getName());
        surname.setText(dto.getSurname());
        setState(anonymous, dto.getActive());

        if(dto.getStartDate() != null) {
            startDateCalendar.setValue(dto.getStartDate().toDate());
        } else {
            startDateCalendar.clear();
        }

        setState(active, dto.getActive());
        addressLine1.setText(dto.getAddressLine1());
        addressLine2.setText(dto.getAddressLine2());
        country.setText(dto.getCountry());
        county.setText(dto.getCounty());

        if(dto.getEndDate() != null) {
            endDateCalendar.setValue(dto.getEndDate().toDate());
        } else {
            endDateCalendar.clear();
        }

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
        anonymous.selectToggle(null);
        startDateCalendar.clear();
        active.selectToggle(null);
        addressLine1.clear();
        addressLine2.clear();
        country.clear();
        county.clear();
        endDateCalendar.clear();

        postcode.clear();
        payeeType.setValue(null);
        phone.clear();
        participationLevel.setValue(null);
        howFoundUs.setValue(null);
        email1.clear();
        email2.clear();
    }

    private void setState(ToggleGroup group, Boolean value) {
        if (value == null || value) {
            group.getToggles().get(0).setSelected(true);
        } else {
            group.getToggles().get(1).setSelected(true);
        }
    }

}
