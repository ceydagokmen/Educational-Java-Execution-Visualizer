package com.analyzer.java;

import com.analyzer.java.model.SimulationStep;
import com.analyzer.java.service.*;
import com.analyzer.java.ui.TreeViewHandler;
import com.analyzer.java.ui.panels.*;
import com.analyzer.java.ui.theme.ThemeManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class Interface extends Application {

    private ScenarioPanel scenarioPanel;
    private EditorPanel editorPanel;
    private TreeViewHandler treeViewHandler;
    private MemoryPanel memoryPanel;
    private EducationLog educationLog;
    private OutputConsole outputConsole;
    private BottomBar bottomBar;

    private CodeAnalyzer codeAnalyzer;
    private SimulationEngine simulationEngine;
    private SimulationProcessor simulationProcessor;
    private ExpressionCalculator expressionCalculator;
    private MemoryManager memoryManager;
    private ContentLoader contentLoader;
    private ContentMapper contentMapper;

    private List<SimulationStep> stepList;
    private int currentStepIndex = 0;

    @Override
    public void start(Stage stage) {
        createUIComponents();
        createServices();
        createScene(stage);
    }

    private void createUIComponents() {
        scenarioPanel = new ScenarioPanel();
        editorPanel = new EditorPanel();
        treeViewHandler = new TreeViewHandler();
        memoryPanel = new MemoryPanel(
                javafx.collections.FXCollections.observableArrayList(),
                javafx.collections.FXCollections.observableArrayList()
        );
        educationLog = new EducationLog();
        outputConsole = new OutputConsole();
        bottomBar = new BottomBar();
    }

    private void createServices() {
        expressionCalculator = new ExpressionCalculator(memoryPanel.getStackData(), memoryPanel.getHeapData());
        memoryManager = new MemoryManager(memoryPanel.getStackData());
        simulationEngine = new SimulationEngine(expressionCalculator);
        contentLoader = new ContentLoader();
        contentMapper = new ContentMapper();
        simulationProcessor = new SimulationProcessor(
                expressionCalculator, memoryManager, editorPanel, educationLog,
                outputConsole, memoryPanel, contentMapper
        );
        codeAnalyzer = new CodeAnalyzer();
    }

    private void createScene(Stage stage) {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + ThemeManager.BG_MAIN + ";");

        // Sol Panel: Senaryolar
        VBox scenarioBox = scenarioPanel.createPanel();

        // ORTA PANEL: Editör ve Konsol (Sabit Dikey Yerleşim)
        VBox editorBox = editorPanel.createPanel();
        VBox consoleBox = outputConsole.createPanel();
        consoleBox.setPrefHeight(160); // Konsol boyutu sabitlendi
        consoleBox.setMinHeight(160);

        VBox centerPane = new VBox(15, editorBox, consoleBox);
        VBox.setVgrow(editorBox, Priority.ALWAYS); // Editör kalan boşluğu doldurur

        // Yapı Röntgeni (Tree)
        VBox treeBox = treeViewHandler.createPanel();

        // SAĞ PANEL: Bellek ve Eğitim (Sabit Dikey Yerleşim)
        VBox memoryBox = memoryPanel.createPanel();
        VBox logBox = educationLog.createPanel();
        logBox.setPrefHeight(280); // Eğitim kutusu boyutu sabitlendi
        logBox.setMinHeight(280);

        VBox rightPane = new VBox(15, memoryBox, logBox);
        VBox.setVgrow(memoryBox, Priority.ALWAYS); // Tablolar dikeyde yayılır
        rightPane.setPrefWidth(380);

        // Ana Yatay Bölücü (Sadece paneller arası genişlik ayarı için SplitPane)
        SplitPane mainSplitter = new SplitPane(scenarioBox, centerPane, treeBox, rightPane);
        mainSplitter.setDividerPositions(0.15, 0.48, 0.65);
        mainSplitter.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        mainLayout.setCenter(mainSplitter);
        mainLayout.setBottom(bottomBar.createPanel());
        mainLayout.setPadding(new Insets(10));

        Platform.runLater(() -> {
            editorPanel.updateLines(java.util.Collections.emptySet());
            scenarioPanel.getScenarioList().getSelectionModel().select(0);
        });

        bindEvents();

        Scene scene = new Scene(mainLayout, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setTitle("Code Analyzer");
        stage.setScene(scene);
        stage.show();
    }

    private void bindEvents() {
        scenarioPanel.getScenarioList().getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                editorPanel.getCodeArea().setText(newVal.getCode());
                memoryPanel.getStackData().clear();
                memoryPanel.getHeapData().clear();
                educationLog.clear();
                outputConsole.getTextArea().clear();
                bottomBar.getBtnPrepare().setText("SİMÜLASYONU BAŞLAT");
                bottomBar.getBtnNext().setDisable(true);
                simulationProcessor.setCurrentScenarioTitle(newVal.getTitle());
                contentLoader.loadLessonByScenarioTitle(newVal.getTitle()).ifPresent(simulationProcessor::setCurrentLessonContent);
            }
        });
        bottomBar.getBtnPrepare().setOnAction(e -> startSimulation());
        bottomBar.getBtnNext().setOnAction(e -> nextStep());
    }

    private void startSimulation() {
        try {
            memoryPanel.getStackData().clear();
            memoryPanel.getHeapData().clear();
            educationLog.clear();
            outputConsole.getTextArea().clear();
            String code = editorPanel.getCodeArea().getText();
            CodeAnalyzer.AnalysisResult result = codeAnalyzer.analyze(code);
            if (!result.isSuccessful()) {
                editorPanel.updateLines(result.getErrorLineNumbers());
                educationLog.showErrorMessage("Analiz sırasında hata bulundu!");
                return;
            }
            if (result.getCompilationUnit() != null) {
                var cu = result.getCompilationUnit();
                treeViewHandler.updateTree(cu);
                simulationProcessor.setCompilationUnit(cu);
                stepList = simulationEngine.createSteps(cu);
            }
            editorPanel.updateLines(result.getErrorLineNumbers());
            educationLog.showInfoMessage("Simülasyon hazır! 'İLERLE'ye bas.");
            currentStepIndex = 0;
            bottomBar.getBtnNext().setDisable(false);
            bottomBar.getBtnNext().setText("İLERLE");
            bottomBar.getBtnPrepare().setText("YENİDEN BAŞLAT");
        } catch (Exception ex) {
            educationLog.showErrorMessage("Hata: " + ex.getMessage());
        }
    }

    private void nextStep() {
        if (stepList != null && currentStepIndex < stepList.size()) {
            simulationProcessor.processStep(stepList.get(currentStepIndex));
            currentStepIndex++;
            if (currentStepIndex >= stepList.size()) {
                bottomBar.getBtnNext().setDisable(true);
                bottomBar.getBtnNext().setText("TAMAMLANDI");
            }
        }
    }

    public static void main(String[] args) { launch(args); }
}