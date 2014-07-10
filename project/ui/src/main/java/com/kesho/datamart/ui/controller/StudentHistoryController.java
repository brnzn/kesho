package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.EducationStatus;
import com.kesho.datamart.domain.SubEducationStatus;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.HistoryDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import java.time.Month;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentHistoryController extends AbstractEditableController<StudentDto> {
    @FXML
    private DatePicker date;
    @FXML
    private TextArea comments;

    private ObservableList<HistoryDto> tableModel = FXCollections.observableArrayList();

    @FXML
    private TableView<HistoryDto> historyTable;

    @FXML
    private TableColumn<HistoryDto, String> commentsCol;
    @FXML
    private TableColumn<HistoryDto, LocalDate> dateCol;

    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;

    @Inject
    private StudentsRepository studentsRepository;

    private Tab studentHistoryTab;


    private SimpleObjectProperty<HistoryDto> selectedHistory = new SimpleObjectProperty<>();

    private Map<String, Node> validationFields = new HashMap<>();

    public StudentHistoryController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @Override
    public void refresh(StudentDto dto) {
        refreshTable();
    }

    public Map<String, Node> getValidateableFields() {
        if(validationFields.isEmpty()) {
            validationFields.put("date", date);
            validationFields.put("comments", comments);
        }

        return validationFields;
    }

    private void refreshTable() {
        tableModel.clear();
        if(selected.get() != null) {
            List<HistoryDto> dtos = studentsRepository.getStudentHistory(selected.get().getId());
            tableModel.addAll(dtos);
            int selectedIndex = historyTable.getSelectionModel().getSelectedIndex();
            historyTable.setItems(null);
            historyTable.layout();
            historyTable.setItems(tableModel);
            historyTable.getSelectionModel().clearSelection();
            historyTable.getSelectionModel().select(selectedIndex >=0 ? selectedIndex : 0);
        }
    }


    @FXML
    private void save() {
        HistoryDto dto = getDto();

        if (isInputValid(dto)) {
            studentsRepository.save(dto);
            refreshTable();
        }
    }

    private boolean isInputValid(HistoryDto dto) {
        List<String> validation = FormValidator.validate(dto, getValidateableFields());

        if(validation.size() > 0) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save History Details", FormValidator.reduce(validation));
            return false;
        } else {
            return true;
        }
    }

    private HistoryDto getDto() {
        HistoryDto dto = selectedHistory.get();
        if (dto != null) {
            dto.setDate(Util.toJodaDate(date.getValue()));
            dto.setComments(comments.getText());
            return dto;
        }

        dto = new HistoryDto(selected.get().getId(), comments.getText(), Util.toJodaDate(date.getValue()));
        return dto;
    }
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        selectedHistory.addListener(new ChangeListener<HistoryDto>() {
            @Override
            public void changed(ObservableValue<? extends HistoryDto> observableValue, HistoryDto dto1, HistoryDto dto2) {
                saveButton.setDisable(dto2 == null);
                deleteButton.setDisable(dto2 == null);

                updateForm(dto2);
            }
        });

        dateCol.setCellValueFactory(new PropertyValueFactory<HistoryDto, LocalDate>("date"));
        commentsCol.setCellValueFactory(new PropertyValueFactory<HistoryDto, String>("comments"));

        historyTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HistoryDto>() {
            @Override
            public void changed(ObservableValue<? extends HistoryDto> observable,
                                HistoryDto oldValue, HistoryDto newValue) {
                selectedHistory.set(newValue);
            }
        });

        tableModel.clear();
        historyTable.setItems(tableModel);

    }

    private void updateForm(HistoryDto dto) {
        if(dto == null) {
            resetForm();
            return;
        }

        date.valueProperty().setValue(Util.toJavaDate(dto.getDate()));
        comments.setText(dto.getComments());
    }


    private void resetForm() {
        date.setValue(null);
        comments.clear();
    }

    @FXML
    private void newFired() {
        selectedHistory.set(null);
        saveButton.setDisable(false);
        resetForm();
    }

    @FXML
    private void deleteFired() {
        if(WindowsUtil.getInstance().showWarningDialog("Delete History Details", "Are you sure you want to delete the selected history row?", null)) {
            studentsRepository.deleteStudentHistory(selectedHistory.get().getId());
            refreshTable();
        }
    }
}
