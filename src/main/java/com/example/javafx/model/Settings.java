package com.example.javafx.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.beans.property.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@JsonSerialize(using = SettingsSerializer.class)
@JsonDeserialize(using = SettingsDeserializer.class)
public class Settings  {
    private Resource saveFile;

    // User
    private StringProperty username = new SimpleStringProperty();

    // Search
    private SearchFilter defaultSearchFilter;

    // Notify
    private BooleanProperty notifyOnTicketAssignedToMe = new SimpleBooleanProperty(false);
    private BooleanProperty notifyOnTicketsBreachingSLA = new SimpleBooleanProperty(false);

    private IntegerProperty ticketRefreshIntervalMins = new SimpleIntegerProperty(5);


    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public SearchFilter getDefaultSearchFilter() {
        return defaultSearchFilter;
    }

    public void setDefaultSearchFilter(SearchFilter defaultSearchFilter) {
        this.defaultSearchFilter = defaultSearchFilter;
    }

    public boolean isNotifyOnTicketAssignedToMe() {
        return notifyOnTicketAssignedToMe.get();
    }

    public BooleanProperty notifyOnTicketAssignedToMeProperty() {
        return notifyOnTicketAssignedToMe;
    }

    public void setNotifyOnTicketAssignedToMe(boolean notifyOnTicketAssignedToMe) {
        this.notifyOnTicketAssignedToMe.set(notifyOnTicketAssignedToMe);
    }

    public boolean isNotifyOnTicketsBreachingSLA() {
        return notifyOnTicketsBreachingSLA.get();
    }

    public BooleanProperty notifyOnTicketsBreachingSLAProperty() {
        return notifyOnTicketsBreachingSLA;
    }

    public void setNotifyOnTicketsBreachingSLA(boolean notifyOnTicketsBreachingSLA) {
        this.notifyOnTicketsBreachingSLA.set(notifyOnTicketsBreachingSLA);
    }

    public int getTicketRefreshIntervalMins() {
        return ticketRefreshIntervalMins.get();
    }

    public IntegerProperty ticketRefreshIntervalMinsProperty() {
        return ticketRefreshIntervalMins;
    }

    public void setTicketRefreshIntervalMins(int ticketRefreshIntervalMins) {
        this.ticketRefreshIntervalMins.set(ticketRefreshIntervalMins);
    }

    public void setSaveFile(Resource saveFile) {
        this.saveFile = saveFile;
    }

    @PreDestroy
    public void beforeDestroyed() {
        System.out.println("Attempting to save settings to file before exit");
        if (saveFile != null) {
            try {
                new ObjectMapper().writeValue(saveFile.getFile(), this);
                System.out.println("Settings saved successfully!");
            } catch (IOException e) {
                System.out.println("Failed to save settings to file!");
                e.printStackTrace();
            }
        } else {
            System.out.println("No save file specified!");
        }
    }
}
