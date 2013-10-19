package com.kesho.crm.ui.controller;

import com.kesho.crm.ui.WindowsUtil;
import javafx.fxml.FXML;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 10/19/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummyCpontroller {
    @FXML
    private void handleNewStudent() {
        WindowsUtil.getInstance().showNewStudentDetails();
    }
}
