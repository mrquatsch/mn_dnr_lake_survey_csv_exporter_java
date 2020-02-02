package com.mikesterry.objects;

import java.util.HashMap;
import java.util.Map;

public class Fish {
    private String commonName;
    // Map should be range...
    private Map<Integer, Integer> lengthAndCount;

    public Fish(String commonName) {
        this.commonName = commonName;
        this.lengthAndCount = new HashMap<>();
    }

    public void addLengthAndCount(int length, int count) {
        if(lengthAndCount.containsKey(length)) {
            int tempCount = lengthAndCount.get(length);
            tempCount += count;
            lengthAndCount.put(length, tempCount);
        } else {
            lengthAndCount.put(length, count);
        }
    }

    public int getTotalCount() {
        int totalCount = 0;
        for(int count : lengthAndCount.values()) {
            totalCount += count;
        }
        return totalCount;
    }

    public Map<Integer, Integer> getLengthAndCount() {
        return lengthAndCount;
    }

    public int getFishCountWithinRange(int startingNumber, int endingNumber) {
        int count = 0;

        for(int length : getLengthAndCount().keySet()) {
            if(length >= startingNumber && length <= endingNumber) {
                count += getLengthAndCount().get(length);
            }
        }

        return count;
    }

    public int getFishCountGreaterThan(int number) {
        int count = 0;

        for(int length : getLengthAndCount().keySet()) {
            if(length >= number) {
                count += getLengthAndCount().get(length);
            }
        }

        return count;
    }

    public String getCommonName() {
        return commonName;
    }

    @Override
    public String toString() {
        return commonName + "," + lengthAndCount.toString();
    }
}
