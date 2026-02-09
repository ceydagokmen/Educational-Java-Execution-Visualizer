package com.analyzer.java.ui.theme;

public class ThemeManager {
    // Figma'daki theme.css renklerini Java sabitleri olarak tanımlıyoruz
    public static final String BG_MAIN = "#0B0E14";
    public static final String CARD_BG = "#1A1C2E";
    public static final String ACCENT_CYAN = "#00F5FF";
    public static final String ACCENT_PURPLE = "#BD93F9";
    public static final String TEXT_PRIMARY = "#F8F9FA";

    // Modern köşe yuvarlatma değeri
    public static final int RADIUS_LG = 12;

    public static String getCardStyle() {
        return "-fx-background-color: " + CARD_BG + "; " +
                "-fx-background-radius: " + RADIUS_LG + "; " +
                "-fx-border-radius: " + RADIUS_LG + "; " +
                "-fx-border-color: rgba(255,255,255,0.1); " +
                "-fx-border-width: 0.5;";
    }
}