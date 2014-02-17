package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.FormActionListener;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.SystemEventListener;
import com.kesho.datamart.ui.util.TabButton;
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
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("StudentsController")
public class StudentsController implements Selectable<StudentDto> {
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
    private Button newButton;
    @FXML
    private Button deleteButton;
    @FXML
    private StudentController studentController;
    @FXML
    private EducationDetailsController educationDetailsController;

    private ObservableList<StudentDto> studentsModel = FXCollections.observableArrayList();
    private Map<String, FormActionListener> formActionListeners = new HashMap<>();

    @Override
    public StudentDto getSelectedItem() {
        return studentsTable.getSelectionModel().getSelectedItem();
    }

    @Override
    public void refresh() {
        if(studentsTable.getSortOrder().isEmpty()) {
            firstNameColumn.setVisible(false);
            firstNameColumn.setVisible(true);
        } else {
            ObservableList<TableColumn<StudentDto, ?>> sort = studentsTable.getSortOrder();
            ObservableList<TableColumn<StudentDto, ?>> sort1 = FXCollections.observableArrayList();
            sort1.addAll(sort);
            studentsTable.getSortOrder().removeAll(sort);
            studentsTable.getSortOrder().addAll(sort1);
        }
    }

    //TODO: what should happen after adding student? Should we go back to last page, or last selected page? what happen if students were deleted by someone else or if we had order
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
//        refreshTable();
        firstNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        studentsTable.getSelectionModel().select(0);
        studentsTable.focusModelProperty().get().focus(0, firstNameColumn);
        studentsTable.requestFocus();
        studentsTable.layout();

        initTabButtons();
        initFormActionListeners();
    }

    public void init() {
        refreshTable();
    }

    public void init(Long studentId) {
        initTable();
        StudentDto dto = studentsRepository.findOne(studentId);
        studentsModel.add(dto);
        studentsTable.getSelectionModel().select(dto);
    }

    private void initTable() {
        studentsModel.clear();

        studentsTable.setItems(studentsModel);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("firstName"));
        familyNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentDto, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<StudentDto, String> s) {
                SimpleStringProperty sp = new SimpleStringProperty(s.getValue().getFamily().getFamilyName());
                return sp;
            }
        });

        studentsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StudentDto>() {
            @Override
            public void changed(ObservableValue<? extends StudentDto> observableValue, StudentDto studentDto, StudentDto studentDto2) {
                WindowsUtil.getInstance().getEventBus().fireEvent(Event.STUDENT_SELECTED);
            }
        });
    }

    private void initPagination() {
        Page p = getPage(0, 10);
        if(p != null) {
            studentsModel.addAll(p.getContent());
            pagination.setPageCount(p.getTotalPages() > 0 ? p.getTotalPages() : 1);
            pagination.currentPageIndexProperty().setValue(0);
        }

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Page<StudentDto> p = getPage(newValue.intValue(), 10);
                pagination.setPageCount(p.getTotalPages());
                studentsModel.clear();
                studentsModel.addAll(p.getContent());
            }
        });
    }

    private Page<StudentDto> getPage(final int page, final int pageSize) {
        return studentsRepository.getPage(page, pageSize);
    }

    private void refreshTable() {
        initTable();
        initPagination();
    }

    public void disableButton(boolean disable, TabButton... buttons) {
        for (TabButton button:buttons) {
            switch(button) {
                case NEW:
                    newButton.disableProperty().set(disable);
                    break;
                case DELETE:
                    deleteButton.disableProperty().setValue(disable);
                    break;
            }
        }
    }

    private void initFormActionListeners() {
        formActionListeners.put("studentDetailsTab", studentController);
        formActionListeners.put("educationTab", educationDetailsController);
    }


    private void initTabButtons() {
        newButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FormActionListener elc = formActionListeners.get(studentTab.getSelectionModel().getSelectedItem().getId());
                if(elc != null) {
                    elc.newFired();
                }
            }
        });

        deleteButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FormActionListener elc = formActionListeners.get(studentTab.getSelectionModel().getSelectedItem().getId());
                if(elc != null) {
                    elc.deleteFired(studentsTable.getSelectionModel().getSelectedItem().getId());
                }
            }
        });
    }


}
