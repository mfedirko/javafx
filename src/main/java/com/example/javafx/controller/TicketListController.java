package com.example.javafx.controller;

import com.example.javafx.config.Constants;
import com.example.javafx.config.javafx.components.AlertBox;
import com.example.javafx.config.javafx.components.ConfirmBox;
import com.example.javafx.model.SearchFilter;
import com.example.javafx.model.Ticket;
import com.example.javafx.model.event.FilterChangedEvent;
import com.example.javafx.repository.impl.SearchFilterRepository;
import com.example.javafx.model.SearchContext;
import com.example.javafx.service.MockTicketsService;
import com.example.javafx.utils.WindowUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class TicketListController implements ApplicationListener<FilterChangedEvent> {
    private WindowUtils windowUtils;
    private SearchFilterRepository filterRepository;
    private MockTicketsService ticketsService;
    public TicketListController(WindowUtils windowUtils, SearchFilterRepository filterRepository,
                                MockTicketsService ticketsService) {
        this.windowUtils = windowUtils;
        this.filterRepository = filterRepository;
        this.ticketsService = ticketsService;
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
    public Menu searchFiltersMenu;

    @FXML
    public void initialize() {
        if (SearchContext.activeSearchFilter() == null) {
            SearchContext.setActiveSearchFilter(filterRepository.getDefaultFilter());
        }
        setupColumns();
        setupFiltersMenu();
        loadData();
    }

    private void setupFiltersMenu() {
        searchFiltersMenu.getItems().clear();
        for (SearchFilter filter : filterRepository.getAllFilters()) {
            Menu filterMenuItem = new Menu();
            String menuName = filter.getName();
            int insertAtIndex = searchFiltersMenu.getItems().size();

            // activate option
            MenuItem activate = new MenuItem("Activate");
            if (activate.getOnAction() == null) {
                activate.setOnAction(e -> {
                    activateFilter(filter);
                    setupFiltersMenu();
                });
            }

            // edit option
            MenuItem edit = new MenuItem("Edit");
            if (edit.getOnAction() == null) {
                edit.setOnAction(evt -> {
                    SearchContext.setFilterToEdit(filterRepository.findByName(filter));
                    windowUtils.openModal(new Stage(StageStyle.DECORATED), new ClassPathResource(Constants.SEARCH_FILTER_FXML), "Search Filter", Modality.APPLICATION_MODAL);
                });
            }

            // delete option
            MenuItem delete = new MenuItem("Delete");
            if (delete.getOnAction() == null) {
                delete.setOnAction(evt -> {
                    boolean confirmed = ConfirmBox.display("Delete search filter", "Confirm whether to delete the search filter");
                    if (!confirmed) return;

                    if (filter.isDefault()) {
                        AlertBox.display("Error", "Please choose another default search filter before deleting");
                    } else if (filter.equals(SearchContext.activeSearchFilter())) {
                        AlertBox.display("Error", "Please set another active search filter before deleting");

                    } else {
                        filterRepository.deleteSearchFilter(filter);
                        setupFiltersMenu(); //refresh the filters in menu
                    }
                });
            }

            filterMenuItem.getItems().addAll(activate, edit, delete);
            if (filter.isDefault()) {
                menuName += " (default)";
                insertAtIndex = 0;
            }
            if (filter.equals(SearchContext.activeSearchFilter())) menuName += " (active)";

            filterMenuItem.setText(menuName);
            searchFiltersMenu.getItems().add(insertAtIndex, filterMenuItem);

        }
    }

    private void activateFilter(SearchFilter filter) {
        SearchContext.setActiveSearchFilter(filterRepository.findByName(filter));
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
        SearchContext.setFilterToEdit(filterRepository.findByName(SearchContext.activeSearchFilter()));
        windowUtils.openModal(new Stage(StageStyle.DECORATED), new ClassPathResource(Constants.SEARCH_FILTER_FXML), "Search Filter", Modality.WINDOW_MODAL);
    }
    @FXML
    public void refreshTickets() {
        System.out.print("Refreshing.... Active search= ");
        System.out.println(SearchContext.activeSearchFilter());
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
        Thread dataLoader = new Thread(() -> {
            ticketTable.getItems().clear();
            ticketTable.getItems().setAll(ticketsService.findTickets());
        });
        dataLoader.start();
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


    @Override
    public void onApplicationEvent(FilterChangedEvent filterChangedEvent) {
        SearchFilter filter = filterChangedEvent.getFilter();
        String activeFilterName = SearchContext.activeSearchFilter() != null ? SearchContext.activeSearchFilter().getName() : null;
        if (Objects.equals(filter.getName(), activeFilterName)) {
            activateFilter(filter);
        } else if (filter.isDefault()) {
            setupFiltersMenu();
        }
    }
}