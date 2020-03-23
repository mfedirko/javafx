package com.example.javafx.repository.impl;

import com.example.javafx.model.SearchFilter;
import com.example.javafx.model.SearchFilterCollection;
import com.example.javafx.model.Settings;
import com.example.javafx.repository.JSONFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsRepository extends JSONFileRepository<Settings> {

    public SettingsRepository(@Value("${app.save.file.location}") FileSystemResource saveFile, @Value("${app.save.file.default.location}") Resource defaultSaveFile) {
        super(saveFile, defaultSaveFile, Settings.class);
    }

}
