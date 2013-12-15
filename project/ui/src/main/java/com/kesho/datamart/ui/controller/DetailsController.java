package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.repository.StudentsRepository;
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

    private ObservableList<StudentDto> studentsModel = FXCollections.observableArrayList();

    @Override
    public StudentDto getSelectedItem() {
        return studentsTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void print() {
        System.out.println(studentTab.getSelectionModel().getSelectedItem().getId());
    }

    private Page<StudentDto> getPage(final int page, final int pageSize) {
        return studentsRepository.getPage(page, pageSize);
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
    }

//    @FXML
//    private void save() {
//        studentsRepository.save(buildDto());
//    }

    private void initTable() {
        studentsModel.clear();

        studentsTable.setItems(studentsModel);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("name"));
        familyNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("familyName"));

//		//update form when selecting table row
//        studentsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StudentDto>() {
//            @Override
//            public void changed(ObservableValue<? extends StudentDto> observable,
//                                StudentDto oldValue, StudentDto newValue) {
//                showStudentDetails(newValue);
//            }
//        });

//        studentsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//                    StudentDto selected = studentsTable.getSelectionModel().getSelectedItem();
//                    if (mouseEvent.getClickCount() == 2 && selected != null) {
//                        WindowsUtil.getInstance().showNewStudentDetails(selected);
//                    }
//                }
//            }
//        });

        //select row
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                studentsTable.requestFocus();
//                studentsTable.getSelectionModel().select(1);
//                studentsTable.getFocusModel().focus(0);
//            }
//        });
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

    public void registerChangeListener(ChangeListener<StudentDto> changeListener) {
        studentsTable.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    public void registerTabChangeListener(ChangeListener<Tab> listener) {
        studentTab.getSelectionModel().selectedItemProperty().addListener(listener);
    }
}
