package com.analyzer.java.model.content;

import java.util.List;

public class LessonContent {
    private String lessonId;
    private String title;
    private List<String> objectives;
    private List<String> concepts;
    private List<ContentBlock> blocks;
    private String defaultMessage;

    public LessonContent() {
    }

    public LessonContent(String lessonId, String title, List<String> objectives, 
                        List<String> concepts, List<ContentBlock> blocks, String defaultMessage) {
        this.lessonId = lessonId;
        this.title = title;
        this.objectives = objectives;
        this.concepts = concepts;
        this.blocks = blocks;
        this.defaultMessage = defaultMessage;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<String> objectives) {
        this.objectives = objectives;
    }

    public List<String> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<String> concepts) {
        this.concepts = concepts;
    }

    public List<ContentBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<ContentBlock> blocks) {
        this.blocks = blocks;
    }

    public String getDefaultMessage() {
        return defaultMessage != null ? defaultMessage : "Bu adımda kod çalışıyor. Detaylar için kodun üzerine bakın.";
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}




