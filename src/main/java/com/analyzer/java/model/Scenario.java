package com.analyzer.java.model;

public class Scenario {
    private final String title;
    private final String code;

    public Scenario(String title, String code) {
        this.title = title;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return title;
    }
}


