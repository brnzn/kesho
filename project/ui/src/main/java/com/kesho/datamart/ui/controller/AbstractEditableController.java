package com.kesho.datamart.ui.controller;

import com.kesho.datamart.ui.validation.FormValidator;
import javafx.scene.Node;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 6/17/14
 * Time: 7:57 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEditableController<T> extends AbstractChildController<T> {
    public abstract Map<String, Node> getValidateableFields();

    @Override
    protected void selectedChanged(T dto) {
        super.selectedChanged(dto);
        clearFormValidations(getValidateableFields());
    }

    protected void clearFormValidations(Map<String, Node> fields) {
        if(fields != null) {
            FormValidator.clearValidation(fields.values());
        }
    }

}
