package com.kesho.crm.ui;

import java.io.IOException;

import com.kesho.crm.dto.StudentDto;
import com.kesho.crm.ui.controller.NewStudentController;
import com.kesho.crm.ui.controller.PersonEditDialogController;
import com.kesho.crm.ui.controller.RootController;
import com.kesho.crm.ui.controller.StudentsController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WindowsUtil {

	private static WindowsUtil instance = new WindowsUtil();
	private Stage primaryStage;
    private Controllers controllers = new Controllers();

    private WindowsUtil(){}
	
	public static WindowsUtil getInstance() {
		return instance;
	}

    //TODO: delete
    public Controllers getControllers() {
        return controllers;
    }
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

//    public void showNewStudentDetails(ObservableList<StudentDto> studentsModel) {
//        try {
//            BorderPane pane = (BorderPane)primaryStage.getScene().getRoot().lookup("#contentLayout");
//            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Student.fxml"));
//            AnchorPane page = (AnchorPane) loader.load();
//            NewStudentController controller = loader.getController();
//            pane.setCenter(page);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }

    public void showStudentsTable() throws IOException {
        BorderPane pane = (BorderPane) getRoot().lookup("#contentLayout");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/StudentsTable.fxml"));
        if(controllers.getStudentsController() != null) {
            loader.setController(controllers.getStudentsController());
        } else {
            StudentsController sc = new StudentsController();
            loader.setController(sc);
            WindowsUtil.getInstance().getControllers().setStudentsController(sc);
        }

        AnchorPane page = (AnchorPane) loader.load();
        controllers.getRootController().setTtile("Students List");
        pane.setCenter(page);
        primaryStage.sizeToScene();

    }

    public void showNewStudentDetails() {
        try {
            BorderPane pane = (BorderPane)getRoot().lookup("#contentLayout");
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Student.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            controllers.getRootController().setTtile("New Student");
            pane.setCenter(page);
            primaryStage.sizeToScene();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private Parent getRoot() {
        return primaryStage.getScene().getRoot();
    }



//	public boolean showPersonEditDialog(StudentDto person) {
//		try {
//			// Load the fxml file and create a new stage for the popup
//			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/PersonEditDialog.fxml"));
//			AnchorPane page = (AnchorPane) loader.load();
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Edit StudentDto");
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.initOwner(primaryStage);
//			Scene scene = new Scene(page);
//			dialogStage.setScene(scene);
//
//			// Set the person into the controller
//			PersonEditDialogController controller = loader.getController();
//			controller.setDialogStage(dialogStage);
//			controller.setPerson(person);
//
//			// Show the dialog and wait until the user closes it
//			dialogStage.showAndWait();
//
//			return controller.isOkClicked();
//
//		} catch (IOException e) {
//			// Exception gets thrown if the fxml file could not be loaded
//			e.printStackTrace();
//			return false;
//		}
//	}

    //TODO: demo code. delete once connected to db
    public class Controllers {
        private StudentsController studentsController;
        private RootController rootController;

        public StudentsController getStudentsController() {
            return studentsController;
        }


        public void setStudentsController(StudentsController studentsController) {
            this.studentsController = studentsController;
        }

        public void setRootController(RootController rootController) {
            this.rootController = rootController;
        }

        public RootController getRootController() {
            return rootController;
        }
    }

}
