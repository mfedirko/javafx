package com.example.javafx.controller;

import com.example.javafx.model.SearchFilter;
import com.example.javafx.model.Ticket;
import com.example.javafx.model.event.FilterChangedEvent;
import com.example.javafx.repository.impl.SearchFilterRepository;
import com.example.javafx.model.SearchContext;
import com.example.javafx.utils.TextAreaToListConverter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;

@Controller
public class SearchFilterController {

    private SearchFilter searchFilterToEdit;
    private final SearchFilterRepository repo;

    private ApplicationEventPublisher publisher;

    private SearchFilter copyOfSearchFilter;

    public SearchFilterController(SearchFilterRepository searchFilter,
                                  ApplicationEventPublisher eventPublisher) {
        this.repo = searchFilter;
        this.publisher = eventPublisher;
        initializeFilter();
    }

    // fields
    @FXML
    public DatePicker startDate;
    @FXML
    public DatePicker endDate;
    @FXML
    public Label errors;
    @FXML
    public Label successMsg;
    @FXML
    public Button saveBtn;
    @FXML
    public TextArea queues;
    @FXML
    public ListView statuses;
    @FXML
    public TextField filterName;
    @FXML
    public CheckBox onlyAssignedToMe;

    public boolean validateForm() {
        errors.setText("");
        successMsg.setText("");
        boolean isValid = true;
        if (copyOfSearchFilter.getQueues() == null || copyOfSearchFilter.getQueues().isEmpty()) {
            addError("Must select at least 1 queue!");
            isValid = false;
        } else
        if (copyOfSearchFilter.getEndDate() == null || copyOfSearchFilter.getStartDate() == null) {
            addError("Start and end dates must be specified!");
            isValid = false;
        } else if (copyOfSearchFilter.getEndDate().isBefore(copyOfSearchFilter.getStartDate())) {
            addError("Start date must be before end date!");
            isValid = false;
        }
        if (copyOfSearchFilter.getStatuses() == null || copyOfSearchFilter.getStatuses().isEmpty()) {
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
        System.out.println(copyOfSearchFilter);
        System.out.println(copyOfSearchFilter.getStartDate());
        System.out.println(copyOfSearchFilter.getEndDate());
        if (validateForm()) {
            SearchFilter toSave = new SearchFilter(copyOfSearchFilter);
            SearchFilter savedEntity = this.repo.saveSearchFilter(toSave);
            boolean saved = savedEntity != null;
            if (!saved) addError("Search filter does not exist!");
            else {
                publisher.publishEvent(new FilterChangedEvent(this, savedEntity));
                successMsg.setText("Search filter '" + savedEntity.getName() + "' saved successfully!");
            }
        }
    }
    @FXML
    public void newSearchFilter() {
        if (validateForm()) {
            SearchFilter toSave = new SearchFilter(copyOfSearchFilter);
            SearchFilter savedEntity = this.repo.newSearchFilter(toSave);
            boolean saved = savedEntity != null;
            if (!saved) addError("Duplicate search filter with this name already exists!");
            else {
                successMsg.setText("New search filter '" + savedEntity.getName() + "' created successfully!");
            }
        }
    }
    @FXML
    public void setAsDefaultSearch() {
        if (validateForm()) {
            if (copyOfSearchFilter.isDefault()) addError("This is already the default search filter");
            else {
                repo.setDefaultFilter(copyOfSearchFilter);
                publisher.publishEvent(new FilterChangedEvent(this, repo.findByName(copyOfSearchFilter)));
                successMsg.setText("Default search filter updated!");
            }
        }
    }

    private void initializeFilter() {
        if (SearchContext.getFilterToEdit() != null) {
            this.searchFilterToEdit = SearchContext.getFilterToEdit();
        } else this.searchFilterToEdit = repo.getDefaultFilter();
        copyOfSearchFilter = new SearchFilter(searchFilterToEdit);
    }
    @FXML
    public void initialize() {
        initializeFilter();
        initializeBinds();
    }

    private void initializeBinds() {
        // start date
        startDate.valueProperty().setValue(copyOfSearchFilter.getStartDate());
        copyOfSearchFilter.startDateProperty().bind(startDate.valueProperty());

        // filter name
        filterName.textProperty().bindBidirectional(copyOfSearchFilter.nameProperty());

        // end date
        endDate.valueProperty().setValue(copyOfSearchFilter.getEndDate());
        copyOfSearchFilter.endDateProperty().bind(endDate.valueProperty());

        // only assigned to me
        onlyAssignedToMe.selectedProperty().bindBidirectional(copyOfSearchFilter.onlyAssignedToMeProperty());

        // queues
        queues.textProperty().bindBidirectional(copyOfSearchFilter.queuesProperty(), new TextAreaToListConverter());

        // statuses
        statuses.setItems(FXCollections.observableArrayList(Ticket.IncidentStatus.values()));
        statuses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        copyOfSearchFilter.getStatuses().forEach(s -> statuses.getSelectionModel().select(s));
        statuses.getSelectionModel().getSelectedItems().addListener((ListChangeListener) c -> {
            copyOfSearchFilter.getStatuses().setAll(statuses.getSelectionModel().getSelectedItems());
        });

    }
}
