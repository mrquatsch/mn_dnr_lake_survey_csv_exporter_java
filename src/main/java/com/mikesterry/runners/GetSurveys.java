package com.mikesterry.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.connectors.Website;
import com.mikesterry.objects.Fish;
import com.mikesterry.objects.Lake;
import com.mikesterry.objects.Survey;

import java.text.SimpleDateFormat;
import java.util.*;

public class GetSurveys implements Runnable {
    private String lakeInfoURL;
    private List<Survey> surveyList;
    private Lake lake;

    public GetSurveys(Lake lake, List<Survey> surveyList) {
        this.lake = lake;
        lakeInfoURL = ("http://maps2.dnr.state.mn.us/cgi-bin/lakefinder/detail.cgi?type=lake_survey&id=");
        this.surveyList = surveyList;
    }

    public void run() {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("Gathering surveys for lake: " + lake.getId() + ": " + lake.getName());
        Website lakeSurveyLookup = new Website(lakeInfoURL + lake.getId());
        System.out.println("Lake survey info url: " + lakeSurveyLookup.getUrl());

        try {
            String jsonString = lakeSurveyLookup.getUrlContents();
            JsonNode actualObj = mapper.readTree(jsonString);
            String status = actualObj.get("status").toString().replace("\"", "");
            if(status.equals("SUCCESS")) {
                JsonNode jsonLakeSurveyNodes = actualObj.get("result").get("surveys");

                long mostRecent = 0;
                int mostRecentIndex = 0;
                for (int i = 0; i < jsonLakeSurveyNodes.size(); i++) {
                    String surveyDate = jsonLakeSurveyNodes.get(i).get("surveyDate").toString().replace("\"", "");
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                    Date date = df.parse(surveyDate);
                    long epoch = date.getTime();
//                            System.out.println(epoch);
                    if(epoch > mostRecent) {
                        mostRecentIndex = i;
                        mostRecent = epoch;
                    }
//                            System.out.println("surveyDate: " + surveyDate);
                }
//                System.out.println("Most recent: " + mostRecent + " - Index: " + mostRecentIndex);

                JsonNode jsonLakeSurveyNode = jsonLakeSurveyNodes.get(mostRecentIndex);

                System.out.println("jsonLakeSurveyArray: " + jsonLakeSurveyNode);
                String surveyDate = jsonLakeSurveyNode.get("surveyDate").toString();
                String surveySubType = jsonLakeSurveyNode.get("surveySubType").toString();
                JsonNode lengths = jsonLakeSurveyNode.get("lengths");

                Survey survey = new Survey(surveyDate, surveySubType, lengths, lake);
                System.out.println("Survey Date: " + survey.getSurveyDate());
                surveyList.add(survey);
            }

            for(Survey survey : surveyList) {
                actualObj = mapper.readTree(survey.getLengths().toString());
                System.out.println("Survey: " + survey.getLengths().toString());
                for (Iterator<JsonNode> it = survey.getLengths().elements(); it.hasNext(); ) {
                    JsonNode jsonNode = it.next();
                    System.out.println("Bleh: " + jsonNode.toString());
                }
                for (Iterator<Map.Entry<String, JsonNode>> it = actualObj.fields(); it.hasNext(); ) {
                    System.out.println(it.toString());
                    Map.Entry<String, JsonNode> something = it.next();
                    String speciesName = something.getKey();
                    System.out.println("Species name: " + speciesName);
                    Fish fish = new Fish(speciesName);
                    JsonNode somethingElse = something.getValue().get( "fishCount" );
//                    System.out.println("FishCount: " + somethingElse);
                    ArrayList objectMapper = new ObjectMapper().convertValue(somethingElse, ArrayList.class);
                    for(Object countArray : objectMapper) {
//                        System.out.println("countArray: " + countArray.toString());
                        ArrayList objectMapperNew = new ObjectMapper().convertValue(countArray, ArrayList.class);
                        System.out.println("Length: " + objectMapperNew.get(0).toString());
                        System.out.println("Count: " + objectMapperNew.get(1).toString());
                        int length = Integer.parseInt(objectMapperNew.get(0).toString());
                        int count = Integer.parseInt(objectMapperNew.get(1).toString());
                        fish.addLengthAndCount(length, count);
                    }
                    System.out.println("Fish: " + fish.toString());
                }
//                JsonNode fishLengthCounts = actualObj.get("fishCount");
//                System.out.println(survey.getLengths());
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
