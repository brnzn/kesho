package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.Dto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 6/16/14
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractChildController<T extends Dto> implements ChildController<T> {
    protected SimpleObjectProperty<T> selected;
    protected Tab tab;
    public abstract void refresh(T dto);  //TODO: don't need to pass dto


    @Override
    public void setSelectedProperty(SimpleObjectProperty<T> selectedProperty) {
        this.selected = selectedProperty;

        selected.addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T dto1, T dto2) {
                selectedChanged(dto2);
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

    protected void selectedChanged(T dto) {
        tab.disableProperty().set(dto == null || dto.getId() == null);
        if (tab.isSelected()) {
            refresh(dto);
        }
    }
}
