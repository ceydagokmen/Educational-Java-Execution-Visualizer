package com.analyzer.java.service;

import com.analyzer.java.model.SimulationStep;
import com.analyzer.java.model.content.ContentBlock;
import com.analyzer.java.model.content.LessonContent;
import com.github.javaparser.ast.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ContentMapper {
    
    public List<ContentBlock> getContentBlocksForStep(LessonContent lessonContent, SimulationStep step) {
        if (lessonContent == null || lessonContent.getBlocks() == null || step == null) {
            return new ArrayList<>();
        }

        Node node = step.getNode();
        int lineNumber = node.getRange().map(r -> r.begin.line).orElse(0);
        String nodeType = getNodeTypeName(node);
        String eventType = step.getDescription();
        String variableName = extractVariableName(node, step);

        return lessonContent.getBlocks().stream()
                .filter(block -> block.matches(lineNumber, nodeType, eventType, variableName))
                .sorted(Comparator.comparing(ContentBlock::getPriority).reversed())
                .collect(Collectors.toList());
    }

    private String getNodeTypeName(Node node) {
        if (node == null) {
            return null;
        }
        String className = node.getClass().getSimpleName();
        // Remove common suffixes
        return className;
    }

    private String extractVariableName(Node node, SimulationStep step) {
        if (step.getCustomName() != null) {
            return step.getCustomName();
        }
        
        // Try to extract from node
        try {
            if (node instanceof com.github.javaparser.ast.body.VariableDeclarator) {
                return ((com.github.javaparser.ast.body.VariableDeclarator) node).getNameAsString();
            } else if (node instanceof com.github.javaparser.ast.expr.AssignExpr) {
                return ((com.github.javaparser.ast.expr.AssignExpr) node).getTarget().toString();
            } else if (node instanceof com.github.javaparser.ast.expr.UnaryExpr) {
                return ((com.github.javaparser.ast.expr.UnaryExpr) node).getExpression().toString();
            }
        } catch (Exception e) {
            // Ignore
        }
        
        return null;
    }
}



