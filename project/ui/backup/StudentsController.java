package backup;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.repository.StudentsRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;

/**
 * The controller for the overview with address childrenTable and details view.
 *
 */
@Named
public class StudentsController {
    @Autowired
	private StudentsRepository repo;
	
	@FXML
	private TableView<StudentDto> studentsTable;
	@FXML
	private TableColumn<StudentDto, String> firstNameColumn;
	@FXML
	private TableColumn<StudentDto, String> familyNameColumn;

	@FXML
	private Label nameLbl;
	@FXML
	private Label familyNameLbl;
    @FXML
    private Label genderLbl;
    @FXML
    private Label yearOfBirthLbl;
    @FXML
    private Label contactNumLbl;
    @FXML
    private Label homeLocationLbl;
    @FXML
    private Label currentStudentLbl;
    @FXML
    private Label hasDisabilityLbl;
    @FXML
    private Label sponsoredLbl;
    @FXML
    private Label startDateLbl;

    @FXML
    private Button newButton;
    @FXML
    private Button saveButton;
    @FXML
    private Pagination pagination;

	private ObservableList<StudentDto> studentsModel = FXCollections.observableArrayList();


	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public StudentsController() {
        System.out.println("=======================");
    }

    private Page<StudentDto> getPage(final int page, final int pageSize) {
        return repo.getPage(page, pageSize);
    }

    //TODO: what should happen after adding student? Should we go back to last page, or last selected page? what happen if students were deleted by someone else or if we had order
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
        initTable();
        initPagination();
        firstNameColumn.setSortType(TableColumn.SortType.DESCENDING);

        studentsTable.getSelectionModel().select(0);
        studentsTable.focusModelProperty().get().focus(0, firstNameColumn);
        studentsTable.requestFocus();


        studentsTable.layout();
    }

    private void initTable() {
        studentsModel.clear();

        studentsTable.setItems(studentsModel);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("name"));
        familyNameColumn.setCellValueFactory(new PropertyValueFactory<StudentDto, String>("familyName"));

//		//update form when selecting childrenTable row
        studentsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StudentDto>() {
            @Override
            public void changed(ObservableValue<? extends StudentDto> observable,
                                StudentDto oldValue, StudentDto newValue) {
                showStudentDetails(newValue);
            }
        });

        studentsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    StudentDto selected = studentsTable.getSelectionModel().getSelectedItem();
                    if (mouseEvent.getClickCount() == 2 && selected != null) {
                   //     WindowsUtil.getInstance().showNewStudentDetails(selected);
                    }
                }
            }
        });

        //select row
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                studentsTable.requestFocus();
//                studentsTable.getSelectionModel().select(1);
//                studentsTable.getFocusModel().focus(0);
//            }
//        });
    }

    private void initPagination() {
        Page p = getPage(0, 2);
        if(p != null) {
            studentsModel.addAll(p.getContent());    //
            pagination.setPageCount(p.getTotalPages() > 0 ? p.getTotalPages() : 1);
        }

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Page<StudentDto> p = getPage(newValue.intValue(), 2);
                pagination.setPageCount(p.getTotalPages());
                studentsModel.clear();
                studentsModel.addAll(p.getContent());
            }
        });
    }
    //TODO: default values
	private void showStudentDetails(StudentDto person) {
 //       nameLbl.textProperty().bind(firstNameColumn.getCellObservableValue(studentsTable.getSelectionModel().getSelectedIndex()));

        if (person != null) {
			nameLbl.setText(person.getFirstName());
			familyNameLbl.setText(person.getFamily().getFamilyName());
            if(person.getGender() != null) {
                genderLbl.setText(person.getGender().toString());
            }

            yearOfBirthLbl.setText(String.valueOf(person.getYearOfBirth()));
            contactNumLbl.setText(person.getMobileNumber());
            homeLocationLbl.setText(person.getHomeLocation());

            if(person.isActiveStudent() != null) {
                currentStudentLbl.setText(String.valueOf(person.isActiveStudent()));
            }

            if (person.hasDisability() != null) {
                hasDisabilityLbl.setText(String.valueOf(person.hasDisability()));
            }

            if(person.isSponsored() != null) {
                sponsoredLbl.setText(String.valueOf(person.isSponsored()));
            }

            if(person.getStartDate() != null) {
                startDateLbl.setText(person.getStartDate().toString()); //TODO: format
            }
		}
	}
}