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

    // User
    private StringProperty username = new SimpleStringProperty();


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



}
