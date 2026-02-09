package com.analyzer.java.ui.panels;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class OutputConsole {
    private final TextArea outputConsole;

    public OutputConsole() {
        outputConsole = new TextArea();
        outputConsole.setEditable(false);
        outputConsole.setFont(Font.font("Consolas", 14));
        outputConsole.getStyleClass().add("output-console");
    }

    public VBox createPanel() {
        Label title = new Label("Program Çıktısı");
        VBox panel = new VBox(5, title, outputConsole);
        VBox.setVgrow(outputConsole, Priority.ALWAYS);
        return panel;
    }

    public TextArea getTextArea() {
        return outputConsole;
    }
}


