package com.kesho.crm.ui.controller;

import java.util.List;

import com.kesho.crm.dto.StudentDto;
import com.kesho.crm.ui.WindowsUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.kesho.matrix.entity.Student;
import com.kesho.matrix.repository.StudentsRepository;

/**
 * The controller for the overview with address table and details view.
 * 
 * @author Marco Jakob
 */
public class StudentsController {
	private StudentsRepository repo;
	
	@FXML
	private TableView<StudentDto> personTable;
	@FXML
	private TableColumn<StudentDto, String> firstNameColumn;
	@FXML
	private TableColumn<StudentDto, String> familyNameColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label familyNameLabel;


    @FXML
    private Button newButton;
    @FXML
    private Button saveButton;
    
	// Reference to the main application
	//private KeshoApp mainApp;
	private ObservableList<StudentDto> personData = FXCollections.observableArrayList();
	
	public void setRepo(StudentsRepository repo) {
		this.repo = repo;
		List<Student> students = repo.findAll();
		for(Student student:students) {
			personData.add(new StudentDto().withName(student.getFirstName()).withId(student.getId()));
		}		

		
	}
	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public StudentsController() {
	}

//	@FXML
//	private void add() {
//		personData.add(new StudentDto().withName("new name").withFamilyName("new family").withId(Long.MAX_VALUE));
//	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table
		firstNameColumn.setCellValueFactory(
				new PropertyValueFactory<StudentDto, String>("name"));
        familyNameColumn.setCellValueFactory(
				new PropertyValueFactory<StudentDto, String>("familyName"));
//
		personTable.setItems(personData);
//
//		//update form when selecting table row
		personTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StudentDto>() {
			@Override
			public void changed(ObservableValue<? extends StudentDto> observable,
					StudentDto oldValue, StudentDto newValue) {
                showPersonDetails(newValue);
			}
		});

        personData.add(new StudentDto().withName("name").withFamilyName("familyName").withId(1L));
	}

	private void showPersonDetails(StudentDto person) {
		if (person != null) {
			nameLabel.setText(person.getName());
			familyNameLabel.setText(String.valueOf(person.getFamilyName()));
//		} else {
//			firstNameLabel.setText("");
//			idLabel.setText("");
		}
	}
	
	@FXML
	private void handleNewPerson() {
		StudentDto tempPerson = new StudentDto();

        boolean okClicked = WindowsUtil.getInstance().showPersonEditDialog(tempPerson);
        if (okClicked) {
            personData.add(tempPerson);
        }
	}
	
	@FXML
	private void handleDeletePerson() {
//		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
//		if (selectedIndex >= 0) {
//			personTable.getItems().remove(selectedIndex);
//		} else {
//			// Nothing selected
//			Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
//					"Please select a person in the table.",
//					"No StudentDto Selected", "No Selection");
//		}
	}
	
	@FXML
	private void handleEditPerson() {
		StudentDto selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = WindowsUtil.getInstance().showPersonEditDialog(selectedPerson);
			if (okClicked) {
				Student s = new Student();
//				s.setId(selectedPerson.getPersonId());
//				s.setFirstName(selectedPerson.getFirstName());
				repo.save(s);
				refreshPersonTable();
				showPersonDetails(selectedPerson);
			}
			
		} else {
			// Nothing selected
			Dialogs.showWarningDialog(WindowsUtil.getInstance().getPrimaryStage(),
					"Please select a person in the table.",
					"No StudentDto Selected", "No Selection");
		}
	}	
	
	private void refreshPersonTable() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		personTable.setItems(null);
		personTable.layout();
		personTable.setItems(personData);
		// Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
		personTable.getSelectionModel().select(selectedIndex);
	}
	
	@FXML
    private void saveIssueFired(ActionEvent event) {
//    	System.out.println("!!!!!!!!!!!!!" + firstNameLabel.getText() + " " + lastNameLabel.getText());
//    	firstNameLabel.getText();
//    	
//    	Student s = new Student();
//    	s.setFirstName(firstNameLabel.getText());
//    	s = repo.save(s);
//    	
//    	personData.add(new StudentDto(s.getFirstName(), String.valueOf(s.getId())));
    }	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
//	public void setMainApp(KeshoApp mainApp) {
//		this.mainApp = mainApp;
//
//		// Add observable list data to the table
//		personTable.setItems(mainApp.getPersonData());
//	}
}