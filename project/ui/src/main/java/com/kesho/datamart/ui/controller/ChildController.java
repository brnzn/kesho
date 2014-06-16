package com.kesho.datamart.ui.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Tab;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 6/16/14
 * Time: 8:13 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ChildController <T>{
    void setSelectedProperty(SimpleObjectProperty<T> selectedProperty);
    void setTab(Tab tab);
}
