package com.analyzer.java;

public class Token {

   public final TokenType tokenType;
   public final String value;

   public Token(TokenType tokenType, String value) {
       this.tokenType = tokenType; // Sınıfın tokenType'ına, parametre tokenType'ını ata.
       this.value = value;         // Sınıfın value'suna, parametre value'yu ata.
   }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", value='" + value + '\'' +
                '}';
    }
}
