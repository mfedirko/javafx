package com.example.javafx.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Set;

public class SettingsSerializer extends JsonSerializer<Settings> {
    @Override
    public void serialize(Settings settings, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (settings != null) {
            jsonGenerator.writeStringField("username", settings.getUsername());
            jsonGenerator.writeNumberField("ticketRefreshIntervalMins", settings.getTicketRefreshIntervalMins());
            jsonGenerator.writeBooleanField("notifyOnTicketAssignedToMe", settings.isNotifyOnTicketAssignedToMe());
            jsonGenerator.writeBooleanField("notifyOnTicketBreachingSLA", settings.isNotifyOnTicketsBreachingSLA());
        }
        jsonGenerator.writeEndObject();
    }
}
