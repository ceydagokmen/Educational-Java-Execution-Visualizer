package com.analyzer.java.scenario;

public abstract class Scenario {

    private String header;
    private String code;

    public Scenario(String name, String code) {
        this.header = name;
        this.code = code;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
