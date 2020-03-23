package com.example.javafx.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;

public class JSONFileRepository<T> {
    private static final Logger LOG = LoggerFactory.getLogger(JSONFileRepository.class);

    private Resource saveFileToUse;
    private FileSystemResource saveFile;
    private Resource defaultSaveFile;
    private T entity;
    private Class<T> tClass;
    private String entityName;

    public JSONFileRepository(FileSystemResource jsonFile, Resource defaultTemplateFile, Class<T> tClass) {
        this.saveFile = jsonFile;
        this.defaultSaveFile = defaultTemplateFile;
        this.tClass = tClass;
        entityName = tClass.getSimpleName();
    }

    @PostConstruct
    public void initializeRepository() {
        if (saveFileToUse == null) {
            saveFileToUse = saveFile;
            if (!saveFile.exists()) {
                saveFileToUse = createSaveFile();
            }
        }
        getEntity();
        LOG.info("Initialized JSON repository for {}", entityName);
    }

    public T getEntity() {
        if (entity == null) {
            T settings;
            try {
                settings = new ObjectMapper().readValue(saveFileToUse.getURL(), tClass);
            } catch (IOException e) {
                throw new RuntimeException("Save file is unreadable: " + saveFileToUse.getFilename(), e);
            }
            this.entity = settings;
        }
        return entity;
    }
    @PreDestroy
    public void beforeDestroyed() {
        LOG.info("Attempting to save {} to file before exit", entityName);
        if (saveFile != null) {
            try {
                new ObjectMapper().writeValue(saveFile.getFile(), entity);
                LOG.info("{} saved successfully {}!", entityName, saveFile.getFilename());
            } catch (IOException e) {
                LOG.error("Failed to save {} to file {}!", entityName, saveFile.getFilename());
                e.printStackTrace();
            }
        } else {
            LOG.error("No save file specified!");
        }
    }
    private Resource createSaveFile() {
        Resource saveFileToUse;
        LOG.info("Attempting to create default save file");
        try {
            File saveFileFile = new File(saveFile.getFilename());
            saveFileFile.createNewFile();
            FileCopyUtils.copy(defaultSaveFile.getFile(), saveFileFile);
            saveFileToUse = new FileSystemResource(saveFileFile);
            LOG.info("Save file created successfully {}!", saveFileToUse);
        } catch (IOException e) {
            LOG.error("Failed to create save file {}!", e);
            throw new RuntimeException("Failed to create default save file", e);
        }
        return saveFileToUse;
    }

}
