package com.analyzer.java.ui.panels;

import com.analyzer.java.ui.theme.ThemeManager;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.HashSet;
import java.util.Set;

public class EditorPanel {
    private final TextArea codeArea;
    private final TextArea lineNumbersArea;

    public EditorPanel() {
        // Ana Kod Alanı
        codeArea = new TextArea();
        codeArea.setWrapText(false);
        codeArea.getStyleClass().add("code-area"); // CSS artık burayı kontrol edecek
        codeArea.setText("// Lütfen soldan bir senaryo seçin ve 'Hazırla'ya basın.");

        // Satır Numaraları Alanı
        lineNumbersArea = new TextArea();
        lineNumbersArea.setEditable(false);
        lineNumbersArea.setPrefWidth(45);
        lineNumbersArea.setWrapText(false);
        lineNumbersArea.getStyleClass().add("line-numbers-area"); // CSS artık burayı kontrol edecek

        // Scroll senkronizasyonu
        lineNumbersArea.scrollTopProperty().bindBidirectional(codeArea.scrollTopProperty());
        codeArea.textProperty().addListener((obs, oldVal, newVal) -> updateLines(new HashSet<>()));
    }

    public VBox createPanel() {
        // Editör Kutusu
        HBox editorBox = new HBox(0, lineNumbersArea, codeArea); // Boşluğu 0 yaptık ki tam birleşsinler
        HBox.setHgrow(codeArea, Priority.ALWAYS);

        // Sadece ana kutu çerçevesini tutuyoruz, iç renkleri CSS'e bıraktık
        editorBox.setStyle(ThemeManager.getCardStyle());

        Label title = new Label("KOD EDİTÖRÜ");
        // Başlık stilini CSS'e taşımadığımız için şimdilik tutabiliriz
        title.setStyle("-fx-text-fill: " + ThemeManager.ACCENT_CYAN + "; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 0 0 10 5;");

        VBox panel = new VBox(5, title, editorBox);
        VBox.setVgrow(editorBox, Priority.ALWAYS);
        return panel;
    }

    public void updateLines(Set<Integer> errorLines) {
        int lineCount = codeArea.getText().split("\n", -1).length;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= lineCount; i++) {
            sb.append(i).append(errorLines.contains(i) ? " (!)\n" : "\n");
        }
        lineNumbersArea.setText(sb.toString());
        // Renk değişimi CSS'teki sınıflar üzerinden yapılacağı için setStyle'ı buradan sildik
    }

    public TextArea getCodeArea() {
        return codeArea;
    }

    public void selectLineSafely(int lineNumber) {
        if (lineNumber <= 0) return;
        try {
            javafx.collections.ObservableList<CharSequence> paragraphs = codeArea.getParagraphs();
            if (lineNumber > paragraphs.size()) return;

            int totalChars = 0;
            for (int i = 0; i < lineNumber - 1; i++) {
                totalChars += paragraphs.get(i).length() + 1;
            }

            int lineLength = paragraphs.get(lineNumber - 1).length();
            if (totalChars + lineLength <= codeArea.getLength()) {
                codeArea.selectRange(totalChars, totalChars + lineLength);
            }
        } catch (Exception ignored) {}
    }
}