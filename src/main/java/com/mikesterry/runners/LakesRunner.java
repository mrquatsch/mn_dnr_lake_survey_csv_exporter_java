package com.mikesterry.runners;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mikesterry.connectors.Website;
import com.mikesterry.objects.County;
import com.mikesterry.objects.Lake;
import com.mikesterry.util.Constants;

import java.io.IOException;
import java.util.List;

public class LakesRunner implements Runnable {
    private List<Lake> lakeList;
    private String lakeIDLookupURL;
    private County county;
    private ObjectMapper mapper;

    public LakesRunner(County county, List<Lake> lakeList) {
        lakeIDLookupURL = Constants.LAKE_INFO_LOOKUP_URL;
        this.lakeList = lakeList;
        this.county = county;
        this.mapper = new ObjectMapper();
    }

    public void run() {
        try {
            System.out.println("Gathering lakes for county: " + county.getName());
            Website lakeIDLookup = new Website(lakeIDLookupURL + county.getId());

            String jsonString = lakeIDLookup.getUrlContents();
            JsonNode actualObj = mapper.readTree(jsonString);
            JsonNode jsonLakeArray = actualObj.get("results");

            for(JsonNode jsonNodeLake : jsonLakeArray) {
                Lake lake = createLakeFromJsonNode(jsonNodeLake);
                lakeList.add(lake);
            }
        } catch (Exception e ) {
            System.out.println("Failed to pull lake results");
            System.out.println(e.getMessage());
        }
    }

    public List<Lake> getLakeList() {
        return this.lakeList;
    }

    private String removeDoubleQuotesFromString(String input) {
        return input.replace("\"", "");
    }

    private Lake createLakeFromJsonNode(JsonNode jsonNodeLake) throws IOException {
        String lakeName = String.valueOf(jsonNodeLake.get("name"));
        lakeName = removeDoubleQuotesFromString(lakeName);

        // Id needs to be a string - there are leading zeros.
        // this goes for all ids
        String lakeID = String.valueOf(jsonNodeLake.get("id"));
        lakeID = removeDoubleQuotesFromString(lakeID);
        ObjectReader reader = mapper.readerFor( new TypeReference<List<String>>() {});
        List<String> invasiveSpecies = reader.readValue(jsonNodeLake.get("invasiveSpecies"));
        String nearestTown = String.valueOf(jsonNodeLake.get("nearest_town"));
        nearestTown = removeDoubleQuotesFromString(nearestTown);

        System.out.println("Creating lake object for " + lakeName);
        return new Lake(lakeID, lakeName, invasiveSpecies, nearestTown, county);
    }
}
