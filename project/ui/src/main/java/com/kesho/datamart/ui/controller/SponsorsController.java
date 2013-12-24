package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.SponsorsRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.SystemEventListener;
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
public class SponsorsController implements Selectable<SponsorDto> {
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
    private Button newSponsorBtn;

    private ObservableList<SponsorDto> sponsorsModel = FXCollections.observableArrayList();
    private Map<String, EventHandler<ActionEvent>> newButtonHandlers = new HashMap<>();

    @Override
    public SponsorDto getSelectedItem() {
        return sponsorsTable.getSelectionModel().getSelectedItem();
    }

    @Override
    public void refresh() {
        if(sponsorsTable.getSortOrder().isEmpty()) {
            firstNameColumn.setVisible(false);
            firstNameColumn.setVisible(true);
        } else {
            ObservableList<TableColumn<SponsorDto, ?>> sort = sponsorsTable.getSortOrder();
            ObservableList<TableColumn<SponsorDto, ?>> sort1 = FXCollections.observableArrayList();
            sort1.addAll(sort);
            sponsorsTable.getSortOrder().removeAll(sort);
            sponsorsTable.getSortOrder().addAll(sort1);
        }
    }

    public void registerNewChangeListener(String id, EventHandler<ActionEvent> eventHandler) {
        newButtonHandlers.put(id, eventHandler);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        refreshTable();
        firstNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        sponsorsTable.getSelectionModel().select(0);
//        sponsorsTable.focusModelProperty().get().focus(0, firstNameColumn);
//        sponsorsTable.requestFocus();
//        sponsorsTable.layout();

        newSponsorBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                add();
                newButtonHandlers.get(sponsorTabPane.getSelectionModel().getSelectedItem().getId()).handle(actionEvent);
            }
        });

        sponsorTabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab tab2) {
                if (newButtonHandlers.get(tab2.getId()) == null) {
                    newSponsorBtn.disableProperty().setValue(true);
                } else {
                    newSponsorBtn.disableProperty().setValue(false);
                }
            }
        });

        WindowsUtil.getInstance().getEventBus().registerListener(Event.SPONSOR_ADDED, new SystemEventListener() {
            @Override
            public void handle() {
                refreshTable();
            }
        });
    }

//    private void add() {
//        SponsorDto dto = new SponsorDto();
//        boolean isOK = WindowsUtil.getInstance().sponsorForm(dto);
//
//        if (isOK) {
//            sponsorsRepository.save(dto);
//            WindowsUtil.getInstance().getEventBus().fireEvent(Event.SPONSOR_ADDED);
//            refreshTable();
//        }
//    }

    private void initTable() {
        sponsorsModel.clear();

        sponsorsTable.setItems(sponsorsModel);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<SponsorDto, String>("name"));
        familyNameColumn.setCellValueFactory(new PropertyValueFactory<SponsorDto, String>("surname"));

        sponsorsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SponsorDto>() {
            @Override
            public void changed(ObservableValue<? extends SponsorDto> observableValue, SponsorDto dto1, SponsorDto dto2) {
                WindowsUtil.getInstance().getEventBus().fireEvent(Event.SPONSOR_SELECTED);
            }
        });
    }

    private void initPagination() {
        Page p = getPage(0, 10);
        if(p != null) {
            sponsorsModel.addAll(p.getContent());    //
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
        initTable();
        initPagination();
    }
}
