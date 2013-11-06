package com.kesho.crm;

import java.io.IOException;

import com.kesho.crm.ui.controller.NewStudentController;
import com.kesho.crm.ui.controller.RootController;
import com.kesho.crm.ui.controller.StudentsController;
import com.kesho.crm.ui.WindowsUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kesho.matrix.repository.StudentsRepository;

public class KeshoApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
//	private ClassPathXmlApplicationContext ctx;

	public KeshoApp() {
	}

	@Override
	public void start(Stage primaryStage) {
//		ctx = new ClassPathXmlApplicationContext("repository-context.xml");

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Kesho StS");

		WindowsUtil.getInstance().setPrimaryStage(primaryStage);
		
		try {
			// Load the root layout from the fxml file
			FXMLLoader loader = new FXMLLoader();
			rootLayout = (BorderPane) loader.load(KeshoApp.class.getResourceAsStream("/view/newRootLayout.fxml"));
            RootController rootController = loader.getController();
            WindowsUtil.getInstance().getControllers().setRootController(rootController);
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}

        showHomePage();
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
	public void showHomePage() {
		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(KeshoApp.class.getResource("/view/Students.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			rootLayout.setCenter(overviewPage);
            WindowsUtil.getInstance().showStudentsTable();
//            BorderPane pane = (BorderPane)rootLayout.lookup("#contentLayout");
//            FXMLLoader loader1 = new FXMLLoader(this.getClass().getResource("/view/StudentsTable.fxml"));
//            AnchorPane page = (AnchorPane) loader1.load();
//            pane.setCenter(page);

//			StudentsController controller = loader.getController();
//			StudentsRepository repo = ctx.getBean(StudentsRepository.class);
//			controller.setRepo(repo);
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
