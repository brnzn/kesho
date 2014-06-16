package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.ui.validation.FormValidator;
import com.kesho.datamart.domain.FinancialArrangement;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.service.StudentService;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.SponsorsRepository;
import com.kesho.datamart.ui.util.Util;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class PaymentArrangementController extends AbstractChildController<SponsorDto>{
    @Inject
    private SponsorsRepository sponsorsRepository;
    @Inject
    private SponsorsController parentController;
    @Inject
    private StudentService studentService;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

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
    private TableColumn<PaymentArrangementDto, LocalDate> endCol;

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

    private SimpleObjectProperty<PaymentArrangementDto> selectedPayment = new SimpleObjectProperty<>();
//    private SimpleObjectProperty<SponsorDto> selectedSponsor = new SimpleObjectProperty<>();

    private Map<String, Node> validationFields = new HashMap<>();

    public PaymentArrangementController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @Override
    public Map<String, Node> getValidateableFields() {
        if(validationFields.isEmpty()) {
            validationFields.put("startDate", startDate);
            validationFields.put("endDate", endDate);
            validationFields.put("type", financialArrangement);
            validationFields.put("amount", totalAllocated);
            validationFields.put("studentId", student);
        }

        return validationFields;
    }

    @Override
    public void refresh(SponsorDto dto) {
        refreshTable();
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
        }
    }

    @FXML
    private void initialize() {
//        selectedSponsor.bind(parentController.getSelectedProperty());
//        selectedSponsor.addListener(new ChangeListener<SponsorDto>() {
//            @Override
//            public void changed(ObservableValue<? extends SponsorDto> observableValue, SponsorDto dto, SponsorDto dto1) {
//                refreshTable();
//            }
//        });


        selectedPayment.addListener(new ChangeListener<PaymentArrangementDto>() {
            @Override
            public void changed(ObservableValue<? extends PaymentArrangementDto> observableValue, PaymentArrangementDto dto1, PaymentArrangementDto dto2) {
                saveButton.setDisable(dto2 == null);
                deleteButton.setDisable(dto2 == null || dto2.getId() == null);
            }
        });

        student.setUserData(null);

        startCol.setCellValueFactory(new PropertyValueFactory<PaymentArrangementDto, LocalDate>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<PaymentArrangementDto, LocalDate>("endDate"));
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

//        paymentArrangementTab.setOnSelectionChanged(new EventHandler<Event>() {
//            @Override
//            public void handle(javafx.event.Event event) {
//                refreshTable();
//            }
//        });

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
            sponsorsRepository.deletePaymentArrangement(selectedPayment.get().getId());
            refreshTable();
        }
    }

    private void clearForm() {
        startDate.valueProperty().setValue(null);
        endDate.valueProperty().setValue(null);
        totalAllocated.clear();
        financialArrangement.getSelectionModel().clearSelection();
        financialArrangement.valueProperty().setValue(null);
        endOfEducation.clear();
        educationLevel.clear();
        student.clear();
    }

    private void initializeForm(PaymentArrangementDto dto) {
        selectedPayment.set(dto);
        if(dto == null) {
            clearForm();
            return;
        }

        student.setText(dto.getStudentName());
        startDate.valueProperty().setValue(Util.toJavaDate(dto.getStartDate()));

        endDate.valueProperty().setValue(Util.toJavaDate(dto.getEndDate()));


        financialArrangement.setValue(dto.getFinancialArrangement());
        totalAllocated.setText(Util.safeToStringValue(dto.getAmount(), ""));
    }

    @FXML
    private void save() {
        PaymentArrangementDto dto = buildDto();

        List<String> validation = FormValidator.validate(dto, getValidateableFields());

        if(validation.size() > 0) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save Payment Arrangement details", FormValidator.reduce(validation));
        } else {
            sponsorsRepository.save(dto);
            refreshTable();
        }
    }

    private PaymentArrangementDto buildDto() {
        PaymentArrangementDto dto = new PaymentArrangementDto();

        dto.setId(selectedPayment.get().getId());
        dto.setEndDate(Util.toJodaDate(endDate.getValue()));

        dto.setStartDate(Util.toJodaDate(startDate.getValue()));

        dto.setFinancialArrangement(financialArrangement.getValue());

        if(StringUtils.isNotBlank(totalAllocated.getText())) {
            dto.setAmount(new BigDecimal(totalAllocated.getText()));
        }

        if(student != null && student.getUserData() != null) {
            dto.setStudentId(((StudentDto)student.getUserData()).getId());
        }

        dto.setSponsorId(selected.get().getId());

        return dto;
    }

    void refreshTable() {
//        if (paymentArrangementTab.isSelected()) {
            tableModel.clear();
            if(selected.get() != null) {
                List<PaymentArrangementDto> dtos = sponsorsRepository.getPaymentArrangements(selected.get().getId());
                tableModel.addAll(dtos);
                int selectedIndex = paymentArrangementTable.getSelectionModel().getSelectedIndex();
                paymentArrangementTable.setItems(null);
                paymentArrangementTable.layout();
                paymentArrangementTable.setItems(tableModel);
                // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
                paymentArrangementTable.getSelectionModel().select(selectedIndex);
            }
 //       }
    }

}