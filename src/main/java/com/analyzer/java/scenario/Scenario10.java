package com.analyzer.java.scenario;

public class Scenario10 extends Scenario {
    public Scenario10() {
        super("10. Sınıf ve Nesne",
                "package ders10;\n" +
                        "public class Galeri {\n" +
                        "    public void arabaUret() {\n" +
                        "        Araba a1 = new Araba();\n" +
                        "        System.out.println(\"Araba üretildi!\");\n" +
                        "    }\n" +
                        "}\n" +
                        "class Araba {\n" +
                        "    String renk = \"Kırmızı\";\n" +
                        "}");
    }
}