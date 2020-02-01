package com.mikesterry.runners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mikesterry.objects.County;
import com.mikesterry.objects.Lake;
import com.mikesterry.connectors.Website;

import java.util.List;

public class GetLakes implements Runnable {
    private List<Lake> lakeList;
    private String lakeIDLookupURL;
    private County county;

    public GetLakes(County county, List<Lake> lakeList) {
        lakeIDLookupURL = ("http://maps2.dnr.state.mn.us/cgi-bin/lakefinder_json.cgi?context=desktop&name=&county=");
        this.lakeList = lakeList;
        this.county = county;
    }

    public void run() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            System.out.println("Gathering lakes for county: " + county.getName());
            Website lakeIDLookup = new Website(lakeIDLookupURL + county.getId());
//            System.out.println("County lake lookup url: " + lakeIDLookup.getUrl());

            String jsonString = lakeIDLookup.getUrlContents();
            JsonNode actualObj = mapper.readTree(jsonString);
            JsonNode jsonLakeArray = actualObj.get("results");

            for(JsonNode lake : jsonLakeArray) {
                String lakeName = String.valueOf(lake.get("name"));

                // Id needs to be a string - there are leading zeros.
                // this goes for all ids
                String lakeID = String.valueOf(lake.get("id")).replace("\"", "");
                ObjectReader reader = mapper.readerFor( new TypeReference<List<String>>() {});
                List<String> invasiveSpecies = reader.readValue(lake.get("invasiveSpecies"));
                String nearestTown = String.valueOf(lake.get("nearest_town"));

                System.out.println("Creating lake object for " + lakeName);
                Lake newLake = new Lake(lakeID, lakeName, invasiveSpecies, nearestTown, county);
                lakeList.add(newLake);
            }
        } catch (Exception e ) {
            System.out.println("Failed to pull lake results");
            System.out.println(e.getMessage());
        }
    }

    public List<Lake> getLakeList() {
        return this.lakeList;
    }
}
