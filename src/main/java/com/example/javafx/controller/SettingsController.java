package com.example.javafx.controller;

import com.example.javafx.model.Settings;
import com.example.javafx.utils.BindUtils;
import com.example.javafx.utils.WindowUtils;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
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
    public SettingsController(Settings settings, WindowUtils windowUtils) {
        this.settings = settings;
        this.windowUtils = windowUtils;
    }

    @FXML
    public void chooseDefaultSearch() {
        windowUtils.openModal(new Stage(StageStyle.DECORATED), new ClassPathResource(SEARCH_FILTER_FXML), "Search Filter", Modality.WINDOW_MODAL);
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
