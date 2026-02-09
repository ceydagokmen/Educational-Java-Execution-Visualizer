package com.analyzer.java.scenario;

public class Scenario2 extends Scenario {
    public Scenario2() {
        super("2. Basit Matematik",
                "package ders2;\n" +
                        "public class Hesapla {\n" +
                        "    public void topla() {\n" +
                        "        int elma = 10;\n" +
                        "        int armut = 20;\n" +
                        "        int toplam = elma + armut;\n" +
                        "        System.out.println(\"Toplam Meyve: \" + toplam);\n" +
                        "    }\n" +
                        "}");
    }
}