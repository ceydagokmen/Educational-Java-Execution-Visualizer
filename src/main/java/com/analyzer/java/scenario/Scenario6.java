package com.analyzer.java.scenario;

public class Scenario6 extends Scenario {
    public Scenario6() {
        super("6. For Döngüsü (Sayma)",
                "package ders6;\n" +
                        "public class Sayma {\n" +
                        "    public void say() {\n" +
                        "        for (int i = 1; i <= 3; i++) {\n" +
                        "            System.out.println(\"Sayı: \" + i);\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
    }
}