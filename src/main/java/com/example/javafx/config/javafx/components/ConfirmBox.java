package com.example.javafx.config.javafx.components;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class ConfirmBox {

    public static boolean display(String title, String message) {
        AtomicBoolean answer = new AtomicBoolean(false);

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        Button yes = new Button("Yes");
        yes.setOnAction(evt -> {
            answer.set(true);
            window.close();
        });

        Button no = new Button("No");
        no.setOnAction(evt -> {
            answer.set(false);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);

        window.showAndWait();
        return answer.get();
    }
}
