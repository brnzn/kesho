package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.EducationStatus;
import com.kesho.datamart.domain.SubEducationStatus;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.InstitutionDto;
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
public class EducationDetailsController {
    @FXML
    private DatePicker startDate;
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

    @Inject
    private StudentsController parentController;

    private SimpleObjectProperty<EducationDto> selected = new SimpleObjectProperty<>();
    private Map<String, Node> validationFields = new HashMap<>();

    public EducationDetailsController() {
        WindowsUtil.getInstance().autowire(this);
    }

    void refreshEducationTable() {
        educationModel.clear();
        if(parentController.getSelectedItem() != null) {
            List<EducationDto> dtos = studentsRepository.getEducationHistory(parentController.getSelectedItem().getId());
            educationModel.addAll(dtos);
            int selectedIndex = educationTable.getSelectionModel().getSelectedIndex();
            educationTable.setItems(null);
            educationTable.layout();
            educationTable.setItems(educationModel);
            // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
            educationTable.getSelectionModel().select(selectedIndex);
        }
    }


    @FXML
    private void save() {
        EducationDto dto = selected.get();
        dto.withStudentId(parentController.getSelectedItem().getId()).
            withYear(educationYear.getText())
            .withEducationalStatus(educationalStatus.getSelectionModel().getSelectedItem())
            .withCourse(course.getText())
            .withSecondaryStatus1(secondaryStatus1.getSelectionModel().getSelectedItem())
            .withSecondaryStatus2(secondaryStatus2.getSelectionModel().getSelectedItem())
            .withInstitution(institutions.getSelectionModel().getSelectedItem())
            .withComments(comments.getText())
            .withEducationDate(Util.toJodaDate(startDate.getValue()));

        if (isInputValid(dto)) {
            studentsRepository.save(dto);
            refreshEducationTable();
        }
    }

    private boolean isInputValid(EducationDto dto) {
        String validation = FormValidator.validate(dto, getFields());

        //if()

        if(StringUtils.isNotBlank(validation)) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save Education details", validation);
            return false;
        } else {
            return true;
        }

    }

    private Map<String, Node> getFields() {
        if(validationFields.isEmpty()) {
            validationFields.put("institution", institutions);
            validationFields.put("date", startDate);
            validationFields.put("year", educationYear);
            validationFields.put("course", course);
            validationFields.put("educationalStatus", educationalStatus);
        }

        return validationFields;
    }

    void loadInstitutions() {
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
//    private void loadEducationHistory() {
//        educationModel.clear();
//        educationTable.setItems(educationModel);
//
//        new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        System.out.println("== current id:" + selectedStudent.getSelectedItem().getId());
//                        educationModel.addAll(studentsRepository.getEducationHistory(selectedStudent.getSelectedItem().getId()));
//                        return null;
//                    }
//                };
//            }
//        }.start();
//    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        selected.addListener(new ChangeListener<EducationDto>() {
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

        educationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EducationDto>() {
            @Override
            public void changed(ObservableValue<? extends EducationDto> observable,
                                EducationDto oldValue, EducationDto newValue) {
                updateEducationForm(newValue);
            }
        });

        Util.initializeComboBoxValues(educationalStatus, EnumSet.allOf(EducationStatus.class));
        educationalStatus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EducationStatus>() {
            @Override
            public void changed(ObservableValue<? extends EducationStatus> observableValue, EducationStatus s, EducationStatus s2) {
                secondaryStatus2.getItems().clear();
                secondaryStatus1.getItems().clear();
                secondaryStatus2.getSelectionModel().clearSelection();
                secondaryStatus1.getSelectionModel().clearSelection();

                if(s2 != null) {
                    secondaryStatus1.getItems().addAll(s2.getChildren());
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
        selected.set(dto);
        if(dto == null) {
            resetForm();
            return;
        }

        educationYear.setText(Util.safeToStringValue(dto.getYear(), ""));
        course.setText(dto.getCourse());
        educationalStatus.getSelectionModel().select(dto.getEducationalStatus());
        startDate.valueProperty().setValue(Util.toJavaDate(dto.getDate()));
        institutions.getSelectionModel().select(dto.getInstitution());
        secondaryStatus1.getSelectionModel().select(dto.getSecondaryEducationStatus1());
        secondaryStatus2.getSelectionModel().select(dto.getSecondaryEducationStatus2());
        comments.setText(dto.getComments());

    }


    private void resetForm() {
        educationYear.clear();
        course.clear();
        educationalStatus.getSelectionModel().clearSelection();
        educationalStatus.valueProperty().setValue(null);
        startDate.setValue(null);
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
        selected.set(new EducationDto());
    }

    @FXML
    private void deleteFired() {
        if(WindowsUtil.getInstance().showWarningDialog("Delete Education History", "Are you sure you want to delete the selected education history row?", null)) {
            studentsRepository.deleteEducationHistory(selected.get().getId());
            refreshEducationTable();
        }
    }
}
