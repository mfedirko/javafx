package com.example.javafx.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ticket {
    // non-editable fields

    private final String title;
    private final String openedBy;
    private final long incidentNumber;

    // editable fields

    private StringProperty description = new SimpleStringProperty();;
    private StringProperty status = new SimpleStringProperty();;
    private StringProperty assignedTechnician = new SimpleStringProperty();;
    private StringProperty assignedGroup = new SimpleStringProperty();;

    //Resolve
    private StringProperty resolution = new SimpleStringProperty();;
    private StringProperty workaround = new SimpleStringProperty();;
    private StringProperty breachDescription = new SimpleStringProperty();;
    private StringProperty breachCategory = new SimpleStringProperty();;

    //Update
    private StringProperty transferReasonCategory = new SimpleStringProperty();;
    private StringProperty transferDescription = new SimpleStringProperty();;

    //SCIM
    private StringProperty system = new SimpleStringProperty();;
    private StringProperty component = new SimpleStringProperty();;
    private StringProperty item = new SimpleStringProperty();;
    private StringProperty module = new SimpleStringProperty();;

    private BooleanProperty selected = new SimpleBooleanProperty();

    public Ticket( long incidentNumber, String title, String openedBy) {
        this.title = title;
        this.openedBy = openedBy;
        this.incidentNumber = incidentNumber;
    }
    public Ticket( long incidentNumber, String title, String openedBy,
                  String status, String assignedTechnician, String assignedGroup) {
        this.title = title;
        this.openedBy = openedBy;
        this.incidentNumber = incidentNumber;
        setAssignedGroup(assignedGroup);
        setAssignedTechnician(assignedTechnician);
        setStatus(status);
    }
    public String getTitle() {
        return title;
    }

    public String getOpenedBy() {
        return openedBy;
    }

    public long getIncidentNumber() {
        return incidentNumber;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getAssignedTechnician() {
        return assignedTechnician.get();
    }

    public StringProperty assignedTechnicianProperty() {
        return assignedTechnician;
    }

    public void setAssignedTechnician(String assignedTechnician) {
        this.assignedTechnician.set(assignedTechnician);
    }

    public String getAssignedGroup() {
        return assignedGroup.get();
    }

    public StringProperty assignedGroupProperty() {
        return assignedGroup;
    }

    public void setAssignedGroup(String assignedGroup) {
        this.assignedGroup.set(assignedGroup);
    }

    public String getResolution() {
        return resolution.get();
    }

    public StringProperty resolutionProperty() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution.set(resolution);
    }

    public String getWorkaround() {
        return workaround.get();
    }

    public StringProperty workaroundProperty() {
        return workaround;
    }

    public void setWorkaround(String workaround) {
        this.workaround.set(workaround);
    }

    public String getBreachDescription() {
        return breachDescription.get();
    }

    public StringProperty breachDescriptionProperty() {
        return breachDescription;
    }

    public void setBreachDescription(String breachDescription) {
        this.breachDescription.set(breachDescription);
    }

    public String getBreachCategory() {
        return breachCategory.get();
    }

    public StringProperty breachCategoryProperty() {
        return breachCategory;
    }

    public void setBreachCategory(String breachCategory) {
        this.breachCategory.set(breachCategory);
    }

    public String getTransferReasonCategory() {
        return transferReasonCategory.get();
    }

    public StringProperty transferReasonCategoryProperty() {
        return transferReasonCategory;
    }

    public void setTransferReasonCategory(String transferReasonCategory) {
        this.transferReasonCategory.set(transferReasonCategory);
    }

    public String getTransferDescription() {
        return transferDescription.get();
    }

    public StringProperty transferDescriptionProperty() {
        return transferDescription;
    }

    public void setTransferDescription(String transferDescription) {
        this.transferDescription.set(transferDescription);
    }

    public String getSystem() {
        return system.get();
    }

    public StringProperty systemProperty() {
        return system;
    }

    public void setSystem(String system) {
        this.system.set(system);
    }

    public String getComponent() {
        return component.get();
    }

    public StringProperty componentProperty() {
        return component;
    }

    public void setComponent(String component) {
        this.component.set(component);
    }

    public String getItem() {
        return item.get();
    }

    public StringProperty itemProperty() {
        return item;
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public String getModule() {
        return module.get();
    }

    public StringProperty moduleProperty() {
        return module;
    }

    public void setModule(String module) {
        this.module.set(module);
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public enum IncidentStatus {
        NEW("New"),
        TRANSFERRED("Transferred"),
        ASSIGNED("Assigned"),
        RESOLVED("Resolved"),
        CLOSED("Closed");

        private final String text;
        IncidentStatus(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public static IncidentStatus fromText(String text) {
            for (IncidentStatus status : IncidentStatus.values()) {
                if (status.getText().equals(text)) {
                    return status;
                }
            }
            return null;
        }

    }

    @Override
    public String toString() {
        return "Ticket{" +
                "title='" + title + '\'' +
                ", openedBy='" + openedBy + '\'' +
                ", incidentNumber=" + incidentNumber +
                ", description=" + description +
                ", status=" + status +
                ", assignedTechnician=" + assignedTechnician +
                ", assignedGroup=" + assignedGroup +
                ", resolution=" + resolution +
                ", workaround=" + workaround +
                ", breachDescription=" + breachDescription +
                ", breachCategory=" + breachCategory +
                ", transferReasonCategory=" + transferReasonCategory +
                ", transferDescription=" + transferDescription +
                ", system=" + system +
                ", component=" + component +
                ", item=" + item +
                ", module=" + module +
                ", selected=" + selected +
                '}' + '\n';
    }
}
