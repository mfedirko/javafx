package com.example.javafx.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class WindowUtils {
    private final ApplicationContext ac;
    public WindowUtils(ApplicationContext ac) {
        this.ac = ac;
    }

    public void openWindow(Stage stage, Resource fxml, String title) {
        openModal(stage, fxml, title, null);
    }

    public void openModal(Stage stage, Resource fxml, String title, Modality modality) {
        openModal(stage, fxml, title, modality, 600, 600);
    }

    public void openModal(Stage stage, Resource fxml, String title, Modality modality, int width, int height) {
        try {
            URL url = fxml.getURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(ac::getBean);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle(title);
            if (modality != null) stage.initModality(modality);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML!", e);
        }
    }
}
