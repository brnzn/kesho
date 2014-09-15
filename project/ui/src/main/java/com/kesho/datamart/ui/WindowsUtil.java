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
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

//TODO: refactor - method to load fxmls
public class WindowsUtil {

	private static WindowsUtil instance = new WindowsUtil();
    private BorderPane rootLayout;
	private Stage primaryStage;
    private ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/ui-context.xml");


    private WindowsUtil(){}
	
	public static WindowsUtil getInstance() {
		return instance;
	}

    public void showRoot(final Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/RootLayout.fxml"));
            loader.setController(getController(RootController.class));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }

        WindowsUtil.getInstance().students();
        primaryStage.sizeToScene();
    }

    public void showErrorDialog(String title, String masthead, String msg) {
        Dialogs.create()
                .owner( primaryStage)
                .title(title)
                .masthead(masthead)
                .message(msg)
                .showError();
    }

    public EventBus getEventBus() {
        return applicationContext.getBean(EventBus.class);
    }

    public boolean showWarningDialog(String title, String head, String message) {
        Action response = Dialogs.create()
                .owner(primaryStage)
                .title(title)
                .masthead(head)
                .message(message)
                .actions(Dialog.Actions.YES, Dialog.Actions.NO)
                .showWarning();

        return Dialog.Actions.YES == response;
    }

    public void sponsors() {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Sponsors.fxml"));
            getController(RootController.class).setTtile("Sponsors");//controllers.getRootController().setTtile("Sponsors");
            loader.setController(getController(SponsorsController.class));//WindowsUtil.getInstance().getControllers().sponsorsController());

            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);
            primaryStage.sizeToScene();
            primaryStage.getScene().getStylesheets().add(WindowsUtil.class.getResource("/style/main_styles.css").toExternalForm());

        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public void sponsors(Long sponsorId) {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Sponsors.fxml"));
            getController(RootController.class).setTtile("Sponsors");//controllers.getRootController().setTtile("Sponsors");
            SponsorsController sc = getController(SponsorsController.class);
            loader.setController(sc);//WindowsUtil.getInstance().getControllers().sponsorsController());

            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);
            primaryStage.sizeToScene();
            sc.init(sponsorId);
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    public void students() {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Students.fxml"));
            getController(RootController.class).setTtile("Students");//controllers.getRootController().setTtile("Students");
            StudentsController sc = getController(StudentsController.class);//WindowsUtil.getInstance().getControllers().studentsController();
            loader.setController(sc);

            AnchorPane overviewPage = (AnchorPane) loader.load();
            rootLayout.setCenter(overviewPage);
            primaryStage.sizeToScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void students(Long studentId) {
        try {
            // Load the fxml file and set into the center of the main layout
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Students.fxml"));
            getController(RootController.class).setTtile("Students");//controllers.getRootController().setTtile("Students");
            StudentsController sc = getController(StudentsController.class);//WindowsUtil.getInstance().getControllers().studentsController();
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
        StudentSelectorController controller = getController(StudentSelectorController.class);//controllers.getStudentSelector();
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
        FamilySelectorController controller = getController(FamilySelectorController.class);//controllers.getFamilySelector();
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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Families.fxml"));
        FamiliesController controller = getController(FamiliesController.class);
        loader.setController(controller);
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Family Form");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller
//        controller.setDialogStage(dialogStage);
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }

    public void institutionsForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/InstitutionForm.fxml"));
        InstitutionController controller = getController(InstitutionController.class);//controllers.getInstitutionController();
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

    private <T> T getController(Class<T> controller) {
        return applicationContext.getBean(controller);
    }
}
