package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.LevelOfSupport;
import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.Util;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyDetailsController {
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

    private ObservableList<StudentDto> studentsModel = FXCollections.observableArrayList();

    @FXML
    private TableView<StudentDto> studentsTable;

    @FXML
    private TableColumn<StudentDto, String> studentNameCol;
    @FXML
    private TableColumn<StudentDto, LevelOfSupport> levelOfSupportCol;

    @Inject
    private StudentsController parentController;
    @Inject
    private FamilyRepository repository;

    public FamilyDetailsController() {
        WindowsUtil.getInstance().autowire(this);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        studentNameCol.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("firstName"));
        levelOfSupportCol.setCellValueFactory(new PropertyValueFactory<StudentDto, LevelOfSupport>("levelOfSupport"));

        studentsModel.clear();
        studentsTable.setItems(studentsModel);
    }

    void updateForm(StudentDto studentDto) {
        if (studentDto == null) {
            resetForm();
            return;
        }

        FamilyDto dto = studentDto.getFamily();
        loadStudents(dto.getId());

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

    private void loadStudents(final Long familyId) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                refreshStudentsTable(repository.getFamilyStudents(familyId));
            }
        });
    }

    private void refreshStudentsTable(List<StudentDto> students) {
        if (students == null) {
            return;
        }

        studentsModel.clear();
        studentsModel.addAll(students);
        studentsTable.setItems(null);
        studentsTable.layout();
        studentsTable.setItems(studentsModel);
    }

    private void resetForm() {
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
}
