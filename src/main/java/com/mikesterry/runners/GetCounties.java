package com.mikesterry.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.objects.County;
import com.mikesterry.connectors.Website;
import com.mikesterry.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GetCounties {
    private List<County> countyList = new ArrayList<>();
    private final Logger LOGGER = Logger.getLogger(GetCounties.class.getName());

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
        String countyID = String.valueOf(county.get("id"));
        countyID = removeDoubleQuotesFromString(countyID);
        LOGGER.info("County ID int: " + countyID);
        LOGGER.info("Creating county object for " + countyName);
        return new County(countyID, countyName);
    }
}
