package com.analyzer.java.ui.components;

import com.analyzer.java.ui.theme.ThemeManager;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import com.analyzer.java.model.RamCell;

/**
 * Bellek hücrelerini Figma tasarımındaki fütüristik renklerle boyayan bileşen.
 */
public class MemoryCell extends TableCell<RamCell, String> {
    private final String columnType;

    public MemoryCell(String columnType) {
        this.columnType = columnType;
        this.setAlignment(Pos.CENTER_LEFT);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setStyle("");
        } else {
            setText(item);
            applyModernStyle(item);
        }
    }

    private void applyModernStyle(String value) {
        // Genel hücre padding'i
        String base = "-fx-padding: 8 10; -fx-text-fill: " + ThemeManager.TEXT_PRIMARY + ";";

        switch (columnType) {
            case "address":
                // Adresler için Mor (Accent) ve Monospace font
                setStyle(base + "-fx-font-family: 'JetBrains Mono'; -fx-text-fill: " + ThemeManager.ACCENT_PURPLE + ";");
                break;
            case "name":
                // Değişken isimleri için Cyan ve Kalın yazı
                setStyle(base + "-fx-font-weight: bold; -fx-text-fill: " + ThemeManager.ACCENT_CYAN + ";");
                break;
            case "value":
                // Değer tipine göre renklendirme
                if (value.equals("null")) {
                    setStyle(base + "-fx-text-fill: #6B7280; -fx-font-style: italic;");
                } else if (value.matches("-?\\d+(\\.\\d+)?")) {
                    setStyle(base + "-fx-text-fill: #60A5FA;"); // Sayı: Mavi
                } else {
                    setStyle(base + "-fx-text-fill: #34D399;"); // String: Yeşil
                }
                break;
            default:
                setStyle(base);
        }
    }
}