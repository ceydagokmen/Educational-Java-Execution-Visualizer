package com.analyzer.java.scenario;

import com.analyzer.java.model.Scenario;
import java.util.ArrayList;
import java.util.List;

public class ScenarioManager {

    /**
     * Tüm senaryo sınıflarını instantiate eder ve Senaryo modeline dönüştürerek liste olarak döner.
     * Bu metot SenaryoPaneli tarafından senaryo listesini doldurmak için kullanılır.
     */
    public static List<Scenario> getScenarios() {
        List<Scenario> scenarioList = new ArrayList<>();

        // Her bir senaryo sınıfından bir örnek oluşturup modele dönüştürerek ekliyoruz
        scenarioList.add(convertToModel(new Scenario1()));
        scenarioList.add(convertToModel(new Scenario2()));
        scenarioList.add(convertToModel(new Scenario3()));
        scenarioList.add(convertToModel(new Scenario4()));
        scenarioList.add(convertToModel(new Scenario5()));
        scenarioList.add(convertToModel(new Scenario6()));
        scenarioList.add(convertToModel(new Scenario7()));
        scenarioList.add(convertToModel(new Scenario8()));
        scenarioList.add(convertToModel(new Scenario9()));
        scenarioList.add(convertToModel(new Scenario10()));

        return scenarioList;
    }

    /**
     * Scenario soyut sınıfından türeyen nesneyi mevcut Scenario modeline çevirir.
     * Bu sayede projenin geri kalanındaki model yapısını bozmamış oluruz.
     */
    private static com.analyzer.java.model.Scenario convertToModel(com.analyzer.java.scenario.Scenario s) {
        return new com.analyzer.java.model.Scenario(s.getHeader(), s.getCode());
    }
}