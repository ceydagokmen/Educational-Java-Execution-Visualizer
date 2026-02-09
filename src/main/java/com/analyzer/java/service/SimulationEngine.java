package com.analyzer.java.service;

import com.analyzer.java.model.SimulationStep;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationEngine {
    private final ExpressionCalculator expressionCalculator;

    public SimulationEngine(ExpressionCalculator expressionCalculator) {
        this.expressionCalculator = expressionCalculator;
    }

    public List<SimulationStep> createSteps(CompilationUnit cu) {
        List<SimulationStep> steps = new ArrayList<>();
        Map<String, String> virtualMemory = new HashMap<>();
        
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            method.getBody().ifPresent(body -> traverseBlock(body, steps, virtualMemory));
        });
        
        return steps;
    }

    private void traverseBlock(BlockStmt block, List<SimulationStep> steps, Map<String, String> virtualMemory) {
        for (Statement stmt : block.getStatements()) {
            if (stmt.isIfStmt()) {
                processIfStmt(stmt.asIfStmt(), steps, virtualMemory);
            } else if (stmt.isForStmt()) {
                processForStmt(stmt.asForStmt(), steps, virtualMemory);
            } else {
                processSingleLine(stmt, steps, virtualMemory);
            }
        }
    }

    private void processIfStmt(IfStmt ifStmt, List<SimulationStep> steps, Map<String, String> virtualMemory) {
        String condition = ifStmt.getCondition().toString();
        steps.add(new SimulationStep(ifStmt, "KARAR KONTROLÜ: " + condition, null, null));

        String result = expressionCalculator.calculate(condition, virtualMemory);
        boolean conditionResult = result.equalsIgnoreCase("true");

        if (conditionResult) {
            if (ifStmt.getThenStmt().isBlockStmt()) {
                traverseBlock(ifStmt.getThenStmt().asBlockStmt(), steps, virtualMemory);
            } else {
                processSingleLine(ifStmt.getThenStmt(), steps, virtualMemory);
            }
        } else {
            if (ifStmt.getElseStmt().isPresent()) {
                Statement elseStmt = ifStmt.getElseStmt().get();
                if (elseStmt.isBlockStmt()) {
                    traverseBlock(elseStmt.asBlockStmt(), steps, virtualMemory);
                } else {
                    processSingleLine(elseStmt, steps, virtualMemory);
                }
            }
        }
    }

    private void processForStmt(ForStmt forStmt, List<SimulationStep> steps, Map<String, String> virtualMemory) {
        for (Node init : forStmt.getInitialization()) {
            if (init instanceof VariableDeclarationExpr) {
                VariableDeclarationExpr varExpr = (VariableDeclarationExpr) init;
                for (VariableDeclarator v : varExpr.getVariables()) {
                    String name = v.getNameAsString();
                    String initialValue = v.getInitializer().map(Object::toString).orElse("0");
                    virtualMemory.put(name, initialValue);
                    steps.add(new SimulationStep(v, "DÖNGÜ BAŞLANGICI", name, initialValue));
                }
            }
        }
        
        for (int i = 1; i <= 3; i++) {
            String iterationInfo = " (Tur " + i + ")";
            if (forStmt.getBody().isBlockStmt()) {
                traverseBlock(forStmt.getBody().asBlockStmt(), steps, virtualMemory);
            } else {
                processSingleLine(forStmt.getBody(), steps, virtualMemory);
            }
            
            for (Node update : forStmt.getUpdate()) {
                if (update instanceof UnaryExpr) {
                    String name = ((UnaryExpr) update).getExpression().toString();
                    try {
                        int val = Integer.parseInt(virtualMemory.getOrDefault(name, "0"));
                        virtualMemory.put(name, String.valueOf(val + 1));
                    } catch (Exception ignored) {
                    }

                    steps.add(new SimulationStep(update, "DÖNGÜ GÜNCELLEME" + iterationInfo, name, String.valueOf(i + 1)));
                }
            }
        }
    }

    private void processSingleLine(Statement stmt, List<SimulationStep> steps, Map<String, String> virtualMemory) {
        if (stmt.isExpressionStmt()) {
            Node expression = stmt.asExpressionStmt().getExpression();

            if (expression instanceof VariableDeclarationExpr) {
                ((VariableDeclarationExpr) expression).getVariables().forEach(v -> {
                    String val = v.getInitializer().map(Object::toString).orElse("?");
                    String calculatedVal = expressionCalculator.calculate(val, virtualMemory);
                    virtualMemory.put(v.getNameAsString(), calculatedVal);
                    steps.add(new SimulationStep(v, "TANIMLAMA", null, null));
                });
            } else if (expression instanceof AssignExpr) {
                AssignExpr assign = (AssignExpr) expression;
                String name = assign.getTarget().toString();
                String val = assign.getValue().toString();
                String calculatedVal = expressionCalculator.calculate(val, virtualMemory);
                virtualMemory.put(name, calculatedVal);
                steps.add(new SimulationStep(expression, "ATAMA/GÜNCELLEME", null, null));
            } else if (expression instanceof VariableDeclarator) {
                VariableDeclarator v = (VariableDeclarator) expression;
                String val = v.getInitializer().map(Object::toString).orElse("?");
                virtualMemory.put(v.getNameAsString(), expressionCalculator.calculate(val, virtualMemory));
                steps.add(new SimulationStep(expression, "TANIMLAMA", null, null));
            } else if (expression instanceof UnaryExpr) {
                steps.add(new SimulationStep(expression, "ARTIRMA/AZALTMA", null, null));
            } else if (expression instanceof MethodCallExpr) {
                steps.add(new SimulationStep(expression, "METOT ÇAĞRISI", null, null));
            }
        }
    }
}

