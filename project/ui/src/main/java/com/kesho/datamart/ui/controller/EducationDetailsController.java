package com.kesho.datamart.ui.controller;

import calendar.FXCalendar;
import com.kesho.datamart.domain.EducationStatus;
import com.kesho.datamart.domain.SubEducationStatus;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.InstitutionRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import java.util.EnumSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class EducationDetailsController {
    @FXML
    private HBox dateControlBox;
    private final FXCalendar calendar = new FXCalendar();
    @FXML
    private ComboBox<InstitutionDto> institutions;
    @FXML
    private TextField educationYear;
    @FXML
    private TextField course;
    @FXML
    private ComboBox<EducationStatus> educationalStatus;
    @FXML
    private ComboBox<SubEducationStatus> secondaryStatus1;
    @FXML
    private ComboBox<SubEducationStatus> secondaryStatus2;
    @FXML
    private TextArea comments;

    private ObservableList<EducationDto> educationModel = FXCollections.observableArrayList();
//    private EducationDto selected;

    @FXML
    private TableView<EducationDto> educationTable;
    @FXML
    private Tab educationTab;

    @FXML
    private TableColumn<EducationDto, String> institutionCol;
    @FXML
    private TableColumn<EducationDto, LocalDate> educationDateCol;
    @FXML
    private TableColumn<EducationDto, String> educationLevelCol;
    @FXML
    private TableColumn<EducationDto, String> yearCol;
    @FXML
    private TableColumn<EducationDto, String> courseCol;


//    private Stage dialogStage;
//    private EducationDto dto;
//    private boolean okClicked = false;

    @Inject
    private InstitutionRepository institutionRepository;
    @Inject
    private StudentsRepository studentsRepository;
    @Inject
    private Selectable<StudentDto> selectedStudent;


    public EducationDetailsController() {
        WindowsUtil.getInstance().autowire(this);

        WindowsUtil.getInstance().getControllers().detailsController().registerChangeListener(new ChangeListener<StudentDto>() {
            @Override
            public void changed(ObservableValue<? extends StudentDto> observable,
                                StudentDto oldValue, StudentDto newValue) {
                if(educationTab.isSelected()) {
                    loadEducationHistory();
                }
            }
        });

        WindowsUtil.getInstance().getControllers().detailsController().registerTabChangeListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab tab2) {
                if("educationTab".equals(tab2.getId())) {
                    institutions.getItems().addAll(FXCollections.observableArrayList(institutionRepository.getInstitutions()));
                    loadEducationHistory();
                }
            }
        });

        calendar.setDateTextWidth(Double.valueOf(100));
    }

    @FXML
    private void save() {
        EducationDto dto = educationTable.getSelectionModel().getSelectedItem();

        dto.withYear(Util.safeToIntegerValue(educationYear.getText(), null))
                .withEducationalStatus(educationalStatus.getSelectionModel().getSelectedItem())
                .withCourse(course.getText())
                .withSecondaryStatus1(secondaryStatus1.getSelectionModel().getSelectedItem())
                .withSecondaryStatus2(secondaryStatus2.getSelectionModel().getSelectedItem())
                .withInstitution(institutions.getSelectionModel().getSelectedItem())
                .withComments(comments.getText());

        if(calendar.getValue() != null) {
            dto.withEducationDate(LocalDate.fromDateFields(calendar.getValue()));
        }

        studentsRepository.save(dto);
    }

    private void loadEducationHistory() {
        educationModel.clear();
        educationTable.setItems(educationModel);

        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        List<EducationDto> d = studentsRepository.getEducationHistory(selectedStudent.getSelectedItem().getId());
                        educationModel.addAll(studentsRepository.getEducationHistory(selectedStudent.getSelectedItem().getId()));
                        return null;
                    }
                };
            }
        }.start();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        institutionCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("institutionName"));
        educationDateCol.setCellValueFactory(new PropertyValueFactory<EducationDto, LocalDate>("date"));
        educationLevelCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("educationalStatus"));
        yearCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("year"));
        courseCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("course"));

        educationModel.clear();
        educationTable.setItems(educationModel);

        educationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EducationDto>() {
            @Override
            public void changed(ObservableValue<? extends EducationDto> observable,
                                EducationDto oldValue, EducationDto newValue) {
                updateEducationForm(newValue);
            }
        });

        dateControlBox.getChildren().add(calendar);
        Util.initializeComboBoxValues(educationalStatus, EnumSet.allOf(EducationStatus.class));
        educationalStatus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EducationStatus>() {
            @Override
            public void changed(ObservableValue<? extends EducationStatus> observableValue, EducationStatus s, EducationStatus s2) {
                if(s2 == null) {
                    secondaryStatus1.getSelectionModel().clearSelection();
                } else {
                    secondaryStatus1.getItems().clear();
                    secondaryStatus1.getItems().addAll(s2.getChildren());
                }
            }
        });
    }

    private void updateEducationForm(EducationDto dto) {
        if(dto == null) {
            clearForm();
            return;
        }

        educationYear.setText(Util.safeToStringValue(dto.getYear(), ""));
        course.setText(dto.getCourse());
        educationalStatus.getSelectionModel().select(dto.getEducationalStatus());
        calendar.setValue(dto.getDate().toDate());
        institutions.getSelectionModel().select(dto.getInstitution());
    }


    private void clearForm() {
        educationYear.clear();
        course.clear();
        educationalStatus.getSelectionModel().clearSelection();
        educationalStatus.valueProperty().setValue(null);
        calendar.clear();
        institutions.getSelectionModel().clearSelection();
        institutions.valueProperty().setValue(null);
    }
    /**
     * Sets the stage of this dialog.
     * @param
     */
//    public void setDialogStage(Stage dialogStage) {
//        this.dialogStage = dialogStage;
//    }

//    public void setPerson(EducationDto dto) {
//        populateInstitutions(dto);
//
//        this.dto = dto;
//        if(dto.getDate() != null) {
//            calendar.setValue(dto.getDate().toDate());
//        }
//
//        educationYear.setText(Util.safeToStringValue(dto.getYear(), null));
//        course.setText(dto.getCourse());
//        comments.setText(dto.getComments());
//        secondaryStatus1.getSelectionModel().select(dto.getSecondaryEducationStatus1());
//        secondaryStatus2.getSelectionModel().select(dto.getSecondaryEducationStatus2());
//
//        if (dto.getEducationalStatus() != null) {
//            educationalStatus.getSelectionModel().select(dto.getEducationalStatus());
//        }
//    }

//    private void populateInstitutions(final EducationDto dto) {
//        new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        institutions.getItems().clear();
//                        institutions.getSelectionModel().select(dto.getInstitution());
//                        return null;
//                    }
//                };
//            }
//        }.start();
//    }
    /**
     * Returns true if the user clicked OK, false otherwise.
     * @return
     */
//    public boolean isOkClicked() {
//        return okClicked;
//    }

    /**
     * Called when the user clicks ok.
     */
//    @FXML
//    private void handleOk() {
//        if (isInputValid()) {
//            dto.withYear(Util.safeToIntegerValue(educationYear.getText(), null))
//                    .withEducationalStatus(educationalStatus.getSelectionModel().getSelectedItem())
//                    .withCourse(course.getText())
//                    .withSecondaryStatus1(secondaryStatus1.getSelectionModel().getSelectedItem())
//                    .withSecondaryStatus2(secondaryStatus2.getSelectionModel().getSelectedItem())
//                    .withInstitution(institutions.getSelectionModel().getSelectedItem())
//                    .withComments(comments.getText());
//
//            if(calendar.getValue() != null) {
//                dto.withEducationDate(LocalDate.fromDateFields(calendar.getValue()));
//            }
//
//            okClicked = true;
//            dialogStage.close();
//        }
//    }

    /**
     * Called when the user clicks cancel.
     */
//    @FXML
//    private void handleCancel() {
//        dialogStage.close();
//    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
//    private boolean isInputValid() {
//        String errorMessage = "";
//
//        if (institutions.getSelectionModel().isEmpty()) {
//            errorMessage += "Institution Name!\n";
//        }
//
//        if (errorMessage.length() == 0) {
//            return true;
//        } else {
//            // Show the error message
//            Dialogs.showErrorDialog(dialogStage, errorMessage,
//                    "Please correct invalid fields", "Invalid Fields");
//            return false;
//        }
//    }

}
