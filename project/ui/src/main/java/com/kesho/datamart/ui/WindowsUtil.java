package com.kesho.datamart.ui;

import java.io.IOException;

import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.controller.NewStudentController;
import com.kesho.datamart.ui.controller.RootController;
import com.kesho.datamart.ui.controller.StudentsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WindowsUtil {

	private static WindowsUtil instance = new WindowsUtil();
	private Stage primaryStage;
    private Controllers controllers = new Controllers();
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/ui-context.xml");

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

    public void showStudentsTable() throws IOException {
        BorderPane pane = (BorderPane) getRoot().lookup("#contentLayout");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/StudentsTable.fxml"));
        loader.setController(controllers.getStudentsController());

        //controllers.getStudentsController().initPager((VBox)getRoot().lookup("#tableContainer"));
        AnchorPane page = (AnchorPane) loader.load();
        controllers.getRootController().setTtile("Students List");
        pane.setCenter(page);
        primaryStage.sizeToScene();
    }

    public void showNewStudentDetails(StudentDto student) {
        try {
            BorderPane pane = (BorderPane)getRoot().lookup("#contentLayout");
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Student.fxml"));
            NewStudentController controller = controllers.getNewStudentController();
            loader.setController(controller);

            AnchorPane page = (AnchorPane) loader.load();
            controller.setSelectedStudent(student);
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
            return applicationContext.getBean(StudentsController.class);
        }


        public void setRootController(RootController rootController) {
            this.rootController = rootController;
        }

        public RootController getRootController() {
            return rootController;
        }

        public NewStudentController getNewStudentController() {
            return applicationContext.getBean(NewStudentController.class);
        }
    }

}
