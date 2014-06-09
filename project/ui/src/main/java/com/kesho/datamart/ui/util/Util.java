package com.kesho.datamart.ui.util;

import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang.StringUtils;

import java.util.EnumSet;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/8/13
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {
    public static void decorateNumericInput(TextField ...inputs) {
        for(TextField input: inputs) {
            input.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if(!StringUtils.isNumeric(keyEvent.getCharacter())) {
                        keyEvent.consume();
                    }
                }
            });
        }
    }

    public static String safeToStringValue(Object value, String defaultValue) {
        return value != null ? value.toString() : (defaultValue != null ? defaultValue : "");
    }

    public static Integer safeToIntegerValue(String value, Integer defaultValue) {
        return StringUtils.isNotBlank(value) ? Integer.valueOf(value) : defaultValue;
    }

    public static <T extends Enum> void initializeComboBoxValues(ComboBox<T> target, EnumSet source) {
        target.getItems().clear();
        target.getItems().addAll(source);
    }

    public static void initializeYesNoGroup(ToggleGroup...groups) {
        for (ToggleGroup group:groups) {
            group.getToggles().get(0).setUserData(Boolean.TRUE);
            group.getToggles().get(1).setUserData(Boolean.FALSE);
            group.getToggles().get(0).setSelected(true);
        }
    }

    public static void setYesNoToggleState(ToggleGroup group, Boolean value) {
        if (value == null || value) {
            group.getToggles().get(0).setSelected(true);
        } else {
            group.getToggles().get(1).setSelected(true);
        }
    }
}
