package com.mikesterry.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LakeTest {
    Lake lake;
    County county;

    @Before
    public void setUp() {
        county = new County(5, "Benton");
        List<String> invasiveZebraMusselSpeciesList = new ArrayList<>();
        invasiveZebraMusselSpeciesList.add("zebra mussel");
        lake = new Lake("05001300","Little Rock",invasiveZebraMusselSpeciesList,"Rice", county);
    }

    @Test
    public void getId_ContainsLeadingZero() {
        Assert.assertEquals("05001300", lake.getId());
    }

    @Test
    public void getName() {
        Assert.assertEquals("Little Rock", lake.getName());
    }

    @Test
    public void getInvasiveSpecies() {
        List<String> invasiveSpeciesList = lake.getInvasiveSpecies();
        Assert.assertEquals(1, invasiveSpeciesList.size());
    }

    @Test
    public void getInvasiveSpecies_ValidateEmptyList() {
        county = new County(5, "Benton");
        List<String> emptyInvasiveSpeciesList = new ArrayList<>();
        lake = new Lake("05001300","Little Rock",emptyInvasiveSpeciesList,"Rice", county);
        List<String> invasiveSpeciesList = lake.getInvasiveSpecies();
        Assert.assertEquals(emptyInvasiveSpeciesList.size(), invasiveSpeciesList.size());
    }

    @Test
    public void getInvasiveSpecies_ValidateClonedListReturned() {
        List<String> invasiveSpeciesList = lake.getInvasiveSpecies();
        invasiveSpeciesList.add("Clams");
        Assert.assertEquals(1, lake.getInvasiveSpecies().size());
    }

    @Test
    public void getNearestTown() {
        Assert.assertEquals("Rice", lake.getNearestTown());
    }

    @Test
    public void getMostRecentSurveyDate() {
        Assert.assertEquals("", lake.getMostRecentSurveyDate());
    }

    @Test
    public void setMostRecentSurveyDate() {
    }

    @Test
    public void getCounty() {
        County expectedCounty = new County(5, "Benton");
        Assert.assertEquals(expectedCounty, county);
    }

    @Test
    public void getFishList() {
        Fish fish = new Fish("Minnow");
        lake.addFish(fish);
        Assert.assertEquals(1, lake.getFishList().size());
    }

    @Test
    public void getFishList_ValidateListIsClone() {
        Fish fish = new Fish("Minnow");
        lake.addFish(fish);
        List<Fish> fishList = lake.getFishList();
        Fish newFish = new Fish("Pout Pout Fish");
        fishList.add(newFish);
        Assert.assertEquals(1, lake.getFishList().size());
    }

    @Test
    public void addFish() {
        Fish fish = new Fish("Minnow");
        lake.addFish(fish);
        Assert.assertEquals(1, lake.getFishList().size());
    }

    @Test
    public void getTotalFishCount() {
        Fish fish = new Fish("Minnow");
        fish.addLengthAndCount(5, 10);
        fish.addLengthAndCount(10, 20);
        fish.addLengthAndCount(15, 30);
        lake.addFish(fish);
        Assert.assertEquals(60, lake.getTotalFishCount());
    }

    @Test
    public void getTotalFishCount_ValidateListIsCloned() {
        Fish fish = new Fish("Minnow");
        fish.addLengthAndCount(5, 10);
        lake.addFish(fish);
        fish.addLengthAndCount(10, 20);
        fish.addLengthAndCount(15, 30);
        Assert.assertEquals(60, lake.getTotalFishCount());
    }

    @Test
    public void getTotalFishCount_ShouldReturnZeroIfLengthCountsAreNotAdded() {
        Fish fish = new Fish("Minnow");
        lake.addFish(fish);
        Assert.assertEquals(0, lake.getTotalFishCount());
    }

    @Test
    public void getFishCountWithinRange() {
        Fish fish = new Fish("Minnow");
        fish.addLengthAndCount(5, 10);
        fish.addLengthAndCount(10, 20);
        fish.addLengthAndCount(15, 30);
        lake.addFish(fish);
        Assert.assertEquals(30, lake.getFishCountWithinRange(5, 10));
    }

    @Test
    public void getFishCountWithinRange_ShouldReturnZero() {
        Fish fish = new Fish("Minnow");
        fish.addLengthAndCount(5, 10);
        fish.addLengthAndCount(10, 20);
        fish.addLengthAndCount(15, 30);
        lake.addFish(fish);
        Assert.assertEquals(0, lake.getFishCountWithinRange(1, 4));
    }

    @Test
    public void getFishCountGreaterThan() {
        Fish fish = new Fish("Minnow");
        fish.addLengthAndCount(5, 10);
        fish.addLengthAndCount(10, 20);
        fish.addLengthAndCount(15, 30);
        lake.addFish(fish);
        Assert.assertEquals(30, lake.getFishCountGreaterThan(15));
    }

    @Test
    public void testToString() {
        Assert.assertEquals("05001300: Little Rock", lake.toString());
    }
}