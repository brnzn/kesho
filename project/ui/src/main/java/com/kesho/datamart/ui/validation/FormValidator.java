package com.kesho.datamart.ui.validation;

import com.kesho.datamart.domain.ValidationResult;
import com.kesho.datamart.service.ValidationUtil;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 2/3/14
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */

//TODO: java 8 stream cause exceptions with hibernate
public class FormValidator {
    static Effect invalidEffect = new DropShadow(BlurType.GAUSSIAN, Color.RED, 4, 0.0, 0, 0);

    public static <T> String validate(Function<T, String> func, T target) {
        return func.apply(target);
    }

    public static void renderInvalid(Node node) {
        node.setEffect(invalidEffect);
    }

    public static void clearValidation(Collection<Node> nodes) {
        if(nodes != null) {
            for (Node node: nodes) {
                node.setEffect(null);
            }
        }
    }

    public static String reduce(List<String> msgs) {
        StringBuilder sb = new StringBuilder();
        for (String msg:msgs) {
            sb.append(msg).append("\n");
        }
//        String s = msgs.stream()
//                .reduce(
//                        "",
//                        (a, b) -> a + b + "\n");
//
        return sb.toString();
    }

    public static List<String> validate(Object dto, Map<String, Node> targetFields) {
        if(targetFields.isEmpty()) {
            return new ArrayList<>();
        }

        ValidationResult validations = ValidationUtil.validateNew(dto);

        //targetFields.values().forEach(node -> node.setEffect(null));

        for (Node node:targetFields.values()) {
            node.setEffect(null);
        }

        final List<String> errs = new ArrayList<>();

        if (validations.isValid()) {
            return errs;
        }

        String errorMessage = "";


//        validations.values().forEach(violation -> {
//            errs.add(violation.getKey());
//            targetFields.get(violation.getValue().toString()).setEffect(invalidEffect);
//        });

        for (Map.Entry<String, String> violation:validations.values()) {
            errs.add(violation.getValue());
            targetFields.get(violation.getKey()).setEffect(invalidEffect);
        }

        return errs;
    }
}
