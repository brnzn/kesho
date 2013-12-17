package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.StudentsRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("StudentDetails")
public class DetailsController implements Selectable<StudentDto> {
    @Autowired
    private StudentsRepository studentsRepository;
    @FXML
    private TableView<StudentDto> studentsTable;
    @FXML
    private TableColumn<StudentDto, String> firstNameColumn;
    @FXML
    private TableColumn<StudentDto, String> familyNameColumn;
    @FXML
    private Pagination pagination;
    @FXML
    private TabPane studentTab;
    @FXML
    private Button bb;

    private ObservableList<StudentDto> studentsModel = FXCollections.observableArrayList();
    private Map<String, EventHandler<ActionEvent>> newButtonHandlers = new HashMap<>();

    @Override
    public StudentDto getSelectedItem() {
        return studentsTable.getSelectionModel().getSelectedItem();
    }

    public void registerChangeListener(ChangeListener<StudentDto> changeListener) {
        studentsTable.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    public void registerTabChangeListener(ChangeListener<Tab> listener) {
        studentTab.getSelectionModel().selectedItemProperty().addListener(listener);
    }

    public void registerNewChangeListener(String id, EventHandler<ActionEvent> eventHandler) {
        newButtonHandlers.put(id, eventHandler);
    }

    //TODO: what should happen after adding student? Should we go back to last page, or last selected page? what happen if students were deleted by someone else or if we had order
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        initTable();
        initPagination();
        firstNameColumn.setSortType(TableColumn.SortType.DESCENDING);

        studentsTable.getSelectionModel().select(0);
        studentsTable.focusModelProperty().get().focus(0, firstNameColumn);
        studentsTable.requestFocus();


        studentsTable.layout();
        bb.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newButtonHandlers.get(studentTab.getSelectionModel().getSelectedItem().getId()).handle(actionEvent);
            }
        });

        studentTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab tab2) {
                if (newButtonHandlers.get(tab2.getId()) == null) {
                    bb.disableProperty().setValue(true);
                } else {
                    bb.disableProperty().setValue(false);
                }
            }
        });
    }



    private void initTable() {
        studentsModel.clear();

        studentsTable.setItems(studentsModel);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("name"));
        familyNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("familyName"));
    }

    private void initPagination() {
        Page p = getPage(0, 2);
        if(p != null) {
            studentsModel.addAll(p.getContent());    //
            pagination.setPageCount(p.getTotalPages() > 0 ? p.getTotalPages() : 1);
        }

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Page<StudentDto> p = getPage(newValue.intValue(), 2);
                pagination.setPageCount(p.getTotalPages());
                studentsModel.clear();
                studentsModel.addAll(p.getContent());
            }
        });
    }

    private Page<StudentDto> getPage(final int page, final int pageSize) {
        return studentsRepository.getPage(page, pageSize);
    }

    public void refresh() {
        initTable();
        initPagination();
    }
}
