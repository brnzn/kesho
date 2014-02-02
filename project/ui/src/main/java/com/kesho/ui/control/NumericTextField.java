package com.kesho.ui.control;

import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/3/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class NumericTextField extends TextField {
    @Override
    public void replaceText(IndexRange indexRange, String s) {
        if (StringUtils.isNumeric(s)) {
            super.replaceText(indexRange, s);
        }
    }

    @Override
    public void replaceText(int i, int i2, String s) {
        if (StringUtils.isNumeric(s)) {
            super.replaceText(i, i2, s);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    @Override
    public void replaceSelection(String s) {
        if (StringUtils.isNumeric(s)) {
            super.replaceSelection(s);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }


}
