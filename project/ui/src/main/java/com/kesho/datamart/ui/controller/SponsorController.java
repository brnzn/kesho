package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.FormActionListener;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
public class SponsorController implements FormActionListener {
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
    @FXML
    private Button saveButton;


    private SimpleObjectProperty<SponsorDto> selected = new SimpleObjectProperty<>();

    @Inject
    private SponsorsController parentController;

    public SponsorController() {
        startDateCalendar.setDateTextWidth(Double.valueOf(200));
        endDateCalendar.setDateTextWidth(Double.valueOf(200));
        WindowsUtil.getInstance().autowire(this);
    }

    @FXML
    private void initialize() {
        selected.addListener(new ChangeListener<SponsorDto>() {
            @Override
            public void changed(ObservableValue<? extends SponsorDto> observableValue, SponsorDto dto, SponsorDto dto1) {
                saveButton.setDisable(dto1 == null);
            }
        });

        selected.set(null);
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

//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                WindowsUtil.getInstance().getControllers().sponsorsController().registerNewChangeListener("sponsorDetailsTab", new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent e) {
//                        add();
//                    }
//                });
//            }
//        });

    }

    @FXML
    private void save() {
        SponsorDto dto = buildDto();
        dto = sponsorsRepository.save(dto);
        WindowsUtil.getInstance().getEventBus().fireEvent(Event.SPONSOR_ADDED);
    }

    private SponsorDto buildDto() {
        SponsorDto current = selected.get();
        current.setName(firstName.getText());
        current.setSurname(surname.getText());
        if(startDateCalendar.getValue() != null) {
            current.setStartDate(LocalDate.fromDateFields(startDateCalendar.getValue()));
        }

        current.setActive((Boolean) active.getSelectedToggle().getUserData());
        current.setAddressLine1(addressLine1.getText());
        current.setAddressLine2(addressLine2.getText());
        current.setAnonymous((Boolean) anonymous.getSelectedToggle().getUserData());
        current.setCountry(country.getText());
        current.setCounty(county.getText());

        if(endDateCalendar.getValue() != null) {
            current.setEndDate(LocalDate.fromDateFields(endDateCalendar.getValue()));
        }

        current.setPostcode(postcode.getText());
        current.setPayeeType(payeeType.getValue());
        current.setPhone(phone.getText());
        current.setLevelOfParticipation(participationLevel.getValue());
        current.setHowFoundUs(howFoundUs.getValue());
        current.setEmail1(email1.getText());
        current.setEmail2(email2.getText());

        return current;
    }

    private void initializeForm(SponsorDto dto) {
        selected.set(dto);

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
        anonymous.getToggles().get(0).setSelected(true);
        startDateCalendar.clear();
        active.getToggles().get(0).setSelected(true);
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

    @Override
    public void newFired() {
        sponsorDetailsTab.getTabPane().getSelectionModel().select(sponsorDetailsTab);
        resetForm();
        selected.set(new SponsorDto());
    }

    @Override
    public void deleteFired(Long id) {
        resetForm();
    }
}
