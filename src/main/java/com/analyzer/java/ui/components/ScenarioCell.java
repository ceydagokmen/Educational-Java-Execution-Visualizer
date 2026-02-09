package com.analyzer.java.ui.components;

import com.analyzer.java.model.Scenario;
import com.analyzer.java.ui.theme.ThemeManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScenarioCell extends ListCell<Scenario> {
    private final HBox container;
    private final Label titleLabel;
    private final Label iconLabel;

    public ScenarioCell() {
        // İkon kısmı (Dosya emojisi veya sembol)
        iconLabel = new Label("");
        iconLabel.setStyle("-fx-font-size: 16px;");

        // Başlık kısmı
        titleLabel = new Label();
        titleLabel.setStyle("-fx-text-fill: #F8F9FA; -fx-font-weight: 500; -fx-font-size: 13px;");

        container = new HBox(12, iconLabel, titleLabel);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(10, 15, 10, 15));

        // Hücre boşken görünümü temizle
        setBackground(null);
    }

    @Override
    protected void updateItem(Scenario scenario, boolean empty) {
        super.updateItem(scenario, empty);
        if (empty || scenario == null) {
            setGraphic(null);
            setText(null);
        } else {
            titleLabel.setText(scenario.getTitle());
            setGraphic(container);
        }
    }
}