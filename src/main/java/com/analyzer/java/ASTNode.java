package com.analyzer.java;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    public String aciklama;           // Düğümün adı (Örn: "Değişken Tanımlama")
    public List<ASTNode> cocuklar;    // Bu düğüme bağlı alt parçalar

    public ASTNode(String aciklama) {
        this.aciklama = aciklama;
        this.cocuklar = new ArrayList<>();
    }

    public void cocukEkle(ASTNode cocuk) {
        cocuklar.add(cocuk);
    }

    // Ağacı konsola güzel yazdırmak için bir metot
    public void yazdir(String girinti) {
        System.out.println(girinti + "|- " + aciklama);
        for (ASTNode cocuk : cocuklar) {
            cocuk.yazdir(girinti + "  ");
        }
    }
}