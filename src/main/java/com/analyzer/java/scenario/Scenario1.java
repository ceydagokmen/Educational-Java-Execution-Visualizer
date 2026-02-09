package com.analyzer.java.scenario;

public class Scenario1 extends Scenario {
    public Scenario1() {
        super("1. Merhaba Dünya",
                "package ders1;\n" +
                        "public class Giris {\n" +
                        "    public void basla() {\n" +
                        "        int yas = 25;\n" +
                        "        System.out.println(\"Merhaba! Yaşım: \" + yas);\n" +
                        "    }\n" +
                        "}");
    }
}