package com.kesho.datamart.ui;

import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.controller.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

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

    public boolean studentForm(StudentDto dto) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/NewStudentForm.fxml"));

        try {
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Education Log");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(WindowsUtil.class.getResource("/style/calendar_styles.css").toExternalForm());

            dialogStage.setScene(scene);
            // Set the person into the controller
            StudentDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDto(dto);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            if(controller.isOkClicked()) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controllers.detailsController().refresh();
                    }
                });
            }
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean educationForm(EducationDto dto) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/NewEducationForm.fxml"));
        EducationDialogController controller = WindowsUtil.getInstance().controllers.getEducationDialogController();

        loader.setController(controller);

        try {
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Education Log");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            scene.getStylesheets().add(WindowsUtil.class.getResource("/style/calendar_styles.css").toExternalForm());

            dialogStage.setScene(scene);

            // Set the person into the controller
            controller.setDialogStage(dialogStage);
            controller.setDto(dto);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return controller.isOkClicked();

    }

    public void autowire(Object o) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(o);

    }
    public void institutionsForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/InstitutionForm.fxml"));
        InstitutionController controller = controllers.getInstitutionController();
        loader.setController(controller);
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Institutions List");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller
        controller.setDialogStage(dialogStage);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }

    //TODO: demo code. delete once connected to db
    public class Controllers {
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

        public EducationDialogController getEducationDialogController() {
            return applicationContext.getBean(EducationDialogController.class);
        }

        public InstitutionController getInstitutionController() {
            return applicationContext.getBean(InstitutionController.class);
        }

        public DetailsController detailsController() {
            return applicationContext.getBean(DetailsController.class);
        }
    }

}
