package com.example.javafx.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class BindUtils {
    public static void bindNumberField(TextField textField, IntegerProperty integerProperty) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.setText(newValue.replaceAll("[^\\d]*", ""));
            if (!textField.getText().isEmpty()) {
                try {
                    Integer integer = Integer.parseInt(textField.getText());
                    if (integer > 0) integerProperty.set(integer);
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        });
        textField.textProperty().bindBidirectional(integerProperty, new NumberStringConverter());
    }

    public static void bindCheckBox(CheckBox checkBox, BooleanProperty booleanProperty) {
        checkBox.selectedProperty().bindBidirectional(booleanProperty);
    }
    public static void bindText(TextField textField, StringProperty stringProperty) {
        textField.textProperty().bindBidirectional(stringProperty);
    }
}
