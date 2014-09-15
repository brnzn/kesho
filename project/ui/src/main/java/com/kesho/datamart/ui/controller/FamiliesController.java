package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.SystemEventListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("FamiliesController")
public class FamiliesController implements Selectable<StudentDto> {
    @Inject
    private FamilyRepository repository;

    @FXML
    private TableView<FamilyDto> familiesTable;
    @FXML
    private TableColumn<FamilyDto, String> familyNameColumn;
    @FXML
    private Pagination pagination;
    @FXML
    private FamilyDetailsController familyDetailsController;
    @FXML
    private FamilyProfileController familyProfileController;

    @FXML
    private TabPane familyTabs;
    @FXML
    private Tab familyProfileTab;
    @FXML
    private Tab familyDetailsTab;

//    private Stage dialogStage;

    private ObservableList<FamilyDto> familiesModel = FXCollections.observableArrayList();
    private SimpleObjectProperty<FamilyDto> selected = new SimpleObjectProperty<>();


    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
//    public void setDialogStage(Stage dialogStage) {
//        this.dialogStage = dialogStage;
//    }


    @Override
    public void refresh() {
        if(familiesTable.getSortOrder().isEmpty()) {
            familyNameColumn.setVisible(false);
            familyNameColumn.setVisible(true);
        } else {
            ObservableList<TableColumn<FamilyDto, ?>> sort = familiesTable.getSortOrder();
            ObservableList<TableColumn<FamilyDto, ?>> sort1 = FXCollections.observableArrayList();
            sort1.addAll(sort);
            familiesTable.getSortOrder().removeAll(sort);
            familiesTable.getSortOrder().addAll(sort1);
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        refreshTable();
//        familiesTable.getSelectionModel().select(0);
//        familiesTable.focusModelProperty().get().focus(0, familyNameColumn);
//        familiesTable.requestFocus();
//        familiesTable.layout();

//        WindowsUtil.getInstance().getEventBus().registerListener(Event.STUDENT_ADDED, new SystemEventListener() {
//            @Override
//            public void handle() {
//                refreshTable();
//            }
//        });

        familyDetailsController.setTab(familyDetailsTab);
        familyDetailsController.setSelectedProperty(selected);

        familyProfileController.setTab(familyProfileTab);
        familyProfileController.setSelectedProperty(selected);

        refreshTable();

    }

    private void initTable() {
        familiesModel.clear();

        familiesTable.setItems(familiesModel);
        familyNameColumn.setCellValueFactory(new PropertyValueFactory<FamilyDto, String>("familyName"));

        familiesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FamilyDto>() {
            @Override
            public void changed(ObservableValue<? extends FamilyDto> observableValue, FamilyDto dto, FamilyDto dto2) {
                selected.set(dto2);
            }
        });
    }

    private void initPagination() {
        Page p = getPage(0, 20); //TODO: page size should come from properties file
        if(p != null) {
            familiesModel.addAll(p.getContent());
            pagination.setPageCount(p.getTotalPages() > 0 ? p.getTotalPages() : 1);
            pagination.currentPageIndexProperty().setValue(0);
        }

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Page<FamilyDto> p = getPage(newValue.intValue(), 20);   //TODO: page size should come from properties file
                pagination.setPageCount(p.getTotalPages());
                familiesModel.clear();
                familiesModel.addAll(p.getContent());
            }
        });
    }

    private Page<FamilyDto> getPage(final int page, final int pageSize) {
        return repository.getPage(page, pageSize);
    }

    @FXML
    private void refreshTable() {
        initTable();
        initPagination();
    }

//    @FXML
//    private void delete() {
//        repository.delete(selected.get().getId());
//        refreshTable();
//    }

    @FXML
    private void doNew() {
        familyDetailsController.newFired();
        familyTabs.getSelectionModel().select(familyDetailsTab);
    }
}
