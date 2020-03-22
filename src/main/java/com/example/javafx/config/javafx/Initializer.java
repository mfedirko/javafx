package com.example.javafx.config.javafx;

import com.example.javafx.utils.WindowUtils;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements ApplicationListener<StageReadyEvent> {
    private final String applicationTitle;
    private final Resource fxml;
    private final WindowUtils windowUtils;

    public Initializer(
            @Value("${application.ui.startup.fxml}") Resource startPageFXML, WindowUtils windowUtils,
            @Value("${application.ui.title}") String applicationTitle) {
        this.applicationTitle = applicationTitle;
        this.fxml = startPageFXML;
        this.windowUtils = windowUtils;
    }



    @Override
    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
        Stage stage = stageReadyEvent.getStage();
        windowUtils.openWindow(stage, fxml, applicationTitle);
    }
}