package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.SchoolRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.SystemEventListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/19/14
 * Time: 11:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("SchoolsController")
public class SchoolsController {
    @Autowired
    private SchoolRepository schoolRepository;
    @FXML
    private TableView<SchoolDto> schoolsTable;
    @FXML
    private TableColumn<SchoolDto, String> nameCol;
    @FXML
    private TableColumn<SchoolDto, String> countyCol;
    @FXML
    private Pagination pagination;
    @FXML
    private TabPane schoolsTabs;
    @FXML
    private SchoolContactsController contactsController;
    @FXML
    private Tab contactsTab;

    @FXML
    private SchoolController schoolController;
    @FXML
    private Tab schoolDetailsTab;


    private ObservableList<SchoolDto> schoolsModel = FXCollections.observableArrayList();
    private SimpleObjectProperty<SchoolDto> selected = new SimpleObjectProperty<>();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        WindowsUtil.getInstance().getEventBus().registerListener(Event.SCHOOL_ADDED, new SystemEventListener() {
            @Override
            public void handle() {
                refreshTable();
            }
        });

        contactsController.setTab(contactsTab);
        contactsController.setSelectedProperty(selected);

        schoolController.setTab(schoolDetailsTab);
        schoolController.setSelectedProperty(selected);

        refreshTable();

        nameCol.setSortType(TableColumn.SortType.DESCENDING);
        schoolsTable.getSelectionModel().select(0);
        schoolsTable.focusModelProperty().get().focus(0, nameCol);
        schoolsTable.requestFocus();
        schoolsTable.layout();

    }

    private void initTable() {
        schoolsModel.clear();

        schoolsTable.setItems(schoolsModel);

        nameCol.setCellValueFactory(new PropertyValueFactory<SchoolDto, String>("name"));
        countyCol.setCellValueFactory(new PropertyValueFactory<SchoolDto, String>("county"));

        schoolsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SchoolDto>() {
            @Override
            public void changed(ObservableValue<? extends SchoolDto> observableValue, SchoolDto studentDto, SchoolDto studentDto2) {
                selected.set(studentDto2);
            }
        });
    }

    private void initPagination() {
        Page p = getPage(0, 20); //TODO: page size should come from properties file
        if(p != null) {
            schoolsModel.addAll(p.getContent());
            pagination.setPageCount(p.getTotalPages() > 0 ? p.getTotalPages() : 1);
            pagination.currentPageIndexProperty().setValue(0);
        }

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Page<SchoolDto> p = getPage(newValue.intValue(), 20);   //TODO: page size should come from properties file
                pagination.setPageCount(p.getTotalPages());
                schoolsModel.clear();
                schoolsModel.addAll(p.getContent());
            }
        });
    }

    private Page<SchoolDto> getPage(final int page, final int pageSize) {
        return schoolRepository.getPage(page, pageSize);
    }

    @FXML
    private void refreshTable() {
        initTable();
        initPagination();
    }

    //TODO: test sorting
//    public void refresh() {
//        if(schoolsTable.getSortOrder().isEmpty()) {
//            nameCol.setVisible(false);
//            nameCol.setVisible(true);
//        } else {
//            ObservableList<TableColumn<StudentDto, ?>> sort = schoolsTable.getSortOrder();
//            ObservableList<TableColumn<StudentDto, ?>> sort1 = FXCollections.observableArrayList();
//            sort1.addAll(sort);
//            schoolsTable.getSortOrder().removeAll(sort);
//            schoolsTable.getSortOrder().addAll(sort1);
//        }
//    }

    @FXML
    private void doNew() {
        schoolController.newFired();
        schoolsTabs.getSelectionModel().select(schoolDetailsTab);
    }

}
