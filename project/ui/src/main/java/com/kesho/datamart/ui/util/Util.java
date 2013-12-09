package com.kesho.datamart.ui.util;

import javafx.scene.control.ComboBox;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/8/13
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {
    public static String safeToStringValue(Object value, String defaultValue) {
        return value != null ? value.toString() : (defaultValue != null ? defaultValue : "");
    }

    public static Integer safeToIntegerValue(String value, Integer defaultValue) {
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    public static <T extends Enum> void initializeComboBoxValues(ComboBox<T> target, EnumSet source) {
        target.getItems().clear();
        target.getItems().addAll(source);
    }

}
