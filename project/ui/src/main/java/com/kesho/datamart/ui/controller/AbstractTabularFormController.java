package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.Dto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTabularFormController<H extends Dto, T extends Dto> extends AbstractEditableController<T> {
    @FXML
    protected TableView<H> childrenTable;

    @FXML
    protected Button saveButton;
    @FXML
    protected Button deleteButton;

    private ObservableList<H> tableModel = FXCollections.observableArrayList();
    protected SimpleObjectProperty<H> selectedChild = new SimpleObjectProperty<>();

    protected abstract void updateForm(H dto);
    protected abstract void deleteDto(Long id);
    protected abstract List<H> getData(Long ownerId);
    protected abstract H saveDto(H dto);
    protected abstract H getDto();

    @Override
    public void refresh() {
        refreshTable();
    }

    private void refreshTable() {
        tableModel.clear();
        if(selected.get() != null) {
            tableModel.addAll(getData(selected.get().getId()));
            int selectedIndex = childrenTable.getSelectionModel().getSelectedIndex();
            childrenTable.setItems(null);
            childrenTable.layout();
            childrenTable.setItems(tableModel);
            childrenTable.getSelectionModel().clearSelection();
            childrenTable.getSelectionModel().select(selectedIndex >=0 ? selectedIndex : 0);
        }
    }

    protected void doSave() {
        H dto = getDto();

        if (isInputValid(dto)) {
            saveDto(dto);
            refreshTable();
        }
    }

    private boolean isInputValid(H dto) {
        List<String> validation = FormValidator.validate(dto, getValidateableFields());

        if(validation.size() > 0) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save record", FormValidator.reduce(validation));
            return false;
        } else {
            return true;
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    protected void initialize() {
        selectedChild.addListener(new ChangeListener<H>() {
            @Override
            public void changed(ObservableValue<? extends H> observableValue, H dto1, H dto2) {
                saveButton.setDisable(dto2 == null);
                deleteButton.setDisable(dto2 == null);

                updateForm(dto2);
            }
        });


        childrenTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<H>() {
            @Override
            public void changed(ObservableValue<? extends H> observable,
                                H oldValue, H newValue) {
                selectedChild.set(newValue);
            }
        });

        tableModel.clear();
        childrenTable.setItems(tableModel);

    }

    @FXML
    protected void newFired() {
        selectedChild.set(null);
        saveButton.setDisable(false);
        updateForm(null);
    }

    @FXML
    protected void deleteFired() {
        if(WindowsUtil.getInstance().showWarningDialog("Delete Item", "Are you sure you want to delete the selected record?", null)) {
            deleteDto(selectedChild.get().getId());
            refreshTable();
        }
    }
}
