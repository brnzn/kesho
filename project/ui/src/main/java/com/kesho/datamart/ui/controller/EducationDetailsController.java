package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.EducationStatus;
import com.kesho.datamart.domain.EducationYear;
import com.kesho.datamart.domain.SubEducationStatus;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.InstitutionRepository;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Util;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import java.time.Month;
import java.time.MonthDay;
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
public class EducationDetailsController extends AbstractEditableController<StudentDto> {
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker predictedEndDate;
    @FXML
    private ComboBox<InstitutionDto> institutions;
    @FXML
    private ComboBox<EducationYear> educationYear;
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

    @FXML
    private TableView<EducationDto> educationTable;

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
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;

    @Inject
    private InstitutionRepository institutionRepository;
    @Inject
    private StudentsRepository studentsRepository;

    private Tab educationTab;


    private SimpleObjectProperty<EducationDto> selectedEducation = new SimpleObjectProperty<>();

    private Map<String, Node> validationFields = new HashMap<>();

    public EducationDetailsController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @Override
    public void refresh(StudentDto dto) {
        loadInstitutions();
        refreshEducationTable();
        resetForm();
    }

    public Map<String, Node> getValidateableFields() {
        if(validationFields.isEmpty()) {
            validationFields.put("date", startDate);
            validationFields.put("educationalStatus", educationalStatus);
        }

        return validationFields;
    }

    private void refreshEducationTable() {
        educationModel.clear();
        if(selected.get() != null) {
            List<EducationDto> dtos = studentsRepository.getEducationHistory(selected.get().getId());
            educationModel.addAll(dtos);
            int selectedIndex = educationTable.getSelectionModel().getSelectedIndex();
            educationTable.setItems(null);
            educationTable.layout();
            educationTable.setItems(educationModel);
            // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
            educationTable.getSelectionModel().select(selectedIndex);
        }
    }


    protected void doSave() {
        EducationDto dto = new EducationDto();
        dto.withId(selectedEducation.get().getId());

        dto.withStudentId(selected.get().getId()).
            withYear(educationYear.getSelectionModel().getSelectedItem())
            .withEducationalStatus(educationalStatus.getSelectionModel().getSelectedItem())
            .withCourse(course.getText())
            .withSecondaryStatus1(secondaryStatus1.getSelectionModel().getSelectedItem())
            .withSecondaryStatus2(secondaryStatus2.getSelectionModel().getSelectedItem())
            .withInstitution(institutions.getSelectionModel().getSelectedItem())
            .withComments(comments.getText())
            .withEducationDate(Util.toJodaDate(startDate.getValue()))
            .withPredictedEndDate(Util.toJodaDate(predictedEndDate.getValue()));

        if (isInputValid(dto)) {
            studentsRepository.save(dto);
            refreshEducationTable();
        }
    }

