package com.example.javafx.controller;

import com.example.javafx.config.Constants;
import com.example.javafx.model.Ticket;
import com.example.javafx.utils.WindowUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class TicketListController {
    private WindowUtils windowUtils;
    public TicketListController(WindowUtils windowUtils) {
        this.windowUtils = windowUtils;
    }

    // columns
    @FXML
    public TableColumn<Ticket, Boolean> checkBoxCol;
    @FXML
    public TableColumn<Ticket, Long> incidentNumber;
    @FXML
    public TableColumn<Ticket, String> title;
    @FXML
    public TableColumn<Ticket, String> status;
    @FXML
    public TableColumn<Ticket, String> assignedTechnician;
    @FXML
    public TableColumn<Ticket, String> assignedGroup;

    // table
    @FXML
    public TableView<Ticket> ticketTable;

    // menu items
    @FXML
    public CheckMenuItem enableBulkModify;
    @FXML
    public MenuItem detailsMenuOpt;
    @FXML
    public MenuItem bulkModifyOpt;
    @FXML
    public MenuItem filterMenuOpt;


    @FXML
    public void initialize() {
        setupColumns();
        loadData();
    }

    @FXML
    public void closeApp() {
        Platform.exit();
    }

    @FXML
    public void showSettings() {
        windowUtils.openModal(new Stage(StageStyle.DECORATED), new ClassPathResource(Constants.SETTINGS_FXML), "Settings", Modality.APPLICATION_MODAL);
    }

    @FXML
    public void onBulkModify() {
        boolean enabled = enableBulkModify.isSelected();
        checkBoxCol.setVisible(enabled);
    }

    @FXML
    public void showTicketDetails() {
        windowUtils.openModal(new Stage(StageStyle.DECORATED), new ClassPathResource(Constants.TICKET_DETAIL_FXML), "Incident Details", Modality.WINDOW_MODAL);
    }

    @FXML
    public void filterTickets() {
       windowUtils.openModal(new Stage(StageStyle.DECORATED), new ClassPathResource(Constants.SEARCH_FILTER_FXML), "Search Filter", Modality.WINDOW_MODAL);
    }
    @FXML
    public void refreshTickets() {
        System.out.println("Refreshing....");
        System.out.println(ticketTable.getItems());
        loadData();
    }

    @FXML
    public void showContextMenu() {
        detailsMenuOpt.setVisible(ticketTable.getSelectionModel().getSelectedItem() != null);
        bulkModifyOpt.setVisible(anyItemsCheckedForBulkModify());
    }

    private boolean anyItemsCheckedForBulkModify() {
        return enableBulkModify.isSelected() && ticketTable.getItems().stream().anyMatch(Ticket::isSelected);
    }

    private void loadData() {
        ticketTable.getItems().setAll(
            new Ticket(10249249249L, "The printer failed", "Joebob", "Transferred", null, "ABC3SUPPGP"),
            new Ticket(10049499423L, "Dog failed", "Jimmy", "Transferred", null, "ABC3SUPPGP"),
            new Ticket(10432094223L, "I'm bored", "John", "Acknowledged", "O44442", "ABC2SUPPGP"),
            new Ticket(14923904823L, "Cat is hungry", "George", "Acknowledged", "H204929", "ABC2SUPPGP")
        );
    }

    private void setupColumns() {
        checkBoxCol.setCellValueFactory(p -> p.getValue().selectedProperty());
        checkBoxCol.setCellFactory(p -> {
            CheckBoxTableCell checkBox = new CheckBoxTableCell<>();
            checkBox.setSelectedStateCallback(param -> ticketTable.getItems().get((int)param).selectedProperty());
            return checkBox;
        });

        incidentNumber.setCellValueFactory(new PropertyValueFactory<>("incidentNumber"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        assignedTechnician.setCellValueFactory(new PropertyValueFactory<>("assignedTechnician"));
        assignedGroup.setCellValueFactory(new PropertyValueFactory<>("assignedGroup"));
    }


}