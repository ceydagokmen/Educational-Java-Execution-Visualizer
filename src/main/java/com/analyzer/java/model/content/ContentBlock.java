package com.analyzer.java.model.content;

import java.util.List;

public class ContentBlock {
    private String type;
    private String title;
    private String content;
    private String icon;
    private List<Trigger> triggers;
    private Integer priority;

    public ContentBlock() {
    }

    public ContentBlock(String type, String title, String content, String icon, List<Trigger> triggers, Integer priority) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.icon = icon;
        this.triggers = triggers;
        this.priority = priority != null ? priority : 0;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    public Integer getPriority() {
        return priority != null ? priority : 0;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public boolean matches(int lineNumber, String nodeType, String eventType, String variableName) {
        if (triggers == null || triggers.isEmpty()) {
            return false;
        }
        return triggers.stream().anyMatch(trigger -> 
            trigger.matches(lineNumber, nodeType, eventType, variableName));
    }
}




