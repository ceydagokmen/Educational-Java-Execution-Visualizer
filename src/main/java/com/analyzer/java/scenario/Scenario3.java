package com.analyzer.java.scenario;

public class Scenario3 extends Scenario {
    public Scenario3() {
        super("3. Metin İşlemleri (Referans)",
                "package ders3;\n" +
                        "public class Metin {\n" +
                        "    public void birlestir() {\n" +
                        "        String ad = \"Ali\";\n" +
                        "        String soyad = \"Can\";\n" +
                        "        String tamAd = ad + \" \" + soyad;\n" +
                        "        System.out.println(\"Hoşgeldin: \" + tamAd);\n" +
                        "    }\n" +
                        "}");
    }
}