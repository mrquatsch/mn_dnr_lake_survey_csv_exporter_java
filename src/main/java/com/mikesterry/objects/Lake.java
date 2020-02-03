package com.mikesterry.objects;

import java.util.ArrayList;
import java.util.List;

public class Lake {
    private String id;
    private String name;
    private List invasiveSpecies;
    private String nearestTown;
    private String mostRecentSurveyDate;
    private County county;
    private List<Fish> fishList;

    public Lake(String id, String name, List<String> invasiveSpecies, String nearestTown, County county) {
        this.id = id;
        this.name = name;
        this.invasiveSpecies = invasiveSpecies;
        this.nearestTown = nearestTown;
        this.mostRecentSurveyDate = "";
        this.county = county;
        this.fishList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getInvasiveSpecies() {
        return invasiveSpecies;
    }

    public String getNearestTown() {
        return nearestTown;
    }

    public String getMostRecentSurveyDate() {
        return mostRecentSurveyDate;
    }

    public void setMostRecentSurveyDate(String mostRecentSurveyDate) {
        this.mostRecentSurveyDate = mostRecentSurveyDate;
    }

    public County getCounty() {
        return county;
    }

    public List<Fish> getFishList() {
        return fishList;
    }

    public void addFish(Fish fish) {
        fishList.add(fish);
    }

    public int getTotalFishCount() {
        int count = 0;
        for(Fish fish : fishList) {
            count += fish.getTotalCount();
        }
        return count;
    }

    public int getFishCountWithinRange(int startingNumber, int endingNumber) {
        int count = 0;
        for(Fish fish : fishList) {
            for(int length : fish.getLengthAndCount().keySet()) {
                if(length >= startingNumber && length <= endingNumber) {
                    count += fish.getLengthAndCount().get(length);
                }
            }
        }
        return count;
    }

    public int getFishCountGreaterThan(int number) {
        int count = 0;
        for(Fish fish : fishList) {
            for(int length : fish.getLengthAndCount().keySet()) {
                if(length >= number) {
                    count += fish.getLengthAndCount().get(length);
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return name + ": " + id;
    }
}
