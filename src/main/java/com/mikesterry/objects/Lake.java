package com.mikesterry.objects;

import java.util.List;

public class Lake {
    private String id;
    private String name;
    private List invasiveSpecies;
    private String nearestTown;
    private County county;

    public Lake(String id, String name, List<String> invasiveSpecies, String nearestTown, County county) {
        this.id = id;
        this.name = name;
        this.invasiveSpecies = invasiveSpecies;
        this.nearestTown = nearestTown;
        this.county = county;
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

    public County getCounty() {
        return county;
    }
}
