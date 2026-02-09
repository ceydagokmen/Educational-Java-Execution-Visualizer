package com.analyzer.java.service;

import com.analyzer.java.model.RamCell;
import com.analyzer.java.model.SimulationStep;
import com.analyzer.java.model.content.LessonContent;
import com.analyzer.java.ui.panels.MemoryPanel;
import com.analyzer.java.ui.panels.OutputConsole;
import com.analyzer.java.ui.panels.EditorPanel;
import com.analyzer.java.ui.panels.EducationLog;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.IfStmt; // Import eklendi
import com.github.javaparser.ast.NodeList;

import java.util.HashMap;

public class SimulationProcessor {
    private final ExpressionCalculator expressionCalculator;
    private final MemoryManager memoryManager;
    private final EditorPanel editorPanel;
    private final EducationLog educationLog;
    private final OutputConsole outputConsole;
    private final MemoryPanel memoryPanel;
    private final ContentMapper contentMapper;
    private CompilationUnit compilationUnit;
    private LessonContent currentLessonContent;
    private String currentScenarioTitle;

    public SimulationProcessor(ExpressionCalculator expressionCalculator,
                               MemoryManager memoryManager,
                               EditorPanel editorPanel,
                               EducationLog educationLog,
                               OutputConsole outputConsole,
                               MemoryPanel memoryPanel,
                               ContentMapper contentMapper) {
        this.expressionCalculator = expressionCalculator;
        this.memoryManager = memoryManager;
        this.editorPanel = editorPanel;
        this.educationLog = educationLog;
        this.outputConsole = outputConsole;
        this.memoryPanel = memoryPanel;
        this.contentMapper = contentMapper;
    }

    public void setCompilationUnit(CompilationUnit cu) {
        this.compilationUnit = cu;
    }

    public void setCurrentLessonContent(LessonContent lessonContent) {
        this.currentLessonContent = lessonContent;
    }

    public void setCurrentScenarioTitle(String scenarioTitle) {
        this.currentScenarioTitle = scenarioTitle;
    }

    public void processStep(SimulationStep step) {
        Node node = step.getNode();
        int lineNumber = node.getRange().map(r -> r.begin.line).orElse(0);
        editorPanel.selectLineSafely(lineNumber);

        // İçerik eşleştirme sistemi (JSON tabanlı)
        if (contentMapper != null && currentLessonContent != null) {
            var contentBlocks = contentMapper.getContentBlocksForStep(currentLessonContent, step);
            if (!contentBlocks.isEmpty()) {
                educationLog.showContentBlocks(contentBlocks);
            } else {
                educationLog.showDefaultMessage();
            }
        } else {
            educationLog.showDefaultMessage();
        }

        // Düğüm tipine göre işlem yapma
        if (node instanceof VariableDeclarator) {
            processVariableDeclarator((VariableDeclarator) node, step);
        } else if (node instanceof IfStmt) {
            // If satırında bellek değişmez, görsel seçim ve JSON açıklaması yeterlidir
            System.out.println("DEBUG: Karar yapısı işleniyor...");
        } else if (node instanceof UnaryExpr) {
            processUnaryExpr((UnaryExpr) node, step);
        } else if (node instanceof AssignExpr) {
            processAssignExpr((AssignExpr) node);
        } else if (node instanceof MethodCallExpr) {
            processMethodCallExpr((MethodCallExpr) node);
        }
    }

    private String getNodeTypeName(Node node) {
        if (node == null) return null;
        return node.getClass().getSimpleName();
    }

    private void processVariableDeclarator(VariableDeclarator v, SimulationStep step) {
        String name = v.getNameAsString();
        String type = v.getType().asString();
        String rawExpression = (step.getCustomValue() != null) ? step.getCustomValue() : v.getInitializer().map(Object::toString).orElse("?");

        if (v.getInitializer().isPresent() && v.getInitializer().get() instanceof ObjectCreationExpr) {
            ObjectCreationExpr obj = (ObjectCreationExpr) v.getInitializer().get();
            String objectType = obj.getType().asString();
            String address = memoryManager.createNewAddress();

            memoryPanel.getHeapData().add(new RamCell(address, objectType, name, objectType));
            memoryManager.update(name, objectType, address);
            return;
        }

        String cleanValue = expressionCalculator.calculate(rawExpression, new HashMap<>());

        if (type.equals("String")) {
            String address = memoryManager.createNewAddress();
            memoryPanel.getHeapData().add(new RamCell(address, type, name, cleanValue));
            memoryManager.update(name, type, address);
        } else {
            memoryManager.update(name, type, cleanValue);
        }
    }

    private void processUnaryExpr(UnaryExpr node, SimulationStep step) {
        String name = (step.getCustomName() != null) ? step.getCustomName() : node.getExpression().toString();
        String newValue = (step.getCustomValue() != null) ? step.getCustomValue() : "Artırıldı";
        memoryManager.update(name, null, newValue);
    }

    private void processAssignExpr(AssignExpr assign) {
        String name = assign.getTarget().toString();
        String rawExpression = assign.getValue().toString();
        String calculatedValue = expressionCalculator.calculate(rawExpression, new HashMap<>());
        memoryManager.update(name, null, calculatedValue);
    }

    private void processMethodCallExpr(MethodCallExpr call) {
        if (call.getNameAsString().equals("println") || call.toString().startsWith("System.out")) {
            String rawArgument = call.getArguments().toString();
            if (rawArgument.startsWith("[") && rawArgument.endsWith("]")) {
                rawArgument = rawArgument.substring(1, rawArgument.length() - 1);
            }
            String result = expressionCalculator.calculate(rawArgument, new HashMap<>());
            outputConsole.getTextArea().appendText(result + "\n");
        } else {
            String methodName = call.getNameAsString();
            MethodDeclaration methodDef = compilationUnit.findAll(MethodDeclaration.class).stream()
                    .filter(m -> m.getNameAsString().equals(methodName)).findFirst().orElse(null);

            if (methodDef != null) {
                NodeList<com.github.javaparser.ast.expr.Expression> args = call.getArguments();
                NodeList<Parameter> params = methodDef.getParameters();

                for (int i = 0; i < args.size() && i < params.size(); i++) {
                    String paramName = params.get(i).getNameAsString();
                    String paramType = params.get(i).getType().asString();
                    String argExpr = args.get(i).toString();
                    String argValue = expressionCalculator.calculate(argExpr, new HashMap<>());

                    memoryManager.update(paramName, paramType, argValue);
                }
            }
        }
    }
}