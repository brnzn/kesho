package com.test;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kesho.matrix.entity.Student;
import com.kesho.matrix.repository.StudentsRepository;

public class MyMain extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Person> personData = FXCollections.observableArrayList();

	public MyMain() {
	}

	public ObservableList<Person> getPersonData() {
		return personData;
	}

	@Override
	public void start(Stage primaryStage) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("repository-context.xml");
		StudentsRepository repo = ctx.getBean(StudentsRepository.class);

		List<Student> students = repo.findAll();
		for(Student student:students) {
			personData.add(new Person(student.getFirstName(), student.getFirstName()));
		}
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");

		try {
			// Load the root layout from the fxml file
			FXMLLoader loader = new FXMLLoader(
					MyMain.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}

		showPersonOverview();
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	// public Stage getPrimaryStage() {
	// return primaryStage;
	// }

	/**
	 * Shows the person overview scene.
	 */
	public void showPersonOverview() {
		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					MyMain.class.getResource("view/PersonOverview.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout.setCenter(overviewPage);

			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
