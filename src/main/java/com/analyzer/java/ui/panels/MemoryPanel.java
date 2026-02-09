package com.analyzer.java.ui.panels;

import com.analyzer.java.model.RamCell;
import com.analyzer.java.ui.components.MemoryCell;
import com.analyzer.java.ui.theme.ThemeManager;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MemoryPanel {
    private final TableView<RamCell> stackTable;
    private final TableView<RamCell> heapTable;

    public MemoryPanel(ObservableList<RamCell> stackData, ObservableList<RamCell> heapData) {
        this.stackTable = createTable("Stack");
        this.stackTable.setItems(stackData);

        this.heapTable = createTable("Heap");
        this.heapTable.setItems(heapData);
    }

    public VBox createPanel() {
        Label title = new Label("CANLI BELLEK (RAM)");
        title.setStyle("-fx-text-fill: " + ThemeManager.ACCENT_CYAN + "; -fx-font-weight: bold; -fx-font-size: 14px;");

        VBox stackBox = createSection("STACK (STATİK)", stackTable, ThemeManager.ACCENT_CYAN);
        VBox heapBox = createSection("HEAP (DİNAMİK)", heapTable, ThemeManager.ACCENT_PURPLE);

        VBox panel = new VBox(15, title, stackBox, heapBox);
        panel.setPadding(new Insets(10));
        VBox.setVgrow(stackBox, Priority.ALWAYS);
        VBox.setVgrow(heapBox, Priority.ALWAYS);
        return panel;
    }

    private VBox createSection(String title, TableView<RamCell> table, String accentColor) {
        Label label = new Label(title);
        label.setStyle("-fx-text-fill: " + accentColor + "; -fx-font-weight: bold; -fx-font-size: 11px;");

        VBox section = new VBox(8, label, table);
        VBox.setVgrow(table, Priority.ALWAYS);
        return section;
    }

    private TableView<RamCell> createTable(String type) {
        TableView<RamCell> table = new TableView<>();
        table.setStyle(ThemeManager.getCardStyle()); // Glassmorphism efekti

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<RamCell, String> colAddr = new TableColumn<>("ADRES");
        colAddr.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAddr.setCellFactory(c -> new MemoryCell("address"));

        TableColumn<RamCell, String> colName = new TableColumn<>("DEĞİŞKEN");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setCellFactory(c -> new MemoryCell("name"));

        TableColumn<RamCell, String> colVal = new TableColumn<>("DEĞER");
        colVal.setCellValueFactory(new PropertyValueFactory<>("value"));
        colVal.setCellFactory(c -> new MemoryCell("value"));

        table.getColumns().addAll(colAddr, colName, colVal);

        // Stack/Heap özel neon parlaması (Opacity %20)
        String accent = type.equals("Stack") ? ThemeManager.ACCENT_CYAN : ThemeManager.ACCENT_PURPLE;
        table.setStyle(table.getStyle() + "-fx-border-color: " + accent + "33;");

        return table;
    }

    // --- SİMÜLASYON SERVİSİ İÇİN GEREKLİ YENİ METOTLAR ---

    /**
     * SimulationProcessor'ın Stack verilerine erişip güncelleme yapabilmesi için.
     */
    public ObservableList<RamCell> getStackData() {
        return stackTable.getItems();
    }

    /**
     * SimulationProcessor'ın Heap verilerine erişip güncelleme yapabilmesi için.
     */
    public ObservableList<RamCell> getHeapData() {
        return heapTable.getItems();
    }
}