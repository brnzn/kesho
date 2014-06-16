package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.LevelOfSupport;
import com.kesho.datamart.domain.Location;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.util.Util;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyDetailsController extends AbstractChildController<StudentDto> {
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

    private SimpleObjectProperty<StudentDto> selected = new SimpleObjectProperty<>();


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

    private Tab familyTab;

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

    @Override
    public void refresh(StudentDto dto) {
        if (dto == null) {
            resetForm();
            return;
        }

        FamilyDto familyDto = dto.getFamily();
        loadStudents(familyDto.getId());

        familyName.setText(familyDto.getFamilyName());
        homeLocation.setValue(familyDto.getHomeLocation());
        homeSubLocation.setText(familyDto.getHomeSubLocation());
        homeClusterId.setText(familyDto.getHomeClusterId());
        aliveParents.setText(Util.safeToStringValue(familyDto.getAliveParents(), null));
        Util.setYesNoToggleState(isMarried, familyDto.getMarried());
        numNonKeshoStudents.setText(Util.safeToStringValue(familyDto.getNumNonKeshoStudents(), null));
        numOfWives.setText(Util.safeToStringValue(familyDto.getNumOfWives(), null));
        primaryCaretaker.setText(familyDto.getPrimaryCaretaker());
        mainContactName.setText(familyDto.getMainContactName());
        mobileNumber.setText(familyDto.getMobileNumber());
        Util.setYesNoToggleState(isPhoneOwner, familyDto.getMarried());
        phoneOwnerName.setText(familyDto.getPhoneOwnerName());
        profile.setText(familyDto.getProfile());
        numOfAdultsAtAddress.setText(Util.safeToStringValue(familyDto.getNumOfAdultsAtAddress(), null));
    }

    @Override
    public Map<String, Node> getValidateableFields() {
        return null;
    }

//    @Override
//    public void setSelectedProperty(SimpleObjectProperty<StudentDto> selectedProperty) {
//        this.selected = selectedProperty;
//
//        selected.addListener(new ChangeListener<StudentDto>() {
//            @Override
//            public void changed(ObservableValue<? extends StudentDto> observableValue, StudentDto dto1, StudentDto dto2) {
//                familyTab.disableProperty().set(dto2 == null);
//
//                if (familyTab.isSelected()) {
//                    updateForm(dto2);
//                }
//            }
//        });
//    }
//
//    @Override
//    public void setTab(Tab familyTab) {
//        this.familyTab = familyTab;
//        familyTab.disableProperty().set(true);
//
//        familyTab.setOnSelectionChanged(new EventHandler<Event>() {
//            @Override
//            public void handle(javafx.event.Event event) {
//                if (familyTab.isSelected()) {
//                    updateForm(selected.get());
//                }
//            }
//        });
//    }

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
