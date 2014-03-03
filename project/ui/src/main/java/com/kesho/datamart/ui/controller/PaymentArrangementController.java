package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.ui.validation.FormValidator;
import com.kesho.ui.control.calendar.FXCalendar;
import com.kesho.datamart.domain.FinancialArrangement;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.service.StudentService;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.SponsorsRepository;
import com.kesho.datamart.ui.util.SystemEventListener;
import com.kesho.datamart.ui.util.TabButton;
import com.kesho.datamart.ui.util.Util;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;

    private SimpleObjectProperty<PaymentArrangementDto> selected = new SimpleObjectProperty<>();
    private SimpleObjectProperty<SponsorDto> selectedSponsor = new SimpleObjectProperty<>();

    private Map<String, Node> validationFields = new HashMap<>();

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
            student.setText(dto.getFirstName());
//            selectedStudent.getSelectedItem().setFamily(dto);
        }
    }

    @FXML
    private void initialize() {
        selectedSponsor.bind(parentController.getSelectedProperty());
        selectedSponsor.addListener(new ChangeListener<SponsorDto>() {
            @Override
            public void changed(ObservableValue<? extends SponsorDto> observableValue, SponsorDto dto, SponsorDto dto1) {
                refreshTable();
            }
        });


        selected.addListener(new ChangeListener<PaymentArrangementDto>() {
            @Override
            public void changed(ObservableValue<? extends PaymentArrangementDto> observableValue, PaymentArrangementDto dto1, PaymentArrangementDto dto2) {
                saveButton.setDisable(dto2 == null);
                deleteButton.setDisable(dto2 == null || dto2.getId() == null);
            }
        });

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

        paymentArrangementTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(javafx.event.Event event) {
                refreshTable();
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

    @FXML
    private void add() {
        PaymentArrangementDto dto = new PaymentArrangementDto();
        initializeForm(dto);
    }

    @FXML
    private void delete () {
        if(WindowsUtil.getInstance().showWarningDialog("Delete Financial Arrangement", "Are you sure you want to delete the selected Financial Arrangement row?", null)) {
            sponsorsRepository.deletePaymentArrangement(selected.get().getId());
            refreshTable();
        }
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
        selected.set(dto);
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
        if(FormValidator.validateAndAlert(dto, getFields())) {
            sponsorsRepository.save(dto);
            refreshTable();
        }
    }

    private PaymentArrangementDto buildDto() {
        PaymentArrangementDto dto = new PaymentArrangementDto();

        if (endDateCalendar.getValue() != null) {
            dto.setEndDate(LocalDate.fromDateFields(endDateCalendar.getValue()));
        }

        if(startDateCalendar.getValue() != null) {
            dto.setStartDate(LocalDate.fromDateFields(startDateCalendar.getValue()));
        }

        dto.setFinancialArrangement(financialArrangement.getValue());

        if(StringUtils.isNotBlank(totalAllocated.getText())) {
            dto.setAmount(new BigDecimal(totalAllocated.getText()));
        }

        if(student != null) {
            dto.setStudentId(((StudentDto)student.getUserData()).getId());
        }

        dto.setSponsorId(selectedSponsor.get().getId());

        return dto;
    }

    private void refreshTable() {
        if (paymentArrangementTab.isSelected()) {
            tableModel.clear();
            if(selectedSponsor.get() != null) {
                List<PaymentArrangementDto> dtos = sponsorsRepository.getPaymentArrangements(selectedSponsor.get().getId());
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

    private Map<String, Node> getFields() {
        if(validationFields.isEmpty()) {
            validationFields.put("startDate", startDateBox);
            validationFields.put("endDate", endDateBox);
            validationFields.put("type", financialArrangement);
            validationFields.put("amount", totalAllocated);
            validationFields.put("studentId", student);
        }

        return validationFields;
    }

}