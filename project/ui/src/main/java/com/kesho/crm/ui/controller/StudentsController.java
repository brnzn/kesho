package com.kesho.crm.ui.controller;

import java.util.Date;

import com.kesho.crm.dto.StudentDto;
import com.kesho.crm.ui.WindowsUtil;
import com.kesho.crm.ui.util.CalendarUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.kesho.matrix.repository.StudentsRepository;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * The controller for the overview with address table and details view.
 * 
 * @author Marco Jakob
 */
public class StudentsController {
	private StudentsRepository repo;
	
	@FXML
	private TableView<StudentDto> studentsTable;
	@FXML
	private TableColumn<StudentDto, String> firstNameColumn;
	@FXML
	private TableColumn<StudentDto, String> familyNameColumn;

	@FXML
	private Label nameLbl;
	@FXML
	private Label familyNameLbl;
    @FXML
    private Label genderLbl;
    @FXML
    private Label yearOfBirthLbl;
    @FXML
    private Label contactNumLbl;
    @FXML
    private Label homeLocationLbl;
    @FXML
    private Label currentStudentLbl;
    @FXML
    private Label hasDisabilityLbl;
    @FXML
    private Label sponsoredLbl;
    @FXML
    private Label startDateLbl;

    @FXML
    private Button newButton;
    @FXML
    private Button saveButton;

	private ObservableList<StudentDto> studentsModel = FXCollections.observableArrayList();
	
//	public void setRepo(StudentsRepository repo) {
//		this.repo = repo;
//		List<Student> students = repo.findAll();
//		for(Student student:students) {
//			studentsModel.add(new StudentDto().withName(student.getFirstName()).withId(student.getId()));
//		}
//	}
	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public StudentsController() {
        System.out.println("=======================");
    }

    //TODO: demo code
    public ObservableList<StudentDto> getDataModel() {
        return studentsModel;
    }
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the students table
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("name"));
        familyNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("familyName"));

		studentsTable.setItems(studentsModel);

//		//update form when selecting table row
		studentsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StudentDto>() {
			@Override
			public void changed(ObservableValue<? extends StudentDto> observable,
					StudentDto oldValue, StudentDto newValue) {
                showStudentDetails(newValue);
			}
		});

        studentsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2 && studentsTable.getSelectionModel().getSelectedItem() != null) {
                        //TODO:pass selected student to initialise form
                        WindowsUtil.getInstance().showNewStudentDetails();
                    }
                }
            }
        });
	}

	private void showStudentDetails(StudentDto person) {
		if (person != null) {
			nameLbl.setText(person.getName());
			familyNameLbl.setText(person.getFamilyName());
            genderLbl.setText(person.getGender());
            yearOfBirthLbl.setText(person.getYearOfBirth());
            contactNumLbl.setText(person.getMobileNumber());
            homeLocationLbl.setText(person.getHomeLocation());
            currentStudentLbl.setText(String.valueOf(person.isActiveStudent()));
            hasDisabilityLbl.setText(String.valueOf(person.isHasDisability()));
            sponsoredLbl.setText(String.valueOf(person.isSponsored()));
            startDateLbl.setText(CalendarUtil.format(person.getStartDate()));
		}
	}

    //TODO: demo code. delete
    public void add(StudentDto student) {
        studentsModel.add(student);
        //To change body of created methods use File | Settings | File Templates.
    }

//	@FXML
//	private void handleDeletePerson() {
//		int selectedIndex = studentsTable.getSelectionModel().getSelectedIndex();
//		if (selectedIndex >= 0) {
//			studentsTable.getItems().remove(selectedIndex);
//		} else {
//			// Nothing selected
//			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
//					"Please select a person in the table.",
//					"No StudentDto Selected", "No Selection");
//		}
//	}
	
//	@FXML
//	private void handleEditPerson() {
//		StudentDto selectedPerson = studentsTable.getSelectionModel().getSelectedItem();
//		if (selectedPerson != null) {
//			boolean okClicked = WindowsUtil.getInstance().showPersonEditDialog(selectedPerson);
//			if (okClicked) {
//				Student s = new Student();
////				s.setId(selectedPerson.getPersonId());
////				s.setFirstName(selectedPerson.getFirstName());
//				repo.save(s);
//				refreshPersonTable();
//				showPersonDetails(selectedPerson);
//			}
//
//		} else {
//			// Nothing selected
//			Dialogs.showWarningDialog(WindowsUtil.getInstance().getPrimaryStage(),
//					"Please select a person in the table.",
//					"No StudentDto Selected", "No Selection");
//		}
//	}
//
//	private void refreshPersonTable() {
//		int selectedIndex = studentsTable.getSelectionModel().getSelectedIndex();
//		studentsTable.setItems(null);
//		studentsTable.layout();
//		studentsTable.setItems(studentsModel);
//		// Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
//		studentsTable.getSelectionModel().select(selectedIndex);
//	}
	
//	@FXML
//    private void saveIssueFired(ActionEvent event) {
//    	System.out.println("!!!!!!!!!!!!!" + firstNameLabel.getText() + " " + lastNameLabel.getText());
//    	firstNameLabel.getText();
//    	
//    	Student s = new Student();
//    	s.setFirstName(firstNameLabel.getText());
//    	s = repo.save(s);
//    	
//    	studentsModel.add(new StudentDto(s.getFirstName(), String.valueOf(s.getId())));
//    }
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
//	public void setMainApp(KeshoApp mainApp) {
//		this.mainApp = mainApp;
//
//		// Add observable list data to the table
//		studentsTable.setItems(mainApp.getPersonData());
//	}

}