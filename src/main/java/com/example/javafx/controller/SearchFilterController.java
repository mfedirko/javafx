package com.example.javafx.controller;

import com.example.javafx.model.SearchFilter;
import com.example.javafx.model.Settings;
import com.example.javafx.model.Ticket;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class SearchFilterController {

    private final SearchFilter searchFilter;
    private final Settings settings;

    private SearchFilter formSearchFilter;

    public SearchFilterController(SearchFilter searchFilter, Settings settings) {
        this.searchFilter = searchFilter;
        this.settings = settings;
    }

    // fields
    @FXML
    public DatePicker startDate;
    @FXML
    public DatePicker endDate;
    @FXML
    public Label errors;
    @FXML
    public TextArea queues;
    @FXML
    public ListView statuses;
    @FXML
    public CheckBox onlyAssignedToMe;

    public boolean validateForm() {
        errors.setText("");
        boolean isValid = true;
        if (formSearchFilter.getQueues() == null || formSearchFilter.getQueues().isEmpty()) {
            addError("Must select at least 1 queue!");
            isValid = false;
        }
        if (formSearchFilter.getEndDate() == null || formSearchFilter.getStartDate() == null) {
            addError("Start and end dates must be specified!");
            isValid = false;
        } else if (formSearchFilter.getEndDate().isBefore(formSearchFilter.getStartDate())) {
            addError("Start date must be before end date!");
            isValid = false;
        }
        if (formSearchFilter.getStatuses() == null || formSearchFilter.getStatuses().isEmpty()) {
            addError("Must select at least 1 status!");
            isValid = false;
        }

        return isValid;
    }

    private void addError(String error) {
        if (!errors.getText().isEmpty()) {
            errors.setText(errors.getText() + "; " + error);
        } else {
            errors.setText(error);
        }
    }

    @FXML
    public void saveSearchFilter() {
        System.out.println(formSearchFilter);
        System.out.println(formSearchFilter.getStartDate());
        System.out.println(formSearchFilter.getEndDate());
        if (validateForm()) {
            this.searchFilter.setStartDate(formSearchFilter.getStartDate());
            this.searchFilter.setEndDate(formSearchFilter.getEndDate());
            this.searchFilter.setOnlyAssignedToMe(formSearchFilter.isOnlyAssignedToMe());
            this.searchFilter.setStatuses(formSearchFilter.getStatuses());
            this.searchFilter.setQueues(formSearchFilter.getQueues());
        }
    }
    @FXML
    public void setAsDefaultSearch() {
        if (validateForm()) {
            saveSearchFilter();
            settings.setDefaultSearchFilter(this.searchFilter);
        }
    }

    @FXML
    public void initialize() {
        formSearchFilter = new SearchFilter(searchFilter);
        initializeBinds();
    }

    private void initializeBinds() {
        startDate.valueProperty().setValue(formSearchFilter.getStartDate());
        endDate.valueProperty().setValue(formSearchFilter.getEndDate());

        formSearchFilter.startDateProperty().bind(startDate.valueProperty());
        formSearchFilter.endDateProperty().bind(endDate.valueProperty());

        onlyAssignedToMe.selectedProperty().bindBidirectional(formSearchFilter.onlyAssignedToMeProperty());

        queues.textProperty().bindBidirectional(formSearchFilter.queuesProperty(), new StringConverter<ObservableList<String>>() {
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
                    list.setAll(split);
                    return list;
                }
                return FXCollections.emptyObservableList();
            }
        });

        statuses.setItems(FXCollections.observableArrayList(Ticket.IncidentStatus.values()));
        statuses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        statuses.getSelectionModel().getSelectedItems().addAll("NEW", "TRANSFERRED");
        statuses.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
    }
}
