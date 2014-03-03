package com.kesho.datamart.ui.validation;

import com.kesho.datamart.domain.ValidationResult;
import com.kesho.datamart.dto.SponsorDto;
import com.kesho.datamart.service.ValidationUtil;
import com.kesho.datamart.ui.WindowsUtil;
import javafx.scene.Node;
import javafx.scene.control.Dialogs;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 2/3/14
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormValidator {
    private static Effect invalidEffect = new DropShadow(BlurType.GAUSSIAN, Color.RED, 4, 0.0, 0, 0);

    public static boolean validateAndAlert(Object dto, Map<String, Node> targetFields) {
        String validation = validate(dto, targetFields);

        if(StringUtils.isNotBlank(validation)) {
            WindowsUtil.getInstance().showErrorDialog(validation);
            return false;
        } else {
            return true;
        }
    }

    public static String validate(Object dto, Map<String, Node> targetFields) {
        ValidationResult validations = ValidationUtil.validateNew(dto);

        for (Node node:targetFields.values()) {
            node.setEffect(null);
        }

        if (validations.isValid()) {
            return null;
        }

        String errorMessage = "";

        for (Map.Entry<String, String> violation:validations.values()) {
            errorMessage += violation.getKey() + "\n";
            targetFields.get(violation.getValue().toString()).setEffect(invalidEffect);
        }

        return errorMessage;
    }
}
