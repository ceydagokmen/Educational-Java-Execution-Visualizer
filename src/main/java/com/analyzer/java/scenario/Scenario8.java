package com.analyzer.java.scenario;

public class Scenario8 extends Scenario {
    public Scenario8() {
        super("8. Ondalıklı Sayılar",
                "package ders8;\n" +
                        "public class Market {\n" +
                        "    public void fiyatlandir() {\n" +
                        "        double ekmek = 5.5;\n" +
                        "        double su = 2.25;\n" +
                        "        double cikolata = 3.0 * 2.0; // Çarpma Testi\n" +
                        "        double toplam = ekmek + su + cikolata;\n" +
                        "        System.out.println(\"Ödenecek: \" + toplam);\n" +
                        "    }\n" +
                        "}");
    }
}