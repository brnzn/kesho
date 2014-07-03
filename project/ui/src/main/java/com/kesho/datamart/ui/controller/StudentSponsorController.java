package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.FinancialSupportStatus;
import com.kesho.datamart.domain.FinancialSupportStatusDetails;
import com.kesho.datamart.domain.LevelOfSupport;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.PaymentArrangementDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.dto.StudentSponsorDto;
import com.kesho.datamart.ui.FormActionListener;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.FamilyRepository;
import com.kesho.datamart.ui.repository.SponsorsRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: After New and Update need to refresh table, but leave form as is

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentSponsorController extends AbstractEditableController<StudentDto> {
    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private SponsorsRepository sponsorsRepository;

    @FXML
    private ToggleGroup enrichmentSupport;

    @FXML
    private ToggleGroup financialSupport;

    @FXML
    private ComboBox<FinancialSupportStatus> financialSupportStatus;
    @FXML
    private ComboBox<FinancialSupportStatusDetails> financialSupportStatusDetails;
    @FXML
    private TextField otherFinancialSupportStatusDetails;

    @FXML
    private ComboBox<LevelOfSupport> levelOfSupport;
    @FXML
    private ToggleGroup topupNeeded;
    @FXML
    private TextField shortfall;    // numeric
    @FXML
    private TextField totalSRequired; // numeric

    @FXML
    private Button saveButton;

    @FXML
    private TableColumn<StudentSponsorDto, Boolean> anonymityCol;

    @FXML
    private TableColumn<StudentSponsorDto, LocalDate> endOfCommitCol;
    @FXML
    private TableColumn<StudentSponsorDto, String> surnameCol;
    @FXML
    private TableColumn<StudentSponsorDto, String> nameCol;
    @FXML
    private TableColumn<StudentSponsorDto, BigDecimal> amountCol;

    @FXML
    private TableView<StudentSponsorDto> sponsorsTable;
    private ObservableList<StudentSponsorDto> sponsorsModel = FXCollections.observableArrayList();

    @Inject
    private StudentsController parentController;
    @Inject
    private FamilyRepository familyRepository;

    private Map<String, Node> validationFields = new HashMap<>();


    public StudentSponsorController() {
        WindowsUtil.getInstance().autowire(this);
    }


    @Override
    public void refresh(StudentDto dto) {
        saveButton.setDisable(dto == null);
        refreshEducationTable();
        itemSelected(dto);
    }

    public Map<String, Node> getValidateableFields() {
        return validationFields;
    }

    @FXML
    private void initialize() {
        anonymityCol.setCellValueFactory(new PropertyValueFactory<StudentSponsorDto, Boolean>("anonymity"));
        endOfCommitCol.setCellValueFactory(new PropertyValueFactory<StudentSponsorDto, LocalDate>("endOfCommitment"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<StudentSponsorDto, String>("surname"));
        nameCol.setCellValueFactory(new PropertyValueFactory<StudentSponsorDto, String>("name"));
        //TODO: grey line if sponsor not active
        nameCol.setCellFactory(new Callback<TableColumn<StudentSponsorDto, String>, TableCell<StudentSponsorDto, String>>() {
            @Override
            public TableCell<StudentSponsorDto, String> call(TableColumn<StudentSponsorDto, String> sponsorName) {
                TableCell<StudentSponsorDto, String> cell = new TableCell<StudentSponsorDto, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {

                        if(item!=null){
                            if(!((StudentSponsorDto)getTableRow().getItem()).isActive()) {
                                getTableRow().getStyleClass().add("row");
                            } else {
                                getTableRow().getStyleClass().remove("row");
                            }
                            Hyperlink link = new Hyperlink(item);
                            link.underlineProperty().setValue(true);
                            link.setUserData(((StudentSponsorDto)getTableRow().getItem()).getSponsorId());
                            link.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    WindowsUtil.getInstance().sponsors((Long) ((Hyperlink) e.getSource()).getUserData());
                                }
                            });
                            setGraphic(link);
                        }
                    }
                };

                return cell;
            }
        });

        amountCol.setCellValueFactory(new PropertyValueFactory<StudentSponsorDto, BigDecimal>("amount"));

        sponsorsModel.clear();
        sponsorsTable.setItems(sponsorsModel);

        Util.decorateNumericInput(shortfall, totalSRequired);

        Util.initializeYesNoGroup(financialSupport, topupNeeded, enrichmentSupport);
        Util.initializeComboBoxValues(financialSupportStatus, EnumSet.allOf(FinancialSupportStatus.class));

        financialSupportStatus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FinancialSupportStatus>() {
            @Override
            public void changed(ObservableValue<? extends FinancialSupportStatus> observableValue, FinancialSupportStatus s, FinancialSupportStatus s2) {
                financialSupportStatusDetails.getItems().clear();
                if (s2 != null) {
                    if (FinancialSupportStatus.OTHER != s2) {
                        financialSupportStatusDetails.getItems().addAll(s2.getChildren());
                        financialSupportStatusDetails.setVisible(true);
                        otherFinancialSupportStatusDetails.setVisible(false);
                        otherFinancialSupportStatusDetails.clear();
                    } else {
                        financialSupportStatusDetails.setVisible(false);
                        financialSupportStatusDetails.getSelectionModel().clearSelection();
                        otherFinancialSupportStatusDetails.setVisible(true);
                    }
                }
            }
        });

        Util.initializeComboBoxValues(levelOfSupport, EnumSet.allOf(LevelOfSupport.class));
    }

    private  void itemSelected(StudentDto student) {
        if(student == null) {
            resetForm();
            return;
        }

        Util.setYesNoToggleState(financialSupport, student.hasFinancialSupport());
        Util.setYesNoToggleState(enrichmentSupport, student.getEnrichmentSupport());
        Util.setYesNoToggleState(topupNeeded, student.isTopupNeeded());


        financialSupportStatus.getSelectionModel().select(student.getFinancialSupportStatus());
        if(student.getFinancialSupportStatus() != null && FinancialSupportStatus.OTHER != student.getFinancialSupportStatus() && student.getFinancialSupportStatusDetails() != null) {
            financialSupportStatusDetails.getSelectionModel().select(FinancialSupportStatusDetails.valueOf(student.getFinancialSupportStatusDetails()));
        } else if (FinancialSupportStatus.OTHER == student.getFinancialSupportStatus()){
            otherFinancialSupportStatusDetails.setText(student.getFinancialSupportStatusDetails());
        }

        levelOfSupport.getSelectionModel().select(student.getLevelOfSupport());

        shortfall.setText(Util.safeToStringValue(student.getShortfall(), null));

        totalSRequired.setText(Util.safeToStringValue(student.getTotalSponsorshipRequired(), null));
    }

    @FXML
    private void save() {
        StudentDto dto = buildDto();
        if (isInputValid(dto)) {
            boolean isNew = dto.getId() == null;

            dto = studentsRepository.save(dto);  //looks like it generate too many sqls
        }
    }

    private StudentDto buildDto() {
        StudentDto dto = selected.get();

        dto.withEnrichmentSupport((Boolean) enrichmentSupport.getSelectedToggle().getUserData())
                .withFinancialSupport((Boolean) financialSupport.getSelectedToggle().getUserData())
                .withTopupNeeded((Boolean) topupNeeded.getSelectedToggle().getUserData())
                .withFinancialSupportStatus(financialSupportStatus.getSelectionModel().getSelectedItem())
                .withFinancialSupportStatusDetails(getFinancialSupportStatusDetails())
                .withLevelOfSupport(levelOfSupport.getSelectionModel().getSelectedItem())
                .withShortfall(Util.safeToIntegerValue(shortfall.getText(), null))
                .withTotalSponsorshipRequired(Util.safeToIntegerValue(totalSRequired.getText(), null))
        ;

        return dto;
    }

    private String getFinancialSupportStatusDetails() {
        return FinancialSupportStatus.OTHER == financialSupportStatus.getSelectionModel().getSelectedItem() ?
               otherFinancialSupportStatusDetails.getText() :
               financialSupportStatusDetails.getSelectionModel().getSelectedItem() != null ?
                       financialSupportStatusDetails.getSelectionModel().getSelectedItem().name() : null;

    }
    private void resetForm() {
        enrichmentSupport.getToggles().get(0).setSelected(true);
        financialSupport.getToggles().get(0).setSelected(true);

        topupNeeded.getToggles().get(0).setSelected(true);

        financialSupportStatus.getSelectionModel().clearSelection();
        levelOfSupport.getSelectionModel().clearSelection();
        shortfall.clear();
        totalSRequired.clear();
    }

    private boolean isInputValid(StudentDto dto) {
        List<String> validation = FormValidator.validate(dto, getValidateableFields());

        if(validation.size() > 0) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save Student details", FormValidator.reduce(validation));
            return false;
        } else {
            return true;
        }

    }

    private void refreshEducationTable() {
        sponsorsModel.clear();
        if(selected.get() != null) {
            List<StudentSponsorDto> dtos = sponsorsRepository.getStudentSponsors(selected.get().getId());
            sponsorsModel.addAll(dtos);
            int selectedIndex = sponsorsTable.getSelectionModel().getSelectedIndex();
            sponsorsTable.setItems(null);
            sponsorsTable.layout();
            sponsorsTable.setItems(sponsorsModel);
            // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
            sponsorsTable.getSelectionModel().select(selectedIndex);
        }
    }

}
