package com.analyzer.java.ui.panels;

import com.analyzer.java.ui.theme.ThemeManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;


public class BottomBar {
    private final Button btnPrepare;
    private final Button btnNext;

    public BottomBar() {
        // Hazırla/Başlat Butonu (Neon Cyan)
        this.btnPrepare = new Button("SİMÜLASYONU BAŞLAT");
        stylePrimaryButton(btnPrepare);

        // Sonraki Adım Butonu (Ghost/Outline Style)
        this.btnNext = new Button("İLERLE");
        styleSecondaryButton(btnNext);
        btnNext.setDisable(true); // Başlangıçta pasif
    }

    public HBox createPanel() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bar = new HBox(15, spacer, btnPrepare, btnNext);
        bar.setAlignment(Pos.CENTER_RIGHT);
        bar.setPadding(new Insets(15, 20, 15, 20));

        // Alt bar için yarı şeffaf koyu zemin ve üst çizgi
        bar.setStyle("-fx-background-color: rgba(26, 28, 46, 0.9); " +
                "-fx-border-color: rgba(255,255,255,0.1) transparent transparent transparent;");

        return bar;
    }

    private void stylePrimaryButton(Button btn) {
        btn.setStyle("-fx-background-color: " + ThemeManager.ACCENT_CYAN + ";" +
                "-fx-text-fill: " + ThemeManager.BG_MAIN + ";" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 25;" +
                "-fx-background-radius: 20;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,245,255,0.4), 10, 0, 0, 0);");
    }

    private void styleSecondaryButton(Button btn) {
        btn.setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: " + ThemeManager.ACCENT_PURPLE + ";" +
                "-fx-border-color: " + ThemeManager.ACCENT_PURPLE + ";" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 20;" +
                "-fx-background-radius: 20;" +
                "-fx-padding: 8 22;" +
                "-fx-cursor: hand;");
    }

    public Button getBtnPrepare() { return btnPrepare; }
    public Button getBtnNext() { return btnNext; }
}