package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.repository.StudentsRepository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 */
@Named
public class StudentSelectorController extends SelectorController<StudentDto> {
    @Inject
    private StudentsRepository repository;

    @Override
    public Collection<StudentDto> getData() {
        return repository.getStudents();
    }
}
