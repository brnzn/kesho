package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.FormActionListener;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.repository.SchoolRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchoolController extends AbstractEditableController<SchoolDto> implements FormActionListener {
    @Autowired
    private SchoolRepository schoolsRepository;

    @FXML
    private TextField name;
    @FXML
    private TextField addressLine1;
    @FXML
    private TextField addressLine2;
    @FXML
    private TextField addressLine3;
    @FXML
    private TextField postcode;
    @FXML
    private TextField county;
    @FXML
    private TextField country;
    @FXML
    private Button saveButton;

    @Inject
    private SchoolsController parentController;

    private Map<String, Node> validationFields = new HashMap<>();

    public SchoolController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @Override
    public void refresh() {
        saveButton.setDisable(selected.get() == null);
        itemSelected(selected.get());
    }


//    @FXML
//    private void initialize() {
//    }

    @Override
    public void newFired() {
        selected.unbind();
        selected.set(new SchoolDto(null));
        //resetForm();
    }

    private  void itemSelected(SchoolDto school) {
        if(school == null) {
            resetForm();
            return;
        }

        name.setText(school.getName());
        addressLine1.setText(school.getAddressLine1());
        addressLine2.setText(school.getAddressLine2());
        addressLine3.setText(school.getAddressLine3());
        postcode.setText(school.getPostcode());
        county.setText(school.getCounty());
        country.setText(school.getCountry());
    }

    @Override
    public void deleteFired(Long id) {
        //Student cannot be deleted
    }

    protected void doSave() {
        SchoolDto dto = buildDto();
        if (isInputValid(dto)) {
            boolean isNew = dto.getId() == null;

            dto = schoolsRepository.save(dto);

            if(isNew) { // fire event so childrenTable can be reloaded
                WindowsUtil.getInstance().getEventBus().fireEvent(Event.SCHOOL_ADDED);
            }

            //parentController.refresh();
        }
    }

    private SchoolDto buildDto() {
        SchoolDto dto = selected.get();
        dto.withName(name.getText())
        .withAddressLine1(addressLine1.getText())
        .withAddressLine2(addressLine2.getText())
        .withAddressLine3(addressLine3.getText())
        .withCounty(country.getText())
        .withCounty(county.getText())
        .withPostcode(postcode.getText());

        return dto;
    }

    private void resetForm() {
        name.clear();
        addressLine1.clear();
        addressLine2.clear();
        addressLine3.clear();
        postcode.clear();
        county.clear();
        country.clear();
    }

    private boolean isInputValid(SchoolDto dto) {
        List<String> validation = FormValidator.validate(dto, getValidateableFields());

        if(validation.size() > 0) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save School", FormValidator.reduce(validation));
            return false;
        } else {
            return true;
        }

    }

    protected Map<String, Node> getValidateableFields() {

        if(validationFields.isEmpty()) {
            validationFields.put("name", name);
        }

        return validationFields;
    }
}
