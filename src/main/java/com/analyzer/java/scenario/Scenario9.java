package com.analyzer.java.scenario;

public class Scenario9 extends Scenario {
    public Scenario9() {
        super("9. Metot Kullanımı",
                "package ders9;\n" +
                        "public class Selamlasma {\n" +
                        "    public void basla() {\n" +
                        "        String isim = \"Ayşe\";\n" +
                        "        selamVer(isim);\n" +
                        "    }\n" +
                        "    public void selamVer(String kime) {\n" +
                        "        System.out.println(\"Merhaba \" + kime);\n" +
                        "    }\n" +
                        "}");
    }
}