package com.analyzer.java.ui.panels;

import com.analyzer.java.model.content.ContentBlock;
import com.analyzer.java.ui.components.LogBlock;
import com.analyzer.java.ui.theme.ThemeManager;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.List;

public class EducationLog {
    private final VBox contentContainer;
    private final ScrollPane scrollPane;

    public EducationLog() {
        // İçeriklerin dizileceği ana dikey kutu
        contentContainer = new VBox(12); // Kartlar arası 12px boşluk
        contentContainer.setPadding(new Insets(10));
        contentContainer.setStyle("-fx-background-color: transparent;");

        // Kaydırma Alanı (Görünmez arka plan ile fütüristik görünüm)
        scrollPane = new ScrollPane(contentContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**
     * Panelin dış iskeletini oluşturur.
     */
    public VBox createPanel() {
        Label title = new Label("EĞİTİM ASİSTANI");
        title.setStyle(String.format(
                "-fx-text-fill: %s; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 0 0 10 5;",
                ThemeManager.ACCENT_PURPLE
        ));

        VBox panel = new VBox(5, title, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        return panel;
    }

    /**
     * Senaryo adımlarındaki detaylı içerik bloklarını gösterir.
     */
    public void showContentBlocks(List<ContentBlock> blocks) {
        contentContainer.getChildren().clear();
        if (blocks == null || blocks.isEmpty()) {
            showDefaultMessage();
            return;
        }

        for (ContentBlock block : blocks) {
            LogBlock card = new LogBlock(block.getTitle(), block.getContent(), block.getType());
            contentContainer.getChildren().add(card);
        }
        scrollPane.setVvalue(0); // Scroll'u en üste çek
    }

    /**
     * Simülasyon başındaki veya boş durumdaki mesaj.
     */
    public void showDefaultMessage() {
        contentContainer.getChildren().clear();
        contentContainer.getChildren().add(new LogBlock(
                "SİSTEM HAZIR",
                "Analiz başlatıldı. Kodun işleyişini görmek için 'Sonraki Adım' butonuna tıklayabilirsiniz.",
                "explanation"
        ));
    }

    /**
     * Hata durumlarını bildiren kırmızı/turuncu vurgulu mesaj.
     */
    public void showErrorMessage(String message) {
        contentContainer.getChildren().clear();
        contentContainer.getChildren().add(new LogBlock(
                "ANALİZ HATASI",
                message,
                "warning"
        ));
    }

    /**
     * Sistem olaylarını bildiren mor vurgulu mesaj.
     */
    public void showInfoMessage(String message) {
        contentContainer.getChildren().clear();
        contentContainer.getChildren().add(new LogBlock(
                "SİSTEM BİLGİSİ",
                message,
                "concept"
        ));
    }

    public void clear() {
        contentContainer.getChildren().clear();
    }
}