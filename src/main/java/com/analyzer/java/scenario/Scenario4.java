package com.analyzer.java.scenario;

public class Scenario4 extends Scenario {
    public Scenario4() {
        super("4. Karar Yapıları (If-Else)",
                "package ders4;\n" +
                        "public class Karar {\n" +
                        "    public void kontrolEt() {\n" +
                        "        int notum = 45;\n" +
                        "        int baraj = 50;\n" +
                        "        if (notum > baraj) {\n" +
                        "             System.out.println(\"Geçti\");\n" +
                        "        } else {\n" +
                        "             System.out.println(\"Kaldı\");\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
    }
}