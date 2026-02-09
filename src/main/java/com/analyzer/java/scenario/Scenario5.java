package com.analyzer.java.scenario;

public class Scenario5 extends Scenario {
    public Scenario5() {
        super("5. Mantıksal (Boolean)",
                "package ders5;\n" +
                        "public class Mantik {\n" +
                        "    public void durum() {\n" +
                        "        boolean lambaAcik = true;\n" +
                        "        if (lambaAcik) {\n" +
                        "             System.out.println(\"Işıklar yanıyor mu? Evet\");\n" +
                        "        } else {\n" +
                        "             System.out.println(\"Işıklar yanıyor mu? Hayır\");\n" +
                        "        }\n" +
                        "        lambaAcik = false;\n" +
                        "        if (lambaAcik) {\n" +
                        "             System.out.println(\"Şimdi yanıyor mu? Evet\");\n" +
                        "        } else {\n" +
                        "             System.out.println(\"Şimdi yanıyor mu? Hayır\");\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
    }
}