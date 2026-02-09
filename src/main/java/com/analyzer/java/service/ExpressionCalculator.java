package com.analyzer.java.service;

import com.analyzer.java.model.RamCell;
import javafx.collections.ObservableList;

import java.util.Map;

public class ExpressionCalculator {
    private final ObservableList<RamCell> stackData;
    private final ObservableList<RamCell> heapData;

    public ExpressionCalculator(ObservableList<RamCell> stackData, ObservableList<RamCell> heapData) {
        this.stackData = stackData;
        this.heapData = heapData;
    }

    public String calculate(String expression, Map<String, String> memory) {
        expression = expression.trim();

        if (expression.startsWith("\"") && expression.endsWith("\"")) {
            return expression.substring(1, expression.length() - 1);
        }
        if (expression.matches("-?\\d+(\\.\\d+)?")) {
            return expression;
        }

        // Boolean check
        if (memory.containsKey(expression) && (memory.get(expression).equals("true") || memory.get(expression).equals("false"))) {
            return memory.get(expression);
        }

        if (expression.contains(">") || expression.contains("<") || expression.contains("==")) {
            return performComparison(expression, memory);
        }

        String[] operators = {"\\*", "/", "\\+", "-"};
        String[] opSigns = {"*", "/", "+", "-"};

        for (int i = 0; i < operators.length; i++) {
            String op = opSigns[i];
            if (expression.contains(op)) {
                if (op.equals("+") && (expression.contains("\"") || estimateType(expression, memory).equals("String"))) {
                    return concatenateStrings(expression, memory);
                }

                String[] parts = expression.split(operators[i]);
                if (parts.length < 2) continue;

                double result = getNumber(parts[0], memory);
                for (int j = 1; j < parts.length; j++) {
                    double right = getNumber(parts[j], memory);
                    switch (op) {
                        case "*": result *= right; break;
                        case "/": result /= right; break;
                        case "+": result += right; break;
                        case "-": result -= right; break;
                    }
                }

                if (result % 1 == 0) return String.valueOf((int) result);
                return String.valueOf(result);
            }
        }

        return readValue(expression, memory);
    }

    private String performComparison(String expression, Map<String, String> memory) {
        String op = "";
        if (expression.contains(">=")) op = ">=";
        else if (expression.contains("<=")) op = "<=";
        else if (expression.contains(">")) op = ">";
        else if (expression.contains("<")) op = "<";
        else if (expression.contains("==")) op = "==";

        String[] parts = expression.split(op.equals(">=") || op.equals("<=") || op.equals("==") ? op : "\\" + op);
        if (parts.length == 2) {
            double left = getNumber(parts[0], memory);
            double right = getNumber(parts[1], memory);
            boolean result = false;
            switch (op) {
                case ">": result = left > right; break;
                case "<": result = left < right; break;
                case ">=": result = left >= right; break;
                case "<=": result = left <= right; break;
                case "==": result = left == right; break;
            }
            return String.valueOf(result);
        }
        return expression;
    }

    private String concatenateStrings(String expression, Map<String, String> memory) {
        String[] parts = expression.split("\\+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            sb.append(readValue(p, memory));
        }
        return sb.toString();
    }

    // DÜZELTİLEN METOT BURADADIR
    public String readValue(String token, Map<String, String> memory) {
        token = token.trim();
        if (token.startsWith("\"")) return token.replace("\"", "");
        if (token.matches("-?\\d+(\\.\\d+)?")) return token;

        if (memory.containsKey(token)) return memory.get(token);

        if (stackData != null) {
            for (RamCell cell : stackData) {
                if (cell.getName().equals(token)) {
                    String value = cell.getValue();

                    // ÖNEMLİ DÜZELTME:
                    // Stack'ten alınan değer bir adres ise (0x...), Heap'ten asıl veriyi kontrol et.
                    if (value.startsWith("0x") && heapData != null) {
                        for (RamCell heapCell : heapData) {
                            if (heapCell.getAddress().equals(value)) {
                                return heapCell.getValue(); // Adresi değil, gerçek veriyi döndür
                            }
                        }
                    }

                    // Adres değilse veya Heap'te bulunamadıysa Stack değerini döndür
                    return value;
                }
            }
        }

        return token;
    }

    public double getNumber(String token, Map<String, String> memory) {
        String val = readValue(token, memory);
        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            return 0;
        }
    }

    public String estimateType(String expression, Map<String, String> memory) {
        if (expression.contains("\"")) return "String";
        String[] tokens = expression.split("[\\+\\-\\*\\/]");
        for (String t : tokens) {
            String val = readValue(t, memory);
            if (!val.matches("-?\\d+(\\.\\d+)?")) return "String";
        }
        return "Sayi";
    }
}