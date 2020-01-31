package com.mikesterry.objects;

import java.util.Map;

public class Fish {
    private String commonName;
    private String speciesName;
    private Map<Integer, Integer> lengthAndCount;

    public Fish(String speciesName) {
        this.speciesName = speciesName;
    }

    public void addLengthAndCount(int length, int count) {
        lengthAndCount.put(length, count);
    }

    @Override
    public String toString() {
        return "Species: " + speciesName + ": " + lengthAndCount.toString();
    }
}
