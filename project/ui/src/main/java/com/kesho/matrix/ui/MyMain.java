package com.kesho.matrix.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kesho.matrix.repository.StudentsRepository;

public class MyMain extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ClassPathXmlApplicationContext ctx;

	public MyMain() {
	}

	@Override
	public void start(Stage primaryStage) {
		ctx = new ClassPathXmlApplicationContext("repository-context.xml");

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");

		WindowsUtil.getInstance().setPrimaryStage(primaryStage);
		
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
					MyMain.class.getResource("view/StudentOverview.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout.setCenter(overviewPage);

			PersonOverviewController controller = loader.getController();
			StudentsRepository repo = ctx.getBean(StudentsRepository.class);
			controller.setRepo(repo);
			//controller.setMainApp(this);
		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
