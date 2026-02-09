package com.analyzer.java.ui.panels;

import com.analyzer.java.model.Scenario;
import com.analyzer.java.scenario.ScenarioManager;
import com.analyzer.java.ui.components.ScenarioCell;
import com.analyzer.java.ui.theme.ThemeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ScenarioPanel {
    private final ListView<Scenario> scenarioList;
    private final ObservableList<Scenario> scenarios;

    public ScenarioPanel() {
        this.scenarioList = new ListView<>();
        this.scenarios = FXCollections.observableArrayList();

        loadScenarios();
        scenarioList.setItems(scenarios);
        scenarioList.setCellFactory(lv -> new ScenarioCell());

        // CSS sınıfını ekliyoruz, beyazlığı CSS'den öldürmüştük
        scenarioList.getStyleClass().add("scenario-list");
    }

    private void loadScenarios() {
        scenarios.addAll(ScenarioManager.getScenarios());
    }

    public VBox createPanel() {
        Label title = new Label("SENARYOLAR");
        title.setStyle("-fx-text-fill: " + ThemeManager.ACCENT_PURPLE +
                "; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 5 0 10 5;");

        VBox panel = new VBox(5, title, scenarioList);
        VBox.setVgrow(scenarioList, Priority.ALWAYS);
        panel.setStyle("-fx-padding: 10;");

        return panel;
    }

    public ListView<Scenario> getScenarioList() {
        return scenarioList;
    }
}