package com.kesho.datamart.ui.controller;

import com.kesho.datamart.domain.ContactType;
import com.kesho.datamart.dto.ContactDetailDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.repository.StudentsRepository;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/15/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentContactsController extends AbstractTabularFormController<ContactDetailDto, StudentDto> {
    @Inject
    private StudentsRepository studentsRepository;

    @FXML
    private TextField value;
    @FXML
    private TextArea comments;

    @FXML
    protected TableColumn<ContactDetailDto, String> commentsCol;
    @FXML
    protected TableColumn<ContactDetailDto, String> valueCol;


    private Map<String, Node> validationFields = new HashMap<>();

    public StudentContactsController() {
        WindowsUtil.getInstance().autowire(this);
    }

    @FXML
    protected void initialize() {
        super.initialize();
        valueCol.setCellValueFactory(new PropertyValueFactory<ContactDetailDto, String>("value"));
        commentsCol.setCellValueFactory(new PropertyValueFactory<ContactDetailDto, String>("comments"));

        comments.disableProperty().bind(saveButton.disabledProperty());
        value.disableProperty().bind(saveButton.disabledProperty());

    }

    @Override
    protected List<ContactDetailDto> getData(Long ownerId) {
        return studentsRepository.getContacts(ownerId, ContactType.S);
    }

    @Override
    protected ContactDetailDto getDto() {
        ContactDetailDto dto = selectedChild.get();
        if (dto == null) {
            dto = new ContactDetailDto();
        }

        dto.withComments(comments.getText())
           .withType(ContactType.S)
           .withValue(value.getText())
            .withOwnerId(selected.get().getId())
        ;

        return dto;
    }

    @Override
    protected void updateForm(ContactDetailDto dto) {
        if(dto == null) {
            resetForm();
            return;
        }

        value.setText(dto.getValue());
        comments.setText(dto.getComments());
    }

    private void resetForm() {
        value.clear();
        comments.clear();
    }

    @Override
    protected ContactDetailDto saveDto(ContactDetailDto dto) {
        return studentsRepository.save(dto);
    }

    @Override
    protected void deleteDto(Long id) {
        studentsRepository.deleteContactDetail(id);
    }

    @Override
    protected Map<String, Node> getValidateableFields() {
        if(validationFields.isEmpty()) {
            validationFields.put("value", value);
        }

        return validationFields;
    }
}
