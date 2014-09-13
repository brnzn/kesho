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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentFamilyDetailsController extends AbstractFamilyDetailsController<StudentDto> {
    private ObservableList<StudentDto> studentsModel = FXCollections.observableArrayList();

    @FXML
    private TableView<StudentDto> studentsTable;

    @FXML
    private TableColumn<StudentDto, String> studentNameCol;
    @FXML
    private TableColumn<StudentDto, LevelOfSupport> levelOfSupportCol;

    public StudentFamilyDetailsController() {
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

        studentNameCol.setCellFactory(new Callback<TableColumn<StudentDto, String>, TableCell<StudentDto, String>>() {
            @Override
            public TableCell<StudentDto, String> call(TableColumn<StudentDto, String> studentNamne) {
                TableCell<StudentDto, String> cell = new TableCell<StudentDto, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {

                        if(item!=null){
                            //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                            Hyperlink link = new Hyperlink(item);
                            link.setUserData(((StudentDto)getTableRow().getItem()).getId());
                            //link.setUserData();
                            link.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    tab.getTabPane().getSelectionModel().selectFirst();
                                    WindowsUtil.getInstance().students((Long) ((Hyperlink) e.getSource()).getUserData());
                                }
                            });
                            setGraphic(link);
                        }
                    }
                };

                return cell;
            }
        });

    }

    @Override
    public void refresh(StudentDto dto) {
        System.out.println("!!!!!!!!!!!!!!!!!!!");
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
        numOfChildrenAtAddress.clear();
        numOfWives.clear();
        primaryCaretaker.clear();
        mainContactName.clear();
        mobileNumber.clear();
        phoneOwnerName.clear();
        profile.clear();
        numOfAdultsAtAddress.clear();
    }
}
