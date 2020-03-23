package com.example.javafx.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

/*
 * Converts text to a list of strings by splitting the text by spaces, commas or newlines
 * Converts list of strings to text by adding newlines between items
 */
public class TextAreaToListConverter extends StringConverter<ObservableList<String>> {
    @Override
    public String toString(ObservableList<String> object) {
        if (object != null) {
            StringBuilder sb = new StringBuilder();
            object.forEach(s -> sb.append(s).append('\n'));
            return sb.toString();
        }
        return "";
    }
    @Override
    public ObservableList<String> fromString(String string) {
        if (string != null) {
            ObservableList<String> list = FXCollections.observableArrayList();
            String[] split = string.trim().split("\\s*,\\s*|\\s+|\\n+");
            list.clear();
            for (String item : split) {
                if (item != null && !item.isEmpty()) list.add(item);
            }
            return list;
        }
        return FXCollections.emptyObservableList();
    }
}
