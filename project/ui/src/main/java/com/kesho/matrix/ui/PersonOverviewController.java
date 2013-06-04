package com.kesho.matrix.ui;

import java.io.IOException;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.kesho.matrix.entity.Student;
import com.kesho.matrix.repository.StudentsRepository;

/**
 * The controller for the overview with address table and details view.
 * 
 * @author Marco Jakob
 */
public class PersonOverviewController {
	private StudentsRepository repo;
	
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, Long> personIdColumn;

	@FXML
	private Label firstNameLabel;
	@FXML
	private Label idLabel;


    @FXML
    private Button newButton;
    @FXML
    private Button saveButton;
    
	// Reference to the main application
	//private MyMain mainApp;
	private ObservableList<Person> personData = FXCollections.observableArrayList();
	
	public void setRepo(StudentsRepository repo) {
		this.repo = repo;
		List<Student> students = repo.findAll();
		for(Student student:students) {
			personData.add(new Person(student.getFirstName(), student.getId()));
		}		

		
	}
	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public PersonOverviewController() {
	}

	@FXML
	private void add() {
		personData.add(new Person("hello", Long.MAX_VALUE));
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table
		firstNameColumn.setCellValueFactory(
				new PropertyValueFactory<Person, String>("firstName"));
		personIdColumn.setCellValueFactory(
				new PropertyValueFactory<Person, Long>("personId"));
		
		personTable.setItems(personData);

		//update form when selecting table row
		personTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
			@Override
			public void changed(ObservableValue<? extends Person> observable,
					Person oldValue, Person newValue) {
				showPersonDetails(newValue);
			}
		});
	}

	private void showPersonDetails(Person person) {
		if (person != null) {
			firstNameLabel.setText(person.getFirstName());
			idLabel.setText(String.valueOf(person.getPersonId()));
		} else {
			firstNameLabel.setText("");
			idLabel.setText("");
		}
	}
	
	@FXML
	private void handleNewPerson() {
//		Person tempPerson = new Person();
//		boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
//		if (okClicked) {
//			mainApp.getPersonData().add(tempPerson);
//		}
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
//					"No Person Selected", "No Selection");
//		}
	}
	
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = WindowsUtil.getInstance().showPersonEditDialog(selectedPerson);
			if (okClicked) {
				Student s = new Student();
				s.setId(selectedPerson.getPersonId());
				s.setFirstName(selectedPerson.getFirstName());
				repo.save(s);
				refreshPersonTable();
				showPersonDetails(selectedPerson);
			}
			
		} else {
			// Nothing selected
			Dialogs.showWarningDialog(WindowsUtil.getInstance().getPrimaryStage(),
					"Please select a person in the table.",
					"No Person Selected", "No Selection");
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
//    	personData.add(new Person(s.getFirstName(), String.valueOf(s.getId())));
    }	
	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
//	public void setMainApp(MyMain mainApp) {
//		this.mainApp = mainApp;
//
//		// Add observable list data to the table
//		personTable.setItems(mainApp.getPersonData());
//	}
}