package com.example.javafx.config.javafx;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class StageReadyEvent extends ApplicationEvent {
    public StageReadyEvent(Stage source) {
        super(source);
    }

    public Stage getStage() {
        return (Stage)this.getSource();
    }
}
