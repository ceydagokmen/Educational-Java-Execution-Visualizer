package com.analyzer.java;

import java.util.List;

public class Parser {
    private List<Token> tokenler;
    private int i = 0;

    public Parser(List<Token> tokenler) {
        this.tokenler = tokenler;
    }

    public ASTNode parse() {
        ASTNode anaDugum = new ASTNode("PROGRAM");

        while (i < tokenler.size()) {
            Token suankiToken = tokenler.get(i);

            // 1. DEĞİŞKEN TANIMLAMA KONTROLÜ
            if (suankiToken.tokenType == TokenType.KEYWORD && suankiToken.value.equals("int")) {
                anaDugum.cocukEkle(degiskenTanimlamaAnalizEt());
            }
            // 2. --- EKLENEN KISIM --- IF BLOGU KONTROLÜ
            else if (suankiToken.tokenType == TokenType.KEYWORD && suankiToken.value.equals("if")) {
                anaDugum.cocukEkle(ifBlogunuAnalizEt());
            }
            // 3. CLASS TANIMLAMA (Şimdilik atlıyoruz)
            else if (suankiToken.tokenType == TokenType.KEYWORD && suankiToken.value.equals("class")) {
                i++;
            }
            else {
                i++; // Tanımadığımız tokenları geçiyoruz
            }
        }
        return anaDugum;
    }

    // --- YENİ VE KRİTİK METOT: IF BLOGUNU ANALİZ ETME ---
    private ASTNode ifBlogunuAnalizEt() {
        ASTNode ifDugumu = new ASTNode("IF BLOGU");
        i++; // "if" kelimesini geç

        // --- KOŞUL KISMI (...) ---
        if (i < tokenler.size() && tokenler.get(i).value.equals("(")) {
            i++; // '(' parantezini geç
        }

        String kosulMetni = "";
        // Parantez kapanana kadar ne varsa "Koşul" olarak al
        while (i < tokenler.size() && !tokenler.get(i).value.equals(")")) {
            kosulMetni += tokenler.get(i).value + " ";
            i++;
        }
        ifDugumu.cocukEkle(new ASTNode("KOŞUL: " + kosulMetni.trim()));

        if (i < tokenler.size()) i++; // ')' parantezini geç

        // --- BLOK İÇERİĞİ {...} ---
        if (i < tokenler.size() && tokenler.get(i).value.equals("{")) {
            i++; // '{' süslü parantezi geç
        }

        ASTNode icerikDugumu = new ASTNode("BLOK İÇERİĞİ");

        // Süslü parantez kapanana kadar içeriyi analiz et
        while (i < tokenler.size() && !tokenler.get(i).value.equals("}")) {
            Token icerdekiToken = tokenler.get(i);

            // EĞER İÇERİDE BİR DEĞİŞKEN VARSA:
            if (icerdekiToken.tokenType == TokenType.KEYWORD && icerdekiToken.value.equals("int")) {
                icerikDugumu.cocukEkle(degiskenTanimlamaAnalizEt());
            }
            // EĞER İÇERİDE BAŞKA BİR IF VARSA (İÇ İÇE BLOKLAR İÇİN KRİTİK!):
            else if (icerdekiToken.tokenType == TokenType.KEYWORD && icerdekiToken.value.equals("if")) {
                icerikDugumu.cocukEkle(ifBlogunuAnalizEt()); // Kendi kendini çağırıyor! (Recursion)
            }
            else {
                i++; // Diğer şeyleri geç
            }
        }

        ifDugumu.cocukEkle(icerikDugumu); // İçeriği IF düğümüne ekle

        if (i < tokenler.size()) i++; // '}' süslü parantezi geç

        return ifDugumu;
    }

    // --- DEĞİŞKEN ANALİZİ (Senin kodun aynen duruyor) ---
    private ASTNode degiskenTanimlamaAnalizEt() {
        ASTNode degiskenDugumu = new ASTNode("DEĞİŞKEN TANIMLAMA");

        Token tipToken = tokenler.get(i);
        String degiskenTipi = tipToken.value;
        degiskenDugumu.cocukEkle(new ASTNode("Tip: " + degiskenTipi));
        i++;

        if (i < tokenler.size() && tokenler.get(i).tokenType == TokenType.IDENTIFIER) {
            degiskenDugumu.cocukEkle(new ASTNode("İsim: " + tokenler.get(i).value));
            i++;
        }

        if (i < tokenler.size() && tokenler.get(i).value.equals("=")) {
            i++;
        }

        if (i < tokenler.size() && tokenler.get(i).tokenType == TokenType.LITERAL) {
            Token degerToken = tokenler.get(i);
            String atananDeger = degerToken.value;

            if (degiskenTipi.equals("int") && atananDeger.startsWith("\"")) {
                // HATA SİNYALİ
                degiskenDugumu.cocukEkle(new ASTNode("!!! HATA: TİP UYUŞMAZLIĞI (int -> String) !!!"));
                System.err.println("HATA: int değişkene String atanamaz!");
            } else {
                degiskenDugumu.cocukEkle(new ASTNode("Değer: " + atananDeger));
            }
            i++;
        }

        if (i < tokenler.size() && tokenler.get(i).value.equals(";")) {
            i++;
        }

        return degiskenDugumu;
    }
}