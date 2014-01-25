package com.kesho.datamart.ui.controller;

import calendar.FXCalendar;
import com.kesho.datamart.domain.FinancialArrangement;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.service.StudentService;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.SponsorsRepository;
import com.kesho.datamart.ui.util.SystemEventListener;
import com.kesho.datamart.ui.util.TabButton;
import com.kesho.datamart.ui.util.Util;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/30/13
 * Time: 7:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class PaymentArrangementController {
    @Inject
    private SponsorsRepository sponsorsRepository;
    @Inject
    private SponsorsController parentController;
    @Inject
    private StudentService studentService;

    @FXML
    private HBox startDateBox;
    private final FXCalendar startDateCalendar = new FXCalendar();
    @FXML
    private HBox endDateBox;
    private final FXCalendar endDateCalendar = new FXCalendar();
    @FXML
    private ComboBox<FinancialArrangement> financialArrangement;
    @FXML
    private TextField totalAllocated;
    @FXML
    private TextField student;
    @FXML
    private Tab paymentArrangementTab;
    @FXML
    private TableView<PaymentArrangementDto> paymentArrangementTable;
    private ObservableList<PaymentArrangementDto> tableModel = FXCollections.observableArrayList();

    @FXML
    private TableColumn<PaymentArrangementDto, LocalDate> startCol;
    @FXML
    private TableColumn<PaymentArrangementDto, String> studentNameCol;
    @FXML
    private TextField endOfEducation;  //TODO: how to work it out?
    @FXML
    private TextField educationLevel;

    private PaymentArrangementDto selected;

    public PaymentArrangementController() {
        startDateCalendar.setDateTextWidth(Double.valueOf(200));
        endDateCalendar.setDateTextWidth(Double.valueOf(200));
        WindowsUtil.getInstance().autowire(this);
    }

    @FXML
    private void openStudent() {
        WindowsUtil.getInstance().students();
    }

    @FXML
    private void selectStudent() {
        StudentDto dto = WindowsUtil.getInstance().studentSelector();
        if(dto != null) {
            student.setUserData(dto);
            student.setText(dto.getName());
//            selectedStudent.getSelectedItem().setFamily(dto);
        }
    }

    @FXML
    private void initialize() {
        student.setUserData(null);
        startDateBox.getChildren().add(startDateCalendar);
        endDateBox.getChildren().add(endDateCalendar);

        startCol.setCellValueFactory(new PropertyValueFactory<PaymentArrangementDto, LocalDate>("startDate"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<PaymentArrangementDto, String>("studentName"));
        studentNameCol.setCellFactory(new Callback<TableColumn<PaymentArrangementDto, String>, TableCell<PaymentArrangementDto, String>>() {
            @Override
            public TableCell<PaymentArrangementDto, String> call(TableColumn<PaymentArrangementDto, String> institutionDtoStringTableColumn) {
                TableCell<PaymentArrangementDto, String> cell = new TableCell<PaymentArrangementDto, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {

                        if(item!=null){
                            //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
                            Hyperlink link = new Hyperlink(item);
                            link.setUserData(((PaymentArrangementDto)getTableRow().getItem()).getStudentId());
                            //link.setUserData();
                            link.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    WindowsUtil.getInstance().students((Long) ((Hyperlink) e.getSource()).getUserData());
                                }
                            });
                            setGraphic(link);
                        }
                    }
                };

                return cell;
            }
        });

        Util.initializeComboBoxValues(financialArrangement, EnumSet.allOf(FinancialArrangement.class));

        WindowsUtil.getInstance().getEventBus().registerListener(com.kesho.datamart.ui.util.Event.SPONSOR_SELECTED, new SystemEventListener() {
            @Override
            public void handle() {
                if (paymentArrangementTab.isSelected()) {
                    refreshTable();
                }
            }
        });

        paymentArrangementTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(javafx.event.Event event) {
                if (paymentArrangementTab.isSelected()) {
                    refreshTable();
                    if (parentController.getSelectedItem() == null || parentController.getSelectedItem().getId() == null) {
                        parentController.disableButton(TabButton.NEW);
                    } else {
                        parentController.enableButton(TabButton.NEW);
                    }
                }
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WindowsUtil.getInstance().getControllers().sponsorsController().registerNewChangeListener("paymentArrangementTab", new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        add();
                    }
                });
            }
        });

        paymentArrangementTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PaymentArrangementDto>() {
            @Override
            public void changed(ObservableValue<? extends PaymentArrangementDto> observable,
                                PaymentArrangementDto oldValue, final PaymentArrangementDto newValue) {
                new Service<Void>() {
                    @Override
                    protected Task<Void> createTask() {
                        return new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                EducationDto dto = studentService.findLatestEducation(newValue.getStudentId());
                                educationLevel.setText(dto.getEducationLevel());
                                return null;
                            }
                        };
                    }
                }.start();

                initializeForm(newValue);
            }
        });


    }

    private void add() {
        PaymentArrangementDto dto = new PaymentArrangementDto();
        initializeForm(dto);
    }

    private void clearForm() {
        startDateCalendar.clear();
        endDateCalendar.clear();
        totalAllocated.clear();
        financialArrangement.getSelectionModel().clearSelection();
        financialArrangement.valueProperty().setValue(null);
        endOfEducation.clear();
        educationLevel.clear();
        student.clear();
    }

    private void initializeForm(PaymentArrangementDto dto) {
        selected = dto;
        if(dto == null) {
            clearForm();
            return;
        }

        student.setText(dto.getStudentName());
        if(dto.getStartDate() != null) {
            startDateCalendar.setValue(dto.getStartDate().toDate());
        } else {
            startDateCalendar.clear();
        }

        if (dto.getEndDate() != null) {
            endDateCalendar.setValue(dto.getEndDate().toDate());
        } else {
            endDateCalendar.clear();
        }

        financialArrangement.setValue(dto.getFinancialArrangement());
        totalAllocated.setText(Util.safeToStringValue(dto.getAmount(), ""));

//        student.setUserData(null);
    }

    @FXML
    private void save() {
        PaymentArrangementDto dto = buildDto();
        dto = sponsorsRepository.save(dto);

        refreshTable();
    }

    private PaymentArrangementDto buildDto() {
        if(selected == null) {
            selected = new PaymentArrangementDto();
        }

        if (endDateCalendar.getValue() != null) {
            selected.setEndDate(LocalDate.fromDateFields(endDateCalendar.getValue()));
        }

        selected.setStartDate(LocalDate.fromDateFields(startDateCalendar.getValue()));
        selected.setFinancialArrangement(financialArrangement.getValue());
        if(StringUtils.isNotBlank(totalAllocated.getText())) {
            selected.setAmount(new BigDecimal(totalAllocated.getText()));
        }

        selected.setStudentId(((StudentDto)student.getUserData()).getId());
        selected.setSponsorId(parentController.getSelectedItem().getId());

        return selected;
    }

    private void refreshTable() {
        tableModel.clear();
        if(parentController.getSelectedItem() != null) {
            List<PaymentArrangementDto> dtos = sponsorsRepository.getPaymentArrangements(parentController.getSelectedItem().getId());
            tableModel.addAll(dtos);
            int selectedIndex = paymentArrangementTable.getSelectionModel().getSelectedIndex();
            paymentArrangementTable.setItems(null);
            paymentArrangementTable.layout();
            paymentArrangementTable.setItems(tableModel);
            // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
            paymentArrangementTable.getSelectionModel().select(selectedIndex);
        }
    }
}