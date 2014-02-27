package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.SponsorsRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.SystemEventListener;
import com.kesho.datamart.ui.util.TabButton;
import javafx.application.Platform;
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
@Named("SponsorsController")
public class SponsorsController {
    @Autowired
    private SponsorsRepository sponsorsRepository;
    @FXML
    private TableView<SponsorDto> sponsorsTable;
    @FXML
    private TableColumn<SponsorDto, String> firstNameColumn;
    @FXML
    private TableColumn<SponsorDto, String> familyNameColumn;
    @FXML
    private Pagination pagination;
    @FXML
    private TabPane sponsorTabPane;
    @FXML
    private Button deleteButton;
    private SimpleObjectProperty<SponsorDto> selected = new SimpleObjectProperty<>();

    @FXML
    private SponsorController sponsorController;

    private ObservableList<SponsorDto> sponsorsModel = FXCollections.observableArrayList();

    void valueChanged() {
        refreshTable();
    }

    SimpleObjectProperty<SponsorDto> getSelectedProperty() {
        return selected;
    }

//    @Override
//    public SponsorDto getSelectedItem() {
//        return sponsorsTable.getSelectionModel().getSelectedItem();
//    }
//
//    @Override
//    public void refresh() {
//        if(sponsorsTable.getSortOrder().isEmpty()) {
//            firstNameColumn.setVisible(false);
//            firstNameColumn.setVisible(true);
//        } else {
//            ObservableList<TableColumn<SponsorDto, ?>> sort = sponsorsTable.getSortOrder();
//            ObservableList<TableColumn<SponsorDto, ?>> sort1 = FXCollections.observableArrayList();
//            sort1.addAll(sort);
//            sponsorsTable.getSortOrder().removeAll(sort);
//            sponsorsTable.getSortOrder().addAll(sort1);
//        }
//    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        selected.addListener(new ChangeListener<SponsorDto>() {
            @Override
            public void changed(ObservableValue<? extends SponsorDto> observableValue, SponsorDto sponsorDto, SponsorDto sponsorDto2) {
                deleteButton.setDisable(sponsorDto2 == null);
            }
        });

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<SponsorDto, String>("name"));
        familyNameColumn.setCellValueFactory(new PropertyValueFactory<SponsorDto, String>("surname"));

        sponsorsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SponsorDto>() {
            @Override
            public void changed(ObservableValue<? extends SponsorDto> observableValue, SponsorDto dto1, SponsorDto dto2) {
                selected.set(dto2);
                WindowsUtil.getInstance().getEventBus().fireEvent(Event.SPONSOR_SELECTED); // used by payment arrangements
            }
        });

        refreshTable();
        firstNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        sponsorsTable.getSelectionModel().select(0);

        WindowsUtil.getInstance().getEventBus().registerListener(Event.SPONSOR_ADDED, new SystemEventListener() {
            @Override
            public void handle() {
                refreshTable();
            }
        });
    }

    @FXML
    private void add() {
        sponsorController.newFired();
    }

    @FXML
    private void delete() {
        if(WindowsUtil.getInstance().showWarningDialog("Delete Sponsor", String.format("Are you sure you want to delete [%s]", selected.get().getName()), "NOTE: Deleting sponsor will alos delete its Payment Arrangements")) {
            Long id = selected.get().getId();
            sponsorsRepository.deleteSponsor(id);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    refreshTable();
                }
            });
            sponsorController.deleteFired(id);
        }
    }

    private void initPagination() {
        Page p = getPage(0, 10);
        if(p != null) {
            sponsorsModel.addAll(p.getContent());
            pagination.setPageCount(p.getTotalPages() > 0 ? p.getTotalPages() : 1);
            pagination.currentPageIndexProperty().setValue(0);
        }

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Page<SponsorDto> p = getPage(newValue.intValue(), 10);
                pagination.setPageCount(p.getTotalPages());
                sponsorsModel.clear();
                sponsorsModel.addAll(p.getContent());
            }
        });
    }

    private Page<SponsorDto> getPage(final int page, final int pageSize) {
        return sponsorsRepository.getPage(page, pageSize);
    }

    private void refreshTable() {
        sponsorsModel.clear();
        sponsorsTable.setItems(sponsorsModel);

        initPagination();
    }
}
