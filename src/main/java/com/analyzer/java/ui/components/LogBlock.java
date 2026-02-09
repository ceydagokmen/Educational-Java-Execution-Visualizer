package com.analyzer.java.ui.components;

import com.analyzer.java.ui.theme.ThemeManager;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Figma'daki ContentBlock yapısını temsil eden modern UI bileşeni.
 */
public class LogBlock extends VBox {

    public LogBlock(String title, String content, String type) {
        // Genel Konfigürasyon
        this.setSpacing(8);
        this.setPadding(new Insets(12));

        // Ana Kart Stili (ThemeManager'dan merkezi olarak çekilir)
        this.setStyle(ThemeManager.getCardStyle());

        // Bloğun tipine göre renk vurgusunu belirle
        String accentColor = getAccentColor(type);

        // Sol kenara kalın renkli çizgi ekleme (Figma Estetiği)
        String currentStyle = this.getStyle();
        this.setStyle(currentStyle + String.format(
                "-fx-border-width: 0 0 0 4; -fx-border-color: %s;", accentColor
        ));

        // Başlık (Title) Kısmı
        if (title != null && !title.isEmpty()) {
            Label titleLabel = new Label(title.toUpperCase());
            titleLabel.setStyle(String.format(
                    "-fx-font-weight: bold; -fx-text-fill: %s; -fx-font-size: 12px; -fx-letter-spacing: 1px;",
                    accentColor
            ));
            this.getChildren().add(titleLabel);
        }

        // İçerik (Content) Kısmı
        Label contentLabel = new Label(content);
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(Double.MAX_VALUE);
        contentLabel.setStyle(String.format(
                "-fx-text-fill: %s; -fx-font-size: 13px; -fx-line-spacing: 1.4;",
                ThemeManager.TEXT_PRIMARY
        ));
        this.getChildren().add(contentLabel);
    }

    private String getAccentColor(String type) {
        if (type == null) return ThemeManager.TEXT_PRIMARY;
        switch (type.toLowerCase()) {
            case "explanation": return ThemeManager.ACCENT_CYAN;   // Bilgilendirme
            case "concept":     return ThemeManager.ACCENT_PURPLE; // Teknik Kavram
            case "warning":     return "#FFB86C";                 // Uyarı (Amber)
            case "reallife":    return "#50FA7B";                 // Gerçek Hayat (Yeşil)
            default:            return ThemeManager.TEXT_PRIMARY;
        }
    }
}