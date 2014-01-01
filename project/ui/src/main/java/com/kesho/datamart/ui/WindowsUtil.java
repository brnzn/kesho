package com.kesho.datamart.ui;

import com.kesho.datamart.dto.EducationDto;
import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.controller.*;
import com.kesho.datamart.ui.util.EventBus;
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
    private BorderPane rootLayout;
	private Stage primaryStage;
    private Controllers controllers = new Controllers();
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/ui-context.xml");

    private WindowsUtil(){}
	
	public static WindowsUtil getInstance() {
		return instance;
	}

    public EventBus getEventBus() {
        return applicationContext.getBean(EventBus.class);
    }

    //TODO: delete
    public Controllers getControllers() {
        return controllers;
    }

    public void sponsors() {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/SponsorWithTable.fxml"));

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
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/StudentWithTable.fxml"));

            loader.setController(WindowsUtil.getInstance().getControllers().detailsController());

            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);
            primaryStage.sizeToScene();
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

//    public void showStudentsTable() throws IOException {
//        BorderPane pane = (BorderPane) getRoot().lookup("#contentLayout");
//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/StudentsTable.fxml"));
//        loader.setController(controllers.getStudentsController());
//
//        AnchorPane page = (AnchorPane) loader.load();
//        controllers.getRootController().setTtile("Students List");
//        pane.setCenter(page);
//        primaryStage.sizeToScene();
//    }

//    public void showNewStudentDetails(StudentDto student) {
//        try {
//            BorderPane pane = (BorderPane)getRoot().lookup("#contentLayout");
//            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Student.fxml"));
//            NewStudentController controller = controllers.getNewStudentController();
//            loader.setController(controller);
//
//            AnchorPane page = (AnchorPane) loader.load();
//            controller.setSelectedStudent(student);
//            controllers.getRootController().setTtile("New Student");
//            pane.setCenter(page);
//            primaryStage.sizeToScene();
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }

    private Parent getRoot() {
        return primaryStage.getScene().getRoot();
    }

    public boolean studentForm(StudentDto dto) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/NewStudentForm.fxml"));

        try {
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Student");
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
            dialogStage.setTitle("New Education");
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

    public StudentDto studentSelector() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/studentSelector.fxml"));
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
            scene.getStylesheets().add("/style/AutoFillTextBox.css");
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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/FamilySelector.fxml"));
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
            scene.getStylesheets().add("/style/AutoFillTextBox.css");
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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/NewFamilyForm.fxml"));
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

//    public boolean sponsorForm(SponsorDto dto) {
//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/backup/NewSponsorForm.fxml"));
//
//        try {
//            AnchorPane page = (AnchorPane) loader.load();
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("New Sponsor");
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            dialogStage.initOwner(primaryStage);
//            Scene scene = new Scene(page);
//
//            dialogStage.setScene(scene);
//            // Set the person into the controller
//            SponsorDialogController controller = loader.getController();
//            controller.setDialogStage(dialogStage);
//            controller.setDto(dto);
//
//            // Show the dialog and wait until the user closes it
//            dialogStage.showAndWait();
//
//            return controller.isOkClicked();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//
//    }

    //TODO: demo code. delete once connected to db
    public class Controllers {
        private RootController rootController;

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

        public StudentsController detailsController() {
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
