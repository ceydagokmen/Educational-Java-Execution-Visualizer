package com.analyzer.java.service;

import com.analyzer.java.model.RamCell;
import javafx.collections.ObservableList;

import java.util.Random;

public class MemoryManager {
    private final ObservableList<RamCell> stackData;
    private final Random random;

    public MemoryManager(ObservableList<RamCell> stackData) {
        this.stackData = stackData;
        this.random = new Random();
    }

    public void update(String name, String newType, String value) {
        boolean found = false;
        for (RamCell cell : stackData) {
            if (cell.getName().equals(name)) {
                int index = stackData.indexOf(cell);
                String typeToUse = (newType == null) ? cell.getType() : newType;
                stackData.set(index, new RamCell(cell.getAddress(), typeToUse, name, value));
                found = true;
                break;
            }
        }
        if (!found) {
            String address = "0x" + Integer.toHexString(random.nextInt(1000, 9999)).toUpperCase();
            String type = (newType == null) ? "Bilinmiyor" : newType;
            stackData.add(new RamCell(address, type, name, value));
        }
    }

    public String createNewAddress() {
        return "0x" + Integer.toHexString(random.nextInt(1000, 9999)).toUpperCase();
    }
}


