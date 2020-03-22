package com.example.javafx.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.time.LocalDate;

public class SettingsDeserializer extends JsonDeserializer<Settings> {
    @Override
    public Settings deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        Settings settings = new Settings();
        try {
            settings.setUsername(node.get("username").asText());
            settings.setNotifyOnTicketAssignedToMe(node.get("notifyOnTicketAssignedToMe").asBoolean());
            settings.setNotifyOnTicketsBreachingSLA(node.get("notifyOnTicketBreachingSLA").asBoolean());
            settings.setTicketRefreshIntervalMins(node.get("ticketRefreshIntervalMins").asInt());

            SearchFilter filter = new SearchFilter();

            JsonNode filterNode = node.get("defaultSearchFilter");

            ArrayNode queues = (ArrayNode) filterNode.get("queues");
            filter.getQueues().clear();
            queues.forEach(n -> filter.getQueues().add(n.asText()));

            ArrayNode statuses = (ArrayNode) filterNode.get("statuses");
            filter.getStatuses().clear();
            statuses.forEach(n -> filter.getStatuses().add(n.asText()));

            filter.setOnlyAssignedToMe(filterNode.get("onlyAssignedToMe").asBoolean());

            LocalDate startDate = LocalDate.parse(filterNode.get("startDate").asText());
            LocalDate endDate = LocalDate.parse(filterNode.get("endDate").asText());
            filter.setStartDate(startDate);
            filter.setEndDate(endDate);

            settings.setDefaultSearchFilter(filter);
        } catch (Exception e) {
            // ignore and return default settings
        }
        return settings;
    }
}
