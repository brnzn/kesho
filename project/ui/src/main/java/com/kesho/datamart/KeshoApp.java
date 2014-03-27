package com.kesho.datamart;

import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.controller.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


public class KeshoApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	public KeshoApp() {
	}

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Kesho StS");

		WindowsUtil.getInstance().setPrimaryStage(primaryStage);
		
		try {
			// Load the root layout from the fxml file
			FXMLLoader loader = new FXMLLoader();
			rootLayout = (BorderPane) loader.load(KeshoApp.class.getResourceAsStream("/view/RootLayout.fxml"));
            WindowsUtil.getInstance().setRootLayout(rootLayout);
            RootController rootController = loader.getController();
            WindowsUtil.getInstance().getControllers().setRootController(rootController);
			Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(KeshoApp.class.getResource("/style/calendar_styles.css").toExternalForm());

            primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}

        WindowsUtil.getInstance().students();
        primaryStage.sizeToScene();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
