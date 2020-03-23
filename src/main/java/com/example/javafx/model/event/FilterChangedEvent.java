package com.example.javafx.model.event;

import com.example.javafx.model.SearchFilter;
import org.springframework.context.ApplicationEvent;

public class FilterChangedEvent extends ApplicationEvent {
    private SearchFilter filter;
    public FilterChangedEvent(Object source, SearchFilter filter) {
        super(source);
        this.filter = filter;
    }

    public SearchFilter getFilter() {
        return filter;
    }
}
