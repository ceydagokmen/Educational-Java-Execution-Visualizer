package com.analyzer.java.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RamCell {
    private final StringProperty address;
    private final StringProperty type;
    private final StringProperty name;
    private final StringProperty value;

    public RamCell(String address, String type, String name, String value) {
        this.address = new SimpleStringProperty(address);
        this.type = new SimpleStringProperty(type);
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleStringProperty(value);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }
}

