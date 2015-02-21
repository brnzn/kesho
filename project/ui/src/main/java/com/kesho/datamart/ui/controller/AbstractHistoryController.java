package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.Dto;
import com.kesho.datamart.dto.HistoryDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.joda.time.LocalDate;

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
public abstract class AbstractHistoryController<T extends Dto> extends AbstractEditableController<T> {
    @FXML
    protected DatePicker date;
    @FXML
    protected TextArea comments;

    @FXML
    protected TableView<HistoryDto> historyTable;

    @FXML
    protected TableColumn<HistoryDto, String> commentsCol;
    @FXML
    protected TableColumn<HistoryDto, LocalDate> dateCol;

    @FXML
    protected Button saveButton;
    @FXML
    protected Button deleteButton;

    private ObservableList<HistoryDto> tableModel = FXCollections.observableArrayList();
    private SimpleObjectProperty<HistoryDto> selectedHistory = new SimpleObjectProperty<>();
    private Map<String, Node> validationFields = new HashMap<>();

  @Override
    public void refresh() {
        refreshTable();
    }

    @Override
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
            tableModel.addAll(getData(selected.get().getId()));
            int selectedIndex = historyTable.getSelectionModel().getSelectedIndex();
            historyTable.setItems(null);
            historyTable.layout();
            historyTable.setItems(tableModel);
            historyTable.getSelectionModel().clearSelection();
            historyTable.getSelectionModel().select(selectedIndex >=0 ? selectedIndex : 0);
        }
    }

    protected abstract List<HistoryDto> getData(Long ownerId);

    protected void doSave() {
        HistoryDto dto = getDto();

        if (isInputValid(dto)) {
            saveDto(dto);
            refreshTable();
        }
    }

    protected abstract HistoryDto saveDto(HistoryDto dto);

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
    protected void initialize() {
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
    protected void newFired() {
        selectedHistory.set(null);
        saveButton.setDisable(false);
        resetForm();
    }

    @FXML
    protected void deleteFired() {
        if(WindowsUtil.getInstance().showWarningDialog("Delete History Details", "Are you sure you want to delete the selected history row?", null)) {
            deleteDto(selectedHistory.get().getId());
            refreshTable();
        }
    }

    protected abstract void deleteDto(Long id);
}
