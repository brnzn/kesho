package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.dto.ContactDto;
import com.kesho.datamart.dto.Dto;
import com.kesho.datamart.dto.SchoolDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.SchoolRepository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/19/14
 * Time: 10:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchoolContactsController extends AbstractChildController<SchoolDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolContactsController.class);
    @Inject
    private SchoolRepository schoolRepository;

    @FXML
    private TableView<ContactDto> contactsTable;
    @FXML
    private TableView<ContactDetailDto> contactDetailsTable;
    @FXML
    private TableColumn<ContactDetailDto, String> valueCol;
    @FXML
    private TableColumn<ContactDetailDto, String> typeCol;
    @FXML
    private TableColumn<ContactDetailDto, String> commentsCol;
    @FXML
    private TableColumn<ContactDto, String> nameCol;
    @FXML
    private TableColumn<ContactDto, String> surnameCol;
    @FXML
    private TableColumn<ContactDto, String> titleCol;
    @FXML
    private TableColumn<ContactDto, String> jobTitleCol;

    @FXML
    private Button newContactButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button newDetailsButton;
    @FXML
    private Button saveDetailsButton;
    @FXML
    private Button deleteDetailsButton;

    private SimpleObjectProperty<Boolean> dirtyContacts = new SimpleObjectProperty(false);
    private SimpleObjectProperty<Boolean> dirtyContactDetails = new SimpleObjectProperty(false);

    private ObservableList<ContactDto> contactsModel = FXCollections.observableArrayList();
    private ObservableList<ContactDetailDto> contactDetailsModel = FXCollections.observableArrayList();

    private Set<Integer> modifiedContacts = new HashSet<>();
    private Set<Integer> modifiedContactDetails = new HashSet<>();

    private SimpleObjectProperty<ContactDto> selectedContact = new SimpleObjectProperty<>();


    public SchoolContactsController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @Override
    public void setSelectedProperty(SimpleObjectProperty<SchoolDto> selectedProperty) {
        super.setSelectedProperty(selectedProperty);
        newContactButton.disableProperty().bind(selected.isNull());
    }

    @FXML
    private void initialize() {
        newDetailsButton.disableProperty().bind(selectedContact.isNull());
        saveButton.disableProperty().bind(dirtyContacts.isEqualTo(false));
        saveDetailsButton.disableProperty().bind(dirtyContactDetails.isEqualTo(false));

        contactsTable.setItems(contactsModel);
        contactDetailsTable.setItems(contactDetailsModel);

        contactsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContactDto>() {
            @Override
            public void changed(ObservableValue<? extends ContactDto> observable, ContactDto oldValue, ContactDto newValue) {
                selectedContact.set(newValue);
                deleteButton.setDisable(newValue == null);
            }
        });

        contactDetailsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContactDetailDto>() {
            @Override
            public void changed(ObservableValue<? extends ContactDetailDto> observable, ContactDetailDto oldValue, ContactDetailDto newValue) {
                deleteDetailsButton.setDisable(newValue == null);
            }
        });

        selectedContact.addListener(new ChangeListener<ContactDto>() {
            @Override
            public void changed(ObservableValue<? extends ContactDto> observable, ContactDto oldValue, ContactDto newValue) {
                refreshContactDetailsTable();
            }
        });


        typeCol.setCellValueFactory(new PropertyValueFactory<ContactDetailDto, String>("type"));
        typeCol.setCellFactory(ComboBoxTableCell.forTableColumn(ContactType.E.toString(), ContactType.P.toString()));

        nameCol.setCellValueFactory(new PropertyValueFactory<ContactDto, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameCol.setCellValueFactory(new PropertyValueFactory<ContactDto, String>("surname"));
        surnameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        titleCol.setCellValueFactory(new PropertyValueFactory<ContactDto, String>("title"));
        titleCol.setCellFactory(ComboBoxTableCell.forTableColumn("Br","Mr", "Miss", "Mrs"));

        jobTitleCol.setCellValueFactory(new PropertyValueFactory<ContactDto, String>("jobTitle"));
        jobTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());

        attachEditCommitHandler();

        valueCol.setCellValueFactory(new PropertyValueFactory<ContactDetailDto, String>("value"));
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn());
        commentsCol.setCellValueFactory(new PropertyValueFactory<ContactDetailDto, String>("comments"));
        commentsCol.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    private void attachEditCommitHandler() {
//        BiConsumer<ContactDto, String> c = (ContactDto cd, String value) -> cd.setName(value);

        typeCol.setOnEditCommit(new EditCommitHandler<>(modifiedContactDetails, dirtyContactDetails, new Consumer<ContactDetailDto>() {
            @Override
            public void accept(ContactDetailDto dto, String value) {
                dto.withType(toEnum(value));
            }

            public ContactType toEnum(String value) {
                if("Phone".equals(value)) {
                    return ContactType.P;
                } else if("Email".equals(value)) {
                    return ContactType.E;
                } else {
                    LOGGER.error("Cannot convert {} to ContactType", value);
                    return null;
                }
            }

        }));

        nameCol.setOnEditCommit(new EditCommitHandler<>(modifiedContacts, dirtyContacts, new Consumer<ContactDto>() {
            @Override
            public void accept(ContactDto dto, String value) {
                dto.setName(value);
            }
        }));

        surnameCol.setOnEditCommit(new EditCommitHandler<>(modifiedContacts, dirtyContacts, new Consumer<ContactDto>() {
            @Override
            public void accept(ContactDto dto, String value) {
                dto.setSurname(value);
            }
        }));

        titleCol.setOnEditCommit(new EditCommitHandler<>(modifiedContacts, dirtyContacts, new Consumer<ContactDto>() {
            @Override
            public void accept(ContactDto dto, String value) {
                dto.setTitle(value);
            }
        }));

        jobTitleCol.setOnEditCommit(new EditCommitHandler<>(modifiedContacts, dirtyContacts, new Consumer<ContactDto>() {
            @Override
            public void accept(ContactDto dto, String value) {
                dto.setJobTitle(value);
            }
        }));

        valueCol.setOnEditCommit(new EditCommitHandler<>(modifiedContactDetails, dirtyContactDetails, new Consumer<ContactDetailDto>() {
            @Override
            public void accept(ContactDetailDto dto, String value) {
                dto.withValue(value);
            }
        }));

        commentsCol.setOnEditCommit(new EditCommitHandler<>(modifiedContactDetails, dirtyContactDetails, new Consumer<ContactDetailDto>() {
            @Override
            public void accept(ContactDetailDto dto, String value) {
                dto.withComments(value);
            }
        }));
    }

    @FXML
    private void newContact() {
        dirtyContacts.set(true);
        ContactDto dto = new ContactDto();
        dto.setSchoolId(selected.get().getId());

        contactsModel.add(dto);
        contactsTable.requestFocus();
        contactsTable.getSelectionModel().select(contactsModel.size() - 1);
        contactsTable.getFocusModel().focus(contactsModel.size() - 1);
    }

    @FXML
    private void deleteContact() {
        schoolRepository.deleteContact(contactsTable.getSelectionModel().getSelectedItem().getId());
        refreshContactsTable();
    }

    @FXML
    private void saveContact() {
        for (Integer index : modifiedContacts) {  //TODO: batch update
            ContactDto dto = contactsModel.get(index);
            ContactDto result = schoolRepository.save(dto);
            dto.setId(result.getId());
        }

        resetContactFlags();
    }

    @FXML
    private void newDetails() {
        dirtyContactDetails.set(true);
        ContactDetailDto dto = new ContactDetailDto()
                .withOwnerId(selectedContact.get().getId())
                .withType(ContactType.P);

        contactDetailsModel.add(dto);
        contactDetailsTable.requestFocus();
        contactDetailsTable.getSelectionModel().select(contactDetailsModel.size() - 1);
        contactDetailsTable.getFocusModel().focus(contactDetailsModel.size() - 1);

    }

    @FXML
    private void deleteDetails() {
        schoolRepository.deleteContactDetail(contactDetailsTable.getSelectionModel().getSelectedItem().getId());
        refreshContactDetailsTable();
    }

    @FXML
    private void saveDetails() {
        for (Integer index : modifiedContactDetails) {  //TODO: batch update
            ContactDetailDto dto = contactDetailsModel.get(index);
            dto = schoolRepository.save(dto);
        }

        resetContactDetailsFlags();
    }

    private void resetContactDetailsFlags() {
        dirtyContactDetails.set(false);
        modifiedContactDetails.clear();
    }

    @Override
    public void refresh(SchoolDto dto) {
        resetContactFlags();
        resetContactDetailsFlags();
        refreshContactsTable();
        refreshContactDetailsTable();
    }

    private void resetContactFlags() {
        dirtyContacts.set(false);
        modifiedContacts.clear();
    }

    private void refreshContactsTable() {
        contactsModel.clear();

        if (selected.get() != null) {
            contactsModel.addAll(schoolRepository.getContactsFor(selected.get().getId()));
        }

        contactsTable.setItems(null);
        contactsTable.layout();
        contactsTable.setItems(contactsModel);
    }

    private void refreshContactDetailsTable() {
        contactDetailsModel.clear();

        if (selectedContact.get() != null) {
            contactDetailsModel.addAll(schoolRepository.getContactDetailsOf(selectedContact.get().getId()));
        }

        contactDetailsTable.setItems(null);
        contactDetailsTable.layout();
        contactDetailsTable.setItems(contactDetailsModel);
    }

    private class EditCommitHandler<T extends Dto> implements EventHandler<TableColumn.CellEditEvent<T, String>> {
        //      private BiConsumer<ContactDto, String> consumer;
        private final Consumer<T> consumer;
        private final Set<Integer> modified;
        private final SimpleObjectProperty<Boolean> dirtyFlag;

        public EditCommitHandler(Set<Integer> modified, SimpleObjectProperty<Boolean> dirtyFlag, Consumer<T> consumer) {
            this.consumer = consumer;
            this.modified = modified;
            this.dirtyFlag = dirtyFlag;
        }

        @Override
        public void handle(TableColumn.CellEditEvent<T, String> event) {
            T target = event.getTableView().getItems().get(
                    event.getTablePosition().getRow());

            String value = event.getNewValue();
            consumer.accept(target, value);

            dirtyFlag.set(true);
            modified.add(event.getTablePosition().getRow());
        }
    }

    private interface Consumer<T extends Dto> {
        void accept(T dto, String value);
    }
}
