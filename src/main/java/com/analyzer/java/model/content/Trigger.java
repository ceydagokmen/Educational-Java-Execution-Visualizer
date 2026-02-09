package com.analyzer.java.model.content;

import java.util.List;

public class Trigger {
    private Integer lineNumber;
    private String astNodeType;
    private String eventType;
    private List<String> variableNames;

    public Trigger() {
    }

    public Trigger(Integer lineNumber, String astNodeType, String eventType, List<String> variableNames) {
        this.lineNumber = lineNumber;
        this.astNodeType = astNodeType;
        this.eventType = eventType;
        this.variableNames = variableNames;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getAstNodeType() {
        return astNodeType;
    }

    public void setAstNodeType(String astNodeType) {
        this.astNodeType = astNodeType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public List<String> getVariableNames() {
        return variableNames;
    }

    public void setVariableNames(List<String> variableNames) {
        this.variableNames = variableNames;
    }

    public boolean matches(int currentLine, String nodeType, String eventType, String variableName) {
        if (this.lineNumber != null && this.lineNumber != currentLine) {
            return false;
        }
        if (this.astNodeType != null && !this.astNodeType.equals(nodeType)) {
            return false;
        }
        if (this.eventType != null && !this.eventType.equals(eventType)) {
            return false;
        }
        if (this.variableNames != null && !this.variableNames.isEmpty()) {
            if (variableName == null || !this.variableNames.contains(variableName)) {
                return false;
            }
        }
        return true;
    }
}




