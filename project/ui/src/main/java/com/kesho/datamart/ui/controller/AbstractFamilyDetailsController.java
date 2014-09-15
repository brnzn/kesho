package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.Dto;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.Util;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFamilyDetailsController<T extends Dto> extends AbstractEditableController<T> {
    @FXML
    protected TextField familyName;
    @FXML
    protected ComboBox<Location> homeLocation;
    @FXML
    protected TextField homeSubLocation;
    @FXML
    protected TextField homeClusterId;
    @FXML
    protected TextField aliveParents;
    @FXML
    protected ToggleGroup isMarried;
    @FXML
    protected TextField numOfChildrenAtAddress;
    @FXML
    protected TextField numOfWives;
    @FXML
    protected TextField primaryCaretaker;
    @FXML
    protected TextField mainContactName;
    @FXML
    protected TextField mobileNumber;
    @FXML
    protected ToggleGroup isPhoneOwner;
    @FXML
    protected TextField phoneOwnerName;
    @FXML
    protected TextArea profile;
    @FXML
    protected TextField numOfAdultsAtAddress;

    @Inject
    protected StudentsController parentController;
    @Inject
    protected FamilyRepository repository;

    protected Tab familyTab;

    public abstract void refresh(T dto);

    protected Map<String, Node> getValidateableFields() {
        return Collections.emptyMap();
    };

    protected  void doSave(){};


}
