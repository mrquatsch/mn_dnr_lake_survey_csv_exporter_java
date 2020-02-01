package com.mikesterry.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.connectors.Website;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GetFishSpecies {
    private final String FISH_SPECIES_URL = "http://maps2.dnr.state.mn.us/lakefinder/lib/lakefinder.js";
    private Map<String, String> speciesMap;
    Website fishSpeciesLookup;

    public GetFishSpecies() {
        this.fishSpeciesLookup = new Website(FISH_SPECIES_URL);
        this.speciesMap = new HashMap<>();
    }

    public void getFishSpecies() throws Exception {
        String siteContents = fishSpeciesLookup.getUrlContents();
        String[] siteContentsArray = createArrayFromString(siteContents);
        String fishMapString = getFishSpeciesFromArray(siteContentsArray);
        JsonNode jsonNode = mapStringToJsonNode(fishMapString);
        createSpeciesMap(jsonNode);
    }

    private String[]  createArrayFromString(String siteContents) {
        return siteContents.split(";");
    }

    private String getFishSpeciesFromArray(String[] siteContentsArray) {
        for(String string : siteContentsArray) {
            if(string.contains("var fish_species = ")) {
                return string.split("var fish_species = ")[1];
            }
        }
        return null;
    }

    private JsonNode mapStringToJsonNode(String input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(input);
    }

    private void createSpeciesMap(JsonNode jsonNodeArray) {
        for (Iterator<JsonNode> it = jsonNodeArray.elements(); it.hasNext(); ) {
            JsonNode jsonNode = it.next();
            String abbreviatedName = jsonNode.get("code").toString();
            String commonName = jsonNode.get("common_name").toString();
            abbreviatedName = removeDoubleQuoatesFromString(abbreviatedName);
            commonName = removeDoubleQuoatesFromString(commonName);
            addSpecies(abbreviatedName, commonName);
        }
    }

    private String removeDoubleQuoatesFromString(String input) {
        return input.replace("\"", "");
    }

    private void addSpecies(String abbreviatedName, String commonName) {
        speciesMap.put(abbreviatedName, commonName);
    }

    public String getSpeciesByAbbreviatedName(String abbreviatedName) {
        return speciesMap.get(abbreviatedName);
    }

    public Map<String, String> getSpeciesMap() {
        return speciesMap;
    }
}