package com.analyzer.java.model;

import com.github.javaparser.ast.Node;

public class SimulationStep {
    private final Node node;
    private final String description;
    private final String customName;
    private final String customValue;

    public SimulationStep(Node node, String description, String customName, String customValue) {
        this.node = node;
        this.description = description;
        this.customName = customName;
        this.customValue = customValue;
    }

    public Node getNode() {
        return node;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomName() {
        return customName;
    }

    public String getCustomValue() {
        return customValue;
    }
}

