package com.kesho.datamart.ui;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.controller.*;
import com.kesho.datamart.ui.util.EventBus;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Dialogs;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class WindowsUtil {

	private static WindowsUtil instance = new WindowsUtil();
    private BorderPane rootLayout;
	private Stage primaryStage;
    private Controllers controllers = new Controllers();
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/ui-context.xml");

    private WindowsUtil(){}
	
	public static WindowsUtil getInstance() {
		return instance;
	}

    public void showErrorDialog(String title, String masthead, String msg) {
        Dialogs.create()
                .owner( primaryStage)
                .title(title)
                .masthead(masthead)
                .message(msg)
                .showError();

//        Dialogs.showErrorDialog(primaryStage, msg, "Please correct invalid fields", "Invalid Fields");
    }
    public EventBus getEventBus() {
        return applicationContext.getBean(EventBus.class);
    }

    /**
     * @deprecated
     * @return
     */
    //TODO: delete
    public Controllers getControllers() {
        return controllers;
    }

    public boolean showWarningDialog(String title, String head, String message) {
        //TODO: fix
//        Dialogs.DialogResponse resp = Dialogs.showWarningDialog(primaryStage, message, head, title, Dialogs.DialogOptions.YES_NO);
//        return resp == Dialogs.DialogResponse.YES;
        return false;
    }

    public void sponsors() {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Sponsors.fxml"));
            controllers.getRootController().setTtile("Sponsors");
            loader.setController(WindowsUtil.getInstance().getControllers().sponsorsController());

            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);
            primaryStage.sizeToScene();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public void students() {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Students.fxml"));
            controllers.getRootController().setTtile("Students");
            StudentsController sc = WindowsUtil.getInstance().getControllers().studentsController();
            loader.setController(sc);

            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);
            primaryStage.sizeToScene();
            sc.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void students(Long studentId) {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Students.fxml"));
            controllers.getRootController().setTtile("Students");
            StudentsController sc = WindowsUtil.getInstance().getControllers().studentsController();
            loader.setController(sc);

            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);
            primaryStage.sizeToScene();
            sc.init(studentId);
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

    private Parent getRoot() {
        return primaryStage.getScene().getRoot();
    }


    public void autowire(Object o) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(o);

    }

    public StudentDto studentSelector() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/AutoCompleteSelector.fxml"));
        StudentSelectorController controller = controllers.getStudentSelector();
        loader.setController(controller);
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Student Selector");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller
            controller.setDialogStage(dialogStage);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getSelected();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    public FamilyDto familySelector() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/AutoCompleteSelector.fxml"));
        FamilySelectorController controller = controllers.getFamilySelector();
        loader.setController(controller);
        AnchorPane page = null;
        try {
            page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Family Form");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller
            controller.setDialogStage(dialogStage);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getSelected();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    public void familyForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/FamilyForm.fxml"));
        FamilyDialogController controller = controllers.getFamilyDialogController();
        loader.setController(controller);
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Family Form");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller
        controller.setDialogStage(dialogStage);
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
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

    public class Controllers {
        private RootController rootController;

        public void setRootController(RootController rootController) {
            this.rootController = rootController;
        }

        public RootController getRootController() {
            return rootController;
        }

        public InstitutionController getInstitutionController() {
            return applicationContext.getBean(InstitutionController.class);
        }

        public StudentsController studentsController() {
            return applicationContext.getBean(StudentsController.class);
        }

        public FamilyDialogController getFamilyDialogController() {
            return applicationContext.getBean(FamilyDialogController.class);
        }

        public FamilySelectorController getFamilySelector() {
            return applicationContext.getBean(FamilySelectorController.class);
        }

        public SponsorsController sponsorsController() {
            return applicationContext.getBean(SponsorsController.class);
        }

        public StudentSelectorController getStudentSelector() {
            return applicationContext.getBean(StudentSelectorController.class);
        }
    }

}
