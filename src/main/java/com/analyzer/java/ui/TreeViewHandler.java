package com.analyzer.java.ui;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TreeViewHandler {
    private final TreeView<String> treeView;

    public TreeViewHandler() {
        treeView = new TreeView<>(new TreeItem<>("Simülasyon Bekleniyor..."));
    }

    public VBox createPanel() {
        Label title = createTitle("YAPI RÖNTGENİ", "#27ae60");
        VBox panel = new VBox(5, title, treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        return panel;
    }

    public void updateTree(CompilationUnit cu) {
        TreeItem<String> rootItem = new TreeItem<>("Proje");
        rootItem.setExpanded(true);
        traverseTree(cu, rootItem);
        treeView.setRoot(rootItem);
    }

    private void traverseTree(Node node, TreeItem<String> parentItem) {
        String displayName = "";
        boolean add = false;

        if (node instanceof ClassOrInterfaceDeclaration) {
            displayName = "SINIF: " + ((ClassOrInterfaceDeclaration) node).getNameAsString();
            add = true;
        } else if (node instanceof MethodDeclaration) {
            displayName = "METOT: " + ((MethodDeclaration) node).getNameAsString() + "()";
            add = true;
        } else if (node instanceof VariableDeclarator) {
            // DÜZELTME: Değişken adı + tipi (Örn: DEĞİŞKEN: x (int))
            VariableDeclarator vd = (VariableDeclarator) node;
            displayName = "DEĞİŞKEN: " + vd.getNameAsString() + " (" + vd.getType() + ")";
            add = true;
        } else if (node instanceof ForStmt) {
            displayName = "DÖNGÜ (For Loop)";
            add = true;
        } else if (node instanceof IfStmt) {
            displayName = "KARAR (If-Else)";
            add = true;
        } else if (node instanceof MethodCallExpr) {
            MethodCallExpr mce = (MethodCallExpr) node;
            if (mce.getNameAsString().equals("println")) {
                displayName = "ÇIKTI: System.out.println";
                add = true;
            }
        }

        TreeItem<String> currentItem = parentItem;
        if (add) {
            currentItem = new TreeItem<>(displayName);
            currentItem.setExpanded(true);
            parentItem.getChildren().add(currentItem);
        }
        for (Node child : node.getChildNodes()) {
            traverseTree(child, currentItem);
        }
    }

    private Label createTitle(String text, String color) {
        Label l = new Label(text);
        l.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        l.getStyleClass().add("title-tree");
        return l;
    }
}