    private boolean isInputValid(EducationDto dto) {
        List<String> validation = FormValidator.validate(dto, getValidateableFields());

        //Reset effect
        institutions.setEffect(null);
        course.setEffect(null);

        if(institutions.getSelectionModel().getSelectedItem() == null) {
            if (isSchoolRequired()) {
                validation.add("School is required");
                FormValidator.renderInvalid(institutions);
            }
        }

        if(StringUtils.isBlank(course.getText()) && isCourseRequired()) {
            validation.add("Course is required");
            FormValidator.renderInvalid(course);
        }

        if(educationYear.getSelectionModel() == null && isSchoolRequired()) {
            validation.add("Class / Year is required");
            FormValidator.renderInvalid(educationYear);
        }

        if(validation.size() > 0) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save Education details", FormValidator.reduce(validation));
            return false;
        } else {
            return true;
        }
    }

    private boolean isCourseRequired() {
        EducationStatus status = educationalStatus.getSelectionModel().getSelectedItem();
        return status == EducationStatus.College || status == EducationStatus.University;

    }
    private boolean isSchoolRequired() {
        EducationStatus status = educationalStatus.getSelectionModel().getSelectedItem();
        return EducationStatus.GapAfterTertiary != status && EducationStatus.GapSchoolLeaver != status;

    }

    private void loadInstitutions() {
        institutions.getItems().clear();
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        institutions.getItems().addAll(FXCollections.observableArrayList(institutionRepository.getInstitutions()));
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
        selectedEducation.addListener(new ChangeListener<EducationDto>() {
            @Override
            public void changed(ObservableValue<? extends EducationDto> observableValue, EducationDto dto1, EducationDto dto2) {
                saveButton.setDisable(dto2 == null);
                deleteButton.setDisable(dto2 == null || dto2.getId() == null);
            }
        });

        institutionCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("institutionName"));
        educationDateCol.setCellValueFactory(new PropertyValueFactory<EducationDto, LocalDate>("date"));
        educationLevelCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("educationalStatus"));
        yearCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("year"));
        courseCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("course"));

        educationModel.clear();
        educationTable.setItems(educationModel);

        selectedEducation.addListener(new ChangeListener<EducationDto>() {
            @Override
            public void changed(ObservableValue<? extends EducationDto> observable, EducationDto oldValue, EducationDto newValue) {
                updateEducationForm(newValue);
            }
        });

        educationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EducationDto>() {
            @Override
            public void changed(ObservableValue<? extends EducationDto> observable,
                                EducationDto oldValue, EducationDto newValue) {
                selectedEducation.set(newValue);
            }
        });

        Util.initializeComboBoxValues(educationalStatus, EnumSet.allOf(EducationStatus.class));
        educationalStatus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EducationStatus>() {
            @Override
            public void changed(ObservableValue<? extends EducationStatus> observableValue, EducationStatus s, EducationStatus s2) {
                secondaryStatus2.valueProperty().setValue(null);
                secondaryStatus1.valueProperty().setValue(null);
                educationYear.valueProperty().setValue(null);

                secondaryStatus2.getItems().clear();
                secondaryStatus1.getItems().clear();
                secondaryStatus2.getSelectionModel().clearSelection();
                secondaryStatus1.getSelectionModel().clearSelection();
                educationYear.getItems().clear();
                educationYear.getSelectionModel().clearSelection();

                if(s2 != null) {
                    secondaryStatus1.getItems().addAll(s2.getChildren());
                    educationYear.getItems().addAll(s2.getClasses());
                }
            }
        });

        secondaryStatus1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SubEducationStatus>() {
            @Override
            public void changed(ObservableValue<? extends SubEducationStatus> observableValue, SubEducationStatus s, SubEducationStatus s2) {
                if(s2 == null) {
                    secondaryStatus2.getSelectionModel().clearSelection();
                } else {
                    secondaryStatus2.getItems().clear();
                    secondaryStatus2.getItems().addAll(s2.getChildren());
                }
            }
        });

    }

    private void updateEducationForm(EducationDto dto) {
        if(dto == null) {
            resetForm();
            return;
        }

        course.setText(dto.getCourse());
        educationalStatus.getSelectionModel().select(dto.getEducationalStatus());
        startDate.valueProperty().setValue(Util.toJavaDate(dto.getDate()));
        predictedEndDate.valueProperty().setValue(Util.toJavaDate(dto.getPredictedEndDate()));
        institutions.getSelectionModel().select(dto.getInstitution());
        secondaryStatus1.getSelectionModel().select(dto.getSecondaryEducationStatus1());
        secondaryStatus2.getSelectionModel().select(dto.getSecondaryEducationStatus2());
        educationYear.getSelectionModel().select(dto.getYear());
        comments.setText(dto.getComments());
    }


    private void resetForm() {
        educationYear.setValue(null);
        educationYear.getSelectionModel().clearSelection();
        course.clear();
        educationalStatus.getSelectionModel().clearSelection();
        educationalStatus.valueProperty().setValue(null);
        startDate.setValue(null);
        predictedEndDate.setValue(null);
        institutions.getSelectionModel().clearSelection();
        institutions.valueProperty().setValue(null);
        secondaryStatus1.getSelectionModel().clearSelection();
        secondaryStatus1.valueProperty().setValue(null);
        secondaryStatus2.getSelectionModel().clearSelection();
        secondaryStatus2.valueProperty().setValue(null);
        comments.clear();
    }

    @FXML
    private void newFired() {
        resetForm();
        selectedEducation.set(getDefaultDto());
        java.time.LocalDate date = java.time.LocalDate.now().withMonth(Month.JANUARY.getValue()).withDayOfMonth(1);
        startDate.setValue(date);
        predictedEndDate.setValue(date.plusYears(1));
    }

    @FXML
    private void deleteFired() {
        if(WindowsUtil.getInstance().showWarningDialog("Delete Education History", "Are you sure you want to delete the selected education history row?", null)) {
            studentsRepository.deleteEducationHistory(selected.get().getId());
            refreshEducationTable();
        }
    }

    private EducationDto getDefaultDto() {
        EducationDto dto = studentsRepository.getLastYearEducationLog(selected.get().getId());
        return dto != null ? dto : new EducationDto();
    }
}
