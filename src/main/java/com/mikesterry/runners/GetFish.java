package com.mikesterry.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.connectors.Website;
import com.mikesterry.objects.Fish;

import java.util.HashMap;
import java.util.Map;

public class GetFish {
    private final String fishSpeciesUrl = ("http://maps2.dnr.state.mn.us/lakefinder/lib/lakefinder.js");
    private Map<String, Fish> fishMap;

    public GetFish() {
        fishMap = new HashMap<>();
    }

    public void run() {
        System.out.println("Gathering fish species info...");
        Website fishSpeciesLookup = new Website(fishSpeciesUrl);
        System.out.println("Fish Species info url: " + fishSpeciesUrl);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String something = fishSpeciesLookup.getUrlContents();
            for(String line : something.split(";")) {
                if(line.contains("var fish_species = ")) {
                    String[] lineSplit = line.split("var fish_species = ");
                    String jsonString = lineSplit[1];
                    JsonNode actualObj = mapper.readTree(jsonString);
                    JsonNode fishSpeciesArray = actualObj;
                    for(JsonNode fishSpecies : fishSpeciesArray) {
                        String code = fishSpecies.get("code").toString();
                        String commonName = fishSpecies.get("common_name").toString();
                        Fish fish = new Fish(code, commonName);
                        fishMap.put(code, fish);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Fish> getFishMap() {
        return fishMap;
    }
}
