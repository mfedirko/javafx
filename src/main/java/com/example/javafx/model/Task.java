package com.example.javafx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class Task {
    @JsonProperty("The name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Started")
    private boolean started;
    @JsonProperty("Start Time")
    private Date startTime;
    @JsonProperty("Is Completed?")
    private boolean completed;
    private Date completionTime;
    public Task() {}
    public Task(String name, String description, boolean started, Date startTime, boolean completed, Date completionTime) {
        this.name = name;
        this.description = description;
        this.started = started;
        this.startTime = startTime;
        this.completed = completed;
        this.completionTime = completionTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }
}
