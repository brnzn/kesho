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
import com.kesho.datamart.ui.util.Event;
import com.kesho.datamart.ui.util.SystemEventListener;
import com.kesho.datamart.ui.util.Util;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class EducationDetailsController  {
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

    @Inject
    private InstitutionRepository institutionRepository;
    @Inject
    private StudentsRepository studentsRepository;
    @Inject
    private Selectable<StudentDto> selectedStudent;


    public EducationDetailsController() {
        WindowsUtil.getInstance().autowire(this);
        calendar.setDateTextWidth(Double.valueOf(150));
    }

    private void add() {
        EducationDto dto = new EducationDto();
        boolean isOK = WindowsUtil.getInstance().educationForm(dto);

        if(isOK) {
            studentsRepository.addEducationHistory(dto.withStudentId(selectedStudent.getSelectedItem().getId()));
            refreshEducationTable();
            updateEducationForm(educationTable.getSelectionModel().getSelectedItem());
        }
    }

    private void refreshEducationTable() {
        List<EducationDto> dtos = studentsRepository.getEducationHistory(selectedStudent.getSelectedItem().getId());
        educationModel.clear();
        educationModel.addAll(dtos);
        int selectedIndex = educationTable.getSelectionModel().getSelectedIndex();
        educationTable.setItems(null);
        educationTable.layout();
        educationTable.setItems(educationModel);
        // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
        educationTable.getSelectionModel().select(selectedIndex);
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
        WindowsUtil.getInstance().getEventBus().registerListener(Event.STUDENT_SELECTED, new SystemEventListener() {
            @Override
            public void handle() {
                if (educationTab.isSelected()) {
                    refreshEducationTable();
                }
            }
        });


        educationTab.setOnSelectionChanged(new EventHandler<javafx.event.Event>() {
            @Override
            public void handle(javafx.event.Event event) {
                if (educationTab.isSelected()) {
                    loadInstitutions();
                    refreshEducationTable();
                }
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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                WindowsUtil.getInstance().getControllers().detailsController().registerNewChangeListener("educationTab", new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        add();
                    }
                });
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
}
