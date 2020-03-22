package com.example.javafx.config.javafx;

import com.example.javafx.JavafxApplication;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class FXApplication extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer
                = ac -> {
                    ac.registerBean(Application.class, () -> FXApplication.this);
                    ac.registerBean(Parameters.class, () -> getParameters());
                    ac.registerBean(HostServices.class, () -> getHostServices());
                };

        this.context = new SpringApplicationBuilder()
                .sources(JavafxApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image(new ClassPathResource("icon.png").getFilename()));
        context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception {
        context.close();
        Platform.exit();
    }

}
