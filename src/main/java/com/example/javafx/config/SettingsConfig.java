package com.example.javafx.config;

import ch.qos.logback.core.util.FileUtil;
import com.example.javafx.model.SearchFilter;
import com.example.javafx.model.Settings;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Configuration
public class SettingsConfig {
    private FileSystemResource saveFile;
    private Resource defaultSaveFile;
    public SettingsConfig(@Value("${app.save.file.location}") FileSystemResource saveFile, @Value("${app.save.file.default.location}") Resource defaultSaveFile) {
        this.saveFile = saveFile;
        this.defaultSaveFile = defaultSaveFile;
    }
    @Bean
    public SearchFilter searchFilter() {
        return new SearchFilter(settings().getDefaultSearchFilter());
    }
    @Bean
    public Settings settings( ) {
        Resource saveFileToUse = saveFile;
        if (!saveFile.exists()) {
            saveFileToUse = createSaveFile();
        }

        // try to read settings from savefile
        Settings settings;
        try {
            settings = new ObjectMapper().readValue(saveFileToUse.getURL(), Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
            // return default settings if savefile unreadable
            settings = new Settings();
        }
        settings.setDefaultSearchFilter(new SearchFilter());
        settings.setSaveFile(saveFileToUse);
        return settings;
    }

    private Resource createSaveFile() {
        Resource saveFileToUse;
        System.out.println("Attempting to create default save file");
        try {
            File saveFileFile = new File(saveFile.getFilename());
            saveFileFile.createNewFile();
            FileCopyUtils.copy(defaultSaveFile.getFile(), saveFileFile);
            saveFileToUse = new FileSystemResource(saveFileFile);
            System.out.println("Save file created successfully!");
        } catch (IOException e) {
            System.out.println("Failed to create save file!");
            e.printStackTrace();
            saveFileToUse = defaultSaveFile;
        }
        return saveFileToUse;
    }

}
