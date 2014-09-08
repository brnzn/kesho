package com.kesho.datamart;

import com.kesho.datamart.ui.WindowsUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class KeshoApp extends Application {
	public KeshoApp() {
	}

	@Override
	public void start(Stage primaryStage) {
        //setUserAgentStylesheet(STYLESHEET_CASPIAN);
		primaryStage.setTitle("Kesho StS");

		WindowsUtil.getInstance().showRoot(primaryStage);
	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}
}
