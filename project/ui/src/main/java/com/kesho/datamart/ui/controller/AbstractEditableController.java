package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.Dto;
import com.kesho.datamart.ui.WindowsUtil;
import com.kesho.datamart.ui.validation.FormValidator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 6/17/14
 * Time: 7:57 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEditableController<T extends Dto> extends AbstractChildController<T> {
    protected abstract Map<String, Node> getValidateableFields();
    protected abstract void doSave();

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

    @FXML
    protected void save() {
        try {
            doSave();
        } catch(OptimisticLockingFailureException e) {
            WindowsUtil.getInstance().showErrorDialog("Saving Error", "Failed to save", "The record has been modified by someone else! Please refresh the childrenTable and re-select to update.");
        }
    }

}
