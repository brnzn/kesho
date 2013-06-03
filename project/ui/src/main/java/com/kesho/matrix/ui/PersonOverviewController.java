package com.kesho.matrix.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
	private TableColumn<Person, String> lastNameColumn;

	@FXML
	private TextField firstNameLabel;
	@FXML
	private TextField lastNameLabel;


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
			personData.add(new Person(student.getFirstName(), student.getFirstName()));
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
		personData.add(new Person("hello", "dfgsdfgg"));
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
		lastNameColumn.setCellValueFactory(
				new PropertyValueFactory<Person, String>("lastName"));
		
		personTable.setItems(personData);
		
	}

    public void saveIssueFired(ActionEvent event) {
    	System.out.println("!!!!!!!!!!!!!" + firstNameLabel.getText() + " " + lastNameLabel.getText());
    	firstNameLabel.getText();
    	
    	Student s = new Student();
    	s.setFirstName(firstNameLabel.getText());
    	s = repo.save(s);
    	
    	personData.add(new Person(s.getFirstName(), String.valueOf(s.getId())));
//        final ObservableIssue ref = getSelectedIssue();
//        final Issue edited = new DetailsData();
//        SaveState saveState = computeSaveState(edited, ref);
//        if (saveState == SaveState.UNSAVED) {
//            model.saveIssue(ref.getId(), edited.getStatus(),
//                    edited.getSynopsis(), edited.getDescription());
//        }
//        // We refresh the content of the table because synopsis and/or description
//        // are likely to have been modified by the user.
//        int selectedRowIndex = table.getSelectionModel().getSelectedIndex();
//        table.getItems().clear();
//        displayedIssues = model.getIssueIds(getSelectedProject());
//        for (String id : displayedIssues) {
//            final ObservableIssue issue = model.getIssue(id);
//            table.getItems().add(issue);
//        }
//        table.getSelectionModel().select(selectedRowIndex);
//
//        updateSaveIssueButtonState();
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