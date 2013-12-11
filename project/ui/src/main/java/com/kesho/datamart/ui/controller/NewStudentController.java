package com.kesho.datamart.ui.controller;

import calendar.FXCalendar;
import com.kesho.datamart.domain.Gender;
import com.kesho.datamart.domain.LeaverStatus;
import com.kesho.datamart.domain.LevelOfSupport;
import com.kesho.datamart.domain.SponsorshipStatus;
import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.InstitutionDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.StudentsRepository;
import com.kesho.datamart.ui.util.Util;
import custom.NumericTextField;
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
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 10/19/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class NewStudentController {
    @FXML
    private TextField firstName;
    @FXML
    private TextField surname;
    @FXML
    private ComboBox<Gender> gender;
    @FXML
    private NumericTextField yearOfBirth;
    @FXML
    private TextField contactNumber;
    @FXML
    private TextField homeLocation;
    @FXML
    private ToggleGroup currentStudent;
    @FXML
    private ToggleGroup hasDisability;

    @FXML
    private ToggleGroup sponsored;
    @FXML
    private HBox dateControlBox;
    private final FXCalendar calendar = new FXCalendar();

    @FXML
    private TabPane studentTab;

    @FXML
    private ComboBox<SponsorshipStatus> sponsorshipStatus;
    @FXML
    private TextField email;
    @FXML
    private TextField facebook;
    @FXML
    private ComboBox<LevelOfSupport> levelOfSupport;
    @FXML
    private ToggleGroup topupNeeded;
    @FXML
    private NumericTextField shortfall;
    @FXML
    private NumericTextField alumniNumber;
    @FXML
    private ComboBox<LeaverStatus> leaverStatus;

    //education
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
    private TextField educationDate;
    @FXML
    private TextField institutionName;
    @FXML
    private TextField educationYear;
    @FXML
    private TextField course;

    @FXML
    private ComboBox<String> educationalStatus;
    @FXML
    private ComboBox<String> secondaryStatus1;
    @FXML
    private ComboBox<String> secondaryStatus2;

    private ObservableList<EducationDto> educationModel = FXCollections.observableArrayList();

    private EducationDto selected;

    @FXML
    private TableView<EducationDto> educationTable;

    private Long currentId;

    @Inject
    private StudentsRepository studentsRepository;

    public NewStudentController() {
        calendar.setDateTextWidth(Double.valueOf(100));
    }

    public void setSelectedStudent(StudentDto student) {
        if(student == null) {
            return;
        }

        initializeForm(student);
    }

    @FXML
    private void initialize() {
        selected = null;
        educationModel.clear();
        educationTable.setItems(educationModel);

        dateControlBox.getChildren().add(calendar);
        currentId = null;

        institutionCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("institutionName"));
        educationDateCol.setCellValueFactory(new PropertyValueFactory<EducationDto, LocalDate>("date"));
        educationLevelCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("educationalStatus"));
        yearCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("year"));
        courseCol.setCellValueFactory(new PropertyValueFactory<EducationDto, String>("course"));

        Util.initializeComboBoxValues(gender, EnumSet.allOf(Gender.class));
        Util.initializeComboBoxValues(leaverStatus, EnumSet.allOf(LeaverStatus.class));
        Util.initializeComboBoxValues(sponsorshipStatus, EnumSet.allOf(SponsorshipStatus.class));
        Util.initializeComboBoxValues(levelOfSupport, EnumSet.allOf(LevelOfSupport.class));

        initializeYesNoGroup(currentStudent, hasDisability, sponsored, topupNeeded);

        studentTab.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab tab2) {
                if("educationTab".equals(tab2.getId())) {
                    loadEducationHistory();
                }
            }
        });

        educationTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EducationDto>() {
            @Override
            public void changed(ObservableValue<? extends EducationDto> observable,
                                EducationDto oldValue, EducationDto newValue) {
                updateEducationForm(newValue);
            }
        });
    }


    private void loadEducationHistory() {
        new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        educationModel.addAll(studentsRepository.getEducationHistory(currentId));
                        return null;
                    }
                };
            }
        }.start();
    }

    private void updateEducationForm(EducationDto dto) {
        if(dto == null)
            return;

        selected = dto;
        educationYear.setText(Util.safeToStringValue(dto.getYear(), ""));
        institutionName.setText(dto.getInstitutionName());
        educationDate.setText(dto.getDate().toString());
        course.setText(dto.getCourse());
    }

    @FXML
    private void addEducation() throws IOException {
        EducationDto dto = new EducationDto();
        boolean isOK = WindowsUtil.getInstance().educationForm(dto);

        if(isOK) {
            studentsRepository.addEducationHistory(dto.withStudentId(currentId));
            educationModel.clear();
            educationModel.addAll(studentsRepository.getEducationHistory(currentId));
            refreshEducationTable();
            updateEducationForm(educationTable.getSelectionModel().getSelectedItem());
        }
    }

    private void refreshEducationTable() {
        List<EducationDto> dtos = studentsRepository.getEducationHistory(currentId);
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
        studentsRepository.save(buildDto());

        try {
            WindowsUtil.getInstance().showStudentsTable();
        } catch (IOException e) {
            //TODO: error handling
            e.printStackTrace();
        }
    }

    private StudentDto buildDto() {
        StudentDto student = new StudentDto();
        student.withId(currentId)
                .withName(firstName.getText())
                .withFamilyName(surname.getText())
                .withMobileNumber(contactNumber.getText())
                .withHomeLocation(homeLocation.getText())
                .activeStudent((Boolean) currentStudent.getSelectedToggle().getUserData())
                .withHasDisability((Boolean) hasDisability.getSelectedToggle().getUserData())
                .sponsored((Boolean)sponsored.getSelectedToggle().getUserData())
                .withEmail(email.getText())
                .withFacebookAddress(facebook.getText())
                .withTopupNeeded((Boolean)topupNeeded.getSelectedToggle().getUserData())
                .withLeaverStatus(leaverStatus.getSelectionModel().getSelectedItem())
                .withSponsorStatus(sponsorshipStatus.getSelectionModel().getSelectedItem())
                .withLevelOfSupport(levelOfSupport.getSelectionModel().getSelectedItem())
                .withGender(gender.getSelectionModel().getSelectedItem());

        if(StringUtils.isNotBlank(shortfall.getText())) {
            student.withShortfall(Integer.valueOf(shortfall.getText()));
        }

        if(StringUtils.isNotBlank(alumniNumber.getText())) {
            student.withAlumniNumber(Integer.valueOf(alumniNumber.getText()));
        }

        if(calendar.getValue() != null) {
            student.withStartDate(LocalDate.fromDateFields(calendar.getValue()));
        }

        if(StringUtils.isNotBlank(yearOfBirth.getText())) {
            student.withYearOfBirth(Integer.valueOf(yearOfBirth.getText()));
        }

        return student;
    }

    @FXML
    private void cancel() throws IOException {
        WindowsUtil.getInstance().showStudentsTable();
    }

    private void initializeForm(StudentDto student) {
        if(student.getStartDate() != null) {
            calendar.setValue(student.getStartDate().toDate());//new FXCalendarUtility().convertStringtoDate("02/02/2001");
        }

        currentId = student.getId();
        firstName.setText(student.getName());
        surname.setText(student.getSurname());
        gender.getSelectionModel().select(student.getGender());

        yearOfBirth.setText(safeToStringValue(student.getYearOfBirth(), null));

        contactNumber.setText(student.getMobileNumber());
        homeLocation.setText(student.getHomeLocation());

        setState(currentStudent, student.isActiveStudent());
        setState(hasDisability, student.hasDisability());
        setState(sponsored, student.isSponsored());
        setState(topupNeeded, student.isTopupNeeded());

        sponsorshipStatus.getSelectionModel().select(student.getSponsorshipStatus());
        email.setText(student.getEmail());
        facebook.setText(student.getFacebookAddress());

        levelOfSupport.getSelectionModel().select(student.getLevelOfSupport());

        shortfall.setText(safeToStringValue(student.getShortfall(), null));

        alumniNumber.setText(safeToStringValue(student.getAlumniNumber(), null));

        leaverStatus.getSelectionModel().select(student.getLeaverStatus());
    }

    public String safeToStringValue(Object value, String defaultValue) {
        return value != null ? value.toString() : (defaultValue != null ? defaultValue : "");
    }

//    private <T extends Enum> void initializeComboBoxValues(ComboBox<T> target, EnumSet source) {
//        target.getItems().clear();
//        target.getItems().addAll(source);
//    }

    private void initializeYesNoGroup(ToggleGroup ...groups) {
        for (ToggleGroup group:groups) {
            group.getToggles().get(0).setUserData(Boolean.TRUE);
            group.getToggles().get(1).setUserData(Boolean.FALSE);
            group.getToggles().get(0).setSelected(true);
        }
    }

    private void setState(ToggleGroup group, boolean value) {
        if(value) {
            group.getToggles().get(0).setSelected(true);
        } else {
            group.getToggles().get(1).setSelected(true);
        }
    }
}
