package com.analyzer.java.scenario;

public class Scenario7 extends Scenario {
    public Scenario7() {
        super("7. Döngü ile Toplama",
                "package ders7;\n" +
                        "public class Biriktirme {\n" +
                        "    public void biriktir() {\n" +
                        "        int kasa = 0;\n" +
                        "        for (int gun = 1; gun <= 3; gun++) {\n" +
                        "            kasa = kasa + 10;\n" +
                        "            System.out.println(gun + \". Gün Kasa: \" + kasa);\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
    }
}