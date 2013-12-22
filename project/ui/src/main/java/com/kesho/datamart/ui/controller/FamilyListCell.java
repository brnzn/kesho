package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.FamilyDto;
import javafx.scene.control.ListCell;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/21/13
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class FamilyListCell extends ListCell<FamilyDto> {
    @Override
    protected void updateItem(FamilyDto item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setText(item.getName());
        }
    }
}