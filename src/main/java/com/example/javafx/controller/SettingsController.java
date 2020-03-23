package com.example.javafx.controller;

import com.example.javafx.model.Settings;
import com.example.javafx.repository.impl.SettingsRepository;
import com.example.javafx.model.SearchContext;
import com.example.javafx.utils.BindUtils;
import com.example.javafx.utils.WindowUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import static com.example.javafx.config.Constants.SEARCH_FILTER_FXML;

@Controller
public class SettingsController {

    @FXML
    public TextField ticketRefreshIntervalSetting;
    @FXML
    public CheckBox notifyWhenSLABreachSetting;
    @FXML
    public CheckBox notifyWhenAssignedSetting;
    @FXML
    public TextField usernameSetting;

    private final Settings settings;
    private final WindowUtils windowUtils;
    public SettingsController(SettingsRepository settings, WindowUtils windowUtils) {
        this.settings = settings.getEntity();
        this.windowUtils = windowUtils;
    }

    @FXML
    public void chooseDefaultSearch() {
        SearchContext.setFilterToEdit(null);
        windowUtils.openModal(new Stage(StageStyle.DECORATED), new ClassPathResource(SEARCH_FILTER_FXML), "Default Search Filter", Modality.WINDOW_MODAL);
    }

    @FXML
    public void initialize() {
        initializeFormBinds();
    }

    private void initializeFormBinds() {
        BindUtils.bindNumberField(ticketRefreshIntervalSetting, settings.ticketRefreshIntervalMinsProperty());
        BindUtils.bindCheckBox(notifyWhenSLABreachSetting, settings.notifyOnTicketsBreachingSLAProperty());
        BindUtils.bindCheckBox(notifyWhenAssignedSetting, settings.notifyOnTicketAssignedToMeProperty());
        BindUtils.bindText(usernameSetting, settings.usernameProperty());

    }
}
