package com.mikesterry.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FishTest {
    private Fish fish;

    @Before
    public void setUp() {
        fish = new Fish("Minnow");
        fish.addLengthAndCount(5, 10);
        fish.addLengthAndCount(10, 20);
        fish.addLengthAndCount(15, 30);
    }

    @Test
    public void addLengthAndCount() {
        int totalCount = fish.getTotalCount();
        Assert.assertEquals(60, totalCount);
    }

    @Test
    public void getTotalCount() {
        int totalCount = fish.getTotalCount();
        Assert.assertEquals(60, totalCount);
    }

    @Test
    public void getLengthAndCount() {
        Map<Integer, Integer> tempFishMap = new HashMap<>();
        tempFishMap.put(5, 10);
        tempFishMap.put(10, 20);
        tempFishMap.put(15, 30);
        Map<Integer, Integer> fishMap = fish.getLengthAndCount();
        Assert.assertEquals(tempFishMap.get(5), fishMap.get(5));
        Assert.assertEquals(tempFishMap.get(10), fishMap.get(10));
        Assert.assertEquals(tempFishMap.get(15), fishMap.get(15));
        Assert.assertEquals(fishMap.size(), 3);
    }

    @Test
    public void getLengthAndCount_ValidateWeAreReturningAClonedMap() {
        Map<Integer, Integer> fishMap = fish.getLengthAndCount();
        fishMap.put(20, 40);
        Assert.assertEquals(3, fish.getLengthAndCount().size());
    }

    @Test
    public void getFishCountWithinRange() {
        int fishCount = fish.getFishCountWithinRange(5, 10);
        Assert.assertEquals(30, fishCount);
    }

    @Test
    public void getFishCountWithinRange2() {
        int fishCount = fish.getFishCountWithinRange(5, 5);
        Assert.assertEquals(10, fishCount);
    }

    @Test
    public void getFishCountWithinRange_ShouldReturnZeroIfNoneFound() {
        int fishCount = fish.getFishCountWithinRange(1, 4);
        Assert.assertEquals(0, fishCount);
    }

    @Test
    public void getFishCountGreaterThan() {
        int fishCount = fish.getFishCountGreaterThan(5);
        Assert.assertEquals(60, fishCount);
    }

    @Test
    public void getFishCountGreaterThan2() {
        int fishCount = fish.getFishCountGreaterThan(15);
        Assert.assertEquals(30, fishCount);
    }

    @Test
    public void getFishCountGreaterThan_ShouldReturnZeroIfNoneFound() {
        int fishCount = fish.getFishCountGreaterThan(20);
        Assert.assertEquals(0, fishCount);
    }

    @Test
    public void getCommonName() {
        Assert.assertEquals("Minnow", fish.getCommonName());
    }

    @Test
    public void testToString() {
        String expectedString = "Minnow,{5=10, 10=20, 15=30}";
        Assert.assertEquals(expectedString, fish.toString());
    }
}