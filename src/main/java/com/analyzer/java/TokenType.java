package com.analyzer.java;

public enum TokenType {
    KEYWORD, // public, int gibi kelimeler
    IDENTIFIER, // sai, main vb. isimler
    LITERAL, // verinin kendisidir, int = 10'daki 10
    OPERATOR, // +, -, = vb.
    SEPARATOR, // ;, {, (
    COMMENT,
    EOF, // dosya sonu sinyali
    ERROR //tanınmayan karakter, hata



}
