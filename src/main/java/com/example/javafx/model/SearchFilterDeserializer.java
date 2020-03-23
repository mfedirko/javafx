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

public class SearchFilterDeserializer extends JsonDeserializer<SearchFilter> {
    @Override
    public SearchFilter deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode filterNode = oc.readTree(jp);

        SearchFilter filter = new SearchFilter();

        ArrayNode queues = (ArrayNode) filterNode.get("queues");
        filter.getQueues().clear();
        queues.forEach(n -> filter.getQueues().add(n.asText()));

        ArrayNode statuses = (ArrayNode) filterNode.get("statuses");
        filter.getStatuses().clear();
        statuses.forEach(n -> {
            Ticket.IncidentStatus status = Ticket.IncidentStatus.fromText(n.asText());
            if (status != null) filter.getStatuses().add(status);
        });

        filter.setName(filterNode.get("name").asText());
        filter.setOnlyAssignedToMe(filterNode.get("onlyAssignedToMe").asBoolean());
        filter.setDefault(filterNode.get("default").asBoolean());
        LocalDate startDate = LocalDate.parse(filterNode.get("startDate").asText());
        LocalDate endDate = LocalDate.parse(filterNode.get("endDate").asText());
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);

        return filter;
    }
}
