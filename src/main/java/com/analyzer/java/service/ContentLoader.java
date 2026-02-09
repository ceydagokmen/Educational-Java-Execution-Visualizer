package com.analyzer.java.service;

import com.analyzer.java.model.content.LessonContent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContentLoader {
    private static final String LESSONS_PATH = "/lessons/";
    private final ObjectMapper objectMapper;
    private final Map<String, LessonContent> cache;

    public ContentLoader() {
        this.objectMapper = new ObjectMapper();
        this.cache = new HashMap<>();
    }

    public Optional<LessonContent> loadLesson(String lessonId) {
        if (cache.containsKey(lessonId)) {
            return Optional.of(cache.get(lessonId));
        }

        try {
            String resourcePath = LESSONS_PATH + lessonId + ".json";
            InputStream inputStream = getClass().getResourceAsStream(resourcePath);
            
            if (inputStream == null) {
                return Optional.empty();
            }

            LessonContent content = objectMapper.readValue(inputStream, LessonContent.class);
            cache.put(lessonId, content);
            return Optional.of(content);
        } catch (Exception e) {
            System.err.println("Error loading lesson: " + lessonId + " - " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<LessonContent> loadLessonByScenarioTitle(String scenarioTitle) {
        // Map scenario titles to lesson IDs
        // Example: "8. Ondalıklı Sayılar" -> "lesson_08_decimal"
        String lessonId = mapScenarioTitleToLessonId(scenarioTitle);
        return loadLesson(lessonId);
    }

    private String mapScenarioTitleToLessonId(String title) {
        // Başlıkta numara kontrolü yapar (Örn: "3. Metin İşlemleri" -> "3")
        if (title != null && title.matches("^\\d+\\..*")) {
            String numberStr = title.substring(0, title.indexOf('.')).trim();
            try {
                int number = Integer.parseInt(numberStr);
                // Her zaman "lesson_01", "lesson_02" formatında döner
                return "lesson_" + String.format("%02d", number);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public void clearCache() {
        cache.clear();
    }
}




