package com.analyzer.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
            "public", "class", "static", "void", "int", "String", "if"
    ));

    public static void main(String[] args) {
        // HATALI KOD TESTİ: int değişkene String atıyoruz.
        String sahteKod = "int x = \"merhaba\";";

        System.out.println("1. Lexical Analiz Başlıyor...");
        List<Token> tokenListesi = tokenize(sahteKod);
        System.out.println("   -> " + tokenListesi.size() + " adet token bulundu.");

        System.out.println("\n2. Syntax Analizi (Parsing) Başlıyor...");
        Parser parser = new Parser(tokenListesi);
        ASTNode agac = parser.parse();

        System.out.println("--- OLUŞTURULAN AST ---");
        agac.yazdir("");
        System.out.println("-----------------------");
    }

    public static List<Token> tokenize(String kod) {
        List<Token> tokenler = new ArrayList<>();
        char[] karakterler = kod.toCharArray();
        int i = 0;

        while (i < karakterler.length) {
            char suankiKarakter = karakterler[i];

            if (Character.isWhitespace(suankiKarakter)) {
                i++;
                continue;
            }

            if (Character.isLetter(suankiKarakter)) {
                StringBuilder kelimeYapici = new StringBuilder();
                while (i < karakterler.length && Character.isLetterOrDigit(karakterler[i])) {
                    kelimeYapici.append(karakterler[i]);
                    i++;
                }
                String kelime = kelimeYapici.toString();
                if (KEYWORDS.contains(kelime)) {
                    tokenler.add(new Token(TokenType.KEYWORD, kelime));
                } else {
                    tokenler.add(new Token(TokenType.IDENTIFIER, kelime));
                }
                continue;
            }

            if (Character.isDigit(suankiKarakter)) {
                StringBuilder sayiYapici = new StringBuilder();
                while (i < karakterler.length && Character.isDigit(karakterler[i])) {
                    sayiYapici.append(karakterler[i]);
                    i++;
                }
                tokenler.add(new Token(TokenType.LITERAL, sayiYapici.toString()));
                continue;
            }


            if (suankiKarakter == '\"') {
                StringBuilder metinYapici = new StringBuilder();
                metinYapici.append(suankiKarakter);
                i++;
                while (i < karakterler.length && karakterler[i] != '\"') {
                    metinYapici.append(karakterler[i]);
                    i++;
                }
                if (i < karakterler.length) {
                    metinYapici.append(karakterler[i]);
                    i++;
                }
                tokenler.add(new Token(TokenType.LITERAL, metinYapici.toString()));
                continue;
            }


            if (suankiKarakter == '=') tokenler.add(new Token(TokenType.OPERATOR, "="));
            else if (suankiKarakter == ';') tokenler.add(new Token(TokenType.SEPARATOR, ";"));
            else if (suankiKarakter == '{') tokenler.add(new Token(TokenType.SEPARATOR, "{"));
            else if (suankiKarakter == '}') tokenler.add(new Token(TokenType.SEPARATOR, "}"));
            else if (suankiKarakter == '(') tokenler.add(new Token(TokenType.SEPARATOR, "("));
            else if (suankiKarakter == ')') tokenler.add(new Token(TokenType.SEPARATOR, ")"));
            else if (suankiKarakter == '>') tokenler.add(new Token(TokenType.OPERATOR, ">"));
            else tokenler.add(new Token(TokenType.ERROR, Character.toString(suankiKarakter)));

            i++;
        }
        tokenler.add(new Token(TokenType.EOF, ""));
        return tokenler;
    }
}