package com.mikesterry.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.objects.County;
import com.mikesterry.connectors.Website;
import com.mikesterry.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CountiesRunner {
    private List<County> countyList = new ArrayList<>();
    private final Logger LOGGER = Logger.getLogger(CountiesRunner.class.getName());

    public void run() {
        Website countyIDLookup = new Website(Constants.COUNTY_INFO_LOOKUP_URL);
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonString = countyIDLookup.getUrlContents();
            JsonNode actualObj = mapper.readTree(jsonString);
            JsonNode jsonCountyArray = actualObj.get("results");

            for(JsonNode jsonNodeCounty : jsonCountyArray) {
                County county = createCountyFromJsonNode(jsonNodeCounty);
                countyList.add(county);
            }
        } catch (Exception e ) {
            LOGGER.warning("Failed to pull county results");
            LOGGER.warning(e.getMessage());
        }
    }

    public List<County> getCountyList() {
        return this.countyList;
    }

    private String removeDoubleQuotesFromString(String input) {
        return input.replace("\"", "");
    }

    private County createCountyFromJsonNode(JsonNode county) {
        String countyName = String.valueOf(county.get("name"));
        countyName = removeDoubleQuotesFromString(countyName);
        String countyIDString = String.valueOf(county.get("id"));
        countyIDString = removeDoubleQuotesFromString(countyIDString);
        int countyId = Integer.parseInt(countyIDString);
        LOGGER.info("County ID int: " + countyId);
        LOGGER.info("Creating county object for " + countyName);
        return new County(countyId, countyName);
    }

    public County getCountyByName(String countyName) {
        for(County county : countyList) {
            if(county.getName().equalsIgnoreCase(countyName)) {
                return county;
            }
        }
        return null;
    }

    public County getCountyById(int id) {
        for(County county : countyList) {
            if(county.getId() == id) {
                return county;
            }
        }
        return null;
    }
}
