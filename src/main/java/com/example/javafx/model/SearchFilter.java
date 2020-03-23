package com.example.javafx.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonSerialize(using = SearchFilterSerializer.class)
@JsonDeserialize(using = SearchFilterDeserializer.class)
public class SearchFilter {
    private ListProperty<String> queues = new SimpleListProperty<>();
    private ListProperty<Ticket.IncidentStatus> statuses = new SimpleListProperty<>();
    private BooleanProperty onlyAssignedToMe = new SimpleBooleanProperty();
    private ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate>  endDate = new SimpleObjectProperty<>();
    private boolean isDefault;
    private StringProperty name = new SimpleStringProperty();

    public SearchFilter(SearchFilter other) {
        LocalDate otherStartDate = other.getStartDate();
        LocalDate otherEndDate = other.getEndDate();
        this.setStartDate(LocalDate.of(otherStartDate.getYear(), otherStartDate.getMonth(), otherStartDate.getDayOfMonth()));
        this.setEndDate(LocalDate.of(otherEndDate.getYear(), otherEndDate.getMonth(), otherEndDate.getDayOfMonth()));
        this.setStatuses(FXCollections.observableArrayList());
        other.getStatuses().forEach(s -> this.getStatuses().add(s));
        this.setName(other.getName());

        this.setQueues(FXCollections.observableArrayList());
        other.getQueues().forEach(q -> this.getQueues().add(q));

        this.setOnlyAssignedToMe(other.isOnlyAssignedToMe());
    }

    // default filters
    public SearchFilter() {
        setQueues(FXCollections.observableArrayList(
                "M2CMCGOPNET4", "M2CMCGOSUA", "M2CMCGODATAMGMT",
                "M3CMCGOPNET4", "M3CMCGOSUA"));
        setStatuses(FXCollections.observableArrayList(Ticket.IncidentStatus.values()));
        setOnlyAssignedToMe(false);
        setStartDate(LocalDate.now().minus(30, ChronoUnit.DAYS));
        setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
    }

    public SearchFilter(List<String> queues, List<Ticket.IncidentStatus> statuses, boolean onlyAssignedToMe, LocalDate startDate, LocalDate endDate) {
        this.setQueues(FXCollections.observableList(queues));
        this.setStatuses(FXCollections.observableList(statuses));
        this.setOnlyAssignedToMe(onlyAssignedToMe);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<String> getQueues() {
        return queues.get();
    }

    public ListProperty<String> queuesProperty() {
        return queues;
    }

    public void setQueues(ObservableList<String> queues) {
        this.queues.set(queues);
    }

    public ObservableList<Ticket.IncidentStatus> getStatuses() {
        return statuses.get();
    }

    public ListProperty<Ticket.IncidentStatus> statusesProperty() {
        return statuses;
    }

    public void setStatuses(ObservableList<Ticket.IncidentStatus> statuses) {
        this.statuses.set(statuses);
    }

    public boolean isOnlyAssignedToMe() {
        return onlyAssignedToMe.get();
    }

    public BooleanProperty onlyAssignedToMeProperty() {
        return onlyAssignedToMe;
    }

    public void setOnlyAssignedToMe(boolean onlyAssignedToMe) {
        this.onlyAssignedToMe.set(onlyAssignedToMe);
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "queues=" + queues +
                ", statuses=" + statuses +
                ", onlyAssignedToMe=" + onlyAssignedToMe +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchFilter filter = (SearchFilter) o;
        return Objects.equals(getQueues(), filter.getQueues()) &&
                Objects.equals(getStatuses(), filter.getStatuses()) &&
                Objects.equals(isOnlyAssignedToMe(), filter.isOnlyAssignedToMe()) &&
                Objects.equals(getStartDate(), filter.getStartDate()) &&
                Objects.equals(getEndDate(), filter.getEndDate()) &&
                Objects.equals(getName(), filter.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQueues(), getStatuses(), isOnlyAssignedToMe(), getStartDate(), getEndDate(), getName());
    }
}
