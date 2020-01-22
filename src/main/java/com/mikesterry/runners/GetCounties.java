package com.mikesterry.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.objects.County;
import com.mikesterry.connectors.Website;

import java.util.ArrayList;
import java.util.List;

public class GetCounties {
    private List<County> countyList = new ArrayList<>();

    public void run() {
        Website countyIDLookup = new Website("http://maps1.dnr.state.mn.us/cgi-bin/gazetteer2.cgi?type=county&_=1517509749935");

        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonString = countyIDLookup.getUrlContents();
            JsonNode actualObj = mapper.readTree(jsonString);
            JsonNode jsonCountyArray = actualObj.get("results");


            for(JsonNode county : jsonCountyArray) {
                String countyName = String.valueOf(county.get("name"));
                String countyID = String.valueOf(county.get("id")).replace("\"", "");
                System.out.println("County ID int: " + countyID);

                System.out.println("Creating county object for " + countyName);
                County newCounty = new County(countyID, countyName);
                countyList.add(newCounty);
//                break;
            }
        } catch (Exception e ) {
            System.out.println("Failed to pull county results");
            System.out.println(e.getMessage());
        }
    }

    public List<County> getCountyList() {
        return this.countyList;
    }
}
