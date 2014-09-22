package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.ContactDto;
import com.kesho.datamart.dto.SchoolDto;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 9/19/14
 * Time: 10:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchoolContactsController extends AbstractChildController<SchoolDto>{
    @FXML
    private TableView<ContactDto> contactsTable;
    @FXML
    private TableColumn<ContactDto, String> nameCol;
    @FXML
    private TableColumn<ContactDto, String> surnameCol;
    @FXML
    private TableColumn<ContactDto, String> titleCol;
    @FXML
    private TableColumn<ContactDto, String> jobTitleCol;

    @FXML
    void newFired() {

    }

    @FXML
    private void deleteFired() {

    }

    @FXML
    private void save() {

    }


    @Override
    public void refresh(SchoolDto dto) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
