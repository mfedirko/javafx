package com.example.javafx.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.datetime.joda.LocalDateTimeParser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class SearchFilterSerializer extends JsonSerializer<SearchFilter> {
    @Override
    public void serialize(SearchFilter filter, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (filter != null) {
            jsonGenerator.writeBooleanField("onlyAssignedToMe", filter.isOnlyAssignedToMe());
            jsonGenerator.writeArrayFieldStart("queues");
            for (String queue : filter.getQueues()) {
                jsonGenerator.writeString(queue);
            }
            jsonGenerator.writeEndArray();

            jsonGenerator.writeArrayFieldStart("statuses");
            for (String status : filter.getStatuses()) {
                jsonGenerator.writeString(status);
            }
            jsonGenerator.writeEndArray();

            jsonGenerator.writeStringField("startDate", filter.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            jsonGenerator.writeStringField("endDate", filter.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        jsonGenerator.writeEndObject();
    }
}
