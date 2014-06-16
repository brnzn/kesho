package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.StudentDto;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 6/16/14
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractChildController<T> implements ChildController<T> {
    protected SimpleObjectProperty<T> selected = new SimpleObjectProperty<>();

    private Tab tab;

    public abstract void refresh(T dto);
    public abstract Map<String, Node> getValidateableFields();


    @Override
    public void setSelectedProperty(SimpleObjectProperty<T> selectedProperty) {
        this.selected.bind(selectedProperty);
        //this.selected = selectedProperty;

        selected.addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T dto1, T dto2) {
                tab.disableProperty().set(dto2 == null);
                if (tab.isSelected()) {
                    clearFormValidations(getValidateableFields());
                    refresh(dto2);
                }
            }
        });
    }

    @Override
    public void setTab(Tab tab) {
        this.tab = tab;
        tab.disableProperty().set(true);

        tab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(javafx.event.Event event) {
                if (tab.isSelected()) {
                    refresh(selected.getValue());
                }
            }
        });
    }

    protected void clearFormValidations(Map<String, Node> fields) {
        if(fields != null) {
            FormValidator.clearValidation(fields.values());
        }
    }
}
