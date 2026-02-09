package com.analyzer.java.service;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.Problem;
import com.github.javaparser.ast.CompilationUnit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeAnalyzer {
    public static class AnalysisResult {
        private final CompilationUnit compilationUnit;
        private final List<String> errorMessages;
        private final Set<Integer> errorLineNumbers;
        private final boolean successful;

        public AnalysisResult(CompilationUnit compilationUnit, List<String> errorMessages, Set<Integer> errorLineNumbers, boolean successful) {
            this.compilationUnit = compilationUnit;
            this.errorMessages = errorMessages;
            this.errorLineNumbers = errorLineNumbers;
            this.successful = successful;
        }

        public CompilationUnit getCompilationUnit() {
            return compilationUnit;
        }

        public List<String> getErrorMessages() {
            return errorMessages;
        }

        public Set<Integer> getErrorLineNumbers() {
            return errorLineNumbers;
        }

        public boolean isSuccessful() {
            return successful;
        }
    }

    public AnalysisResult analyze(String code) {
        ParserConfiguration config = new ParserConfiguration();
        JavaParser parser = new JavaParser(config);
        ParseResult<CompilationUnit> result = parser.parse(code);

        List<String> allErrorMessages = new ArrayList<>();
        Set<Integer> errorLineNumbers = new HashSet<>();

        for (Problem p : result.getProblems()) {
            int line = -1;
            if (p.getLocation().isPresent() && p.getLocation().get().getBegin().getRange().isPresent()) {
                line = p.getLocation().get().getBegin().getRange().get().begin.line;
            }

            errorLineNumbers.add(line);
            allErrorMessages.add("📍 SATIR " + (line == -1 ? "?" : line) + ": " + p.getMessage());
        }

        if (!allErrorMessages.isEmpty()) {
            return new AnalysisResult(null, allErrorMessages, errorLineNumbers, false);
        }

        return result.getResult()
                .map(cu -> new AnalysisResult(cu, allErrorMessages, errorLineNumbers, true))
                .orElse(new AnalysisResult(null, allErrorMessages, errorLineNumbers, false));
    }
}


