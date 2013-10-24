package com.kesho.crm.ui.controller;

import com.kesho.crm.ui.WindowsUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 10/24/13
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class RootController {
    @FXML private Label mainLbl;

    public void setTtile(String text) {
        mainLbl.setText(text);
    }

    @FXML
    private void openStudents() throws IOException {
        WindowsUtil.getInstance().showStudentsTable();
    }
}
