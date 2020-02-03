package com.mikesterry.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.connectors.Website;
import com.mikesterry.objects.Lake;
import com.mikesterry.objects.Survey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
//        System.out.println("Lake survey info url: " + lakeSurveyLookup.getUrl());

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

//                System.out.println("jsonLakeSurveyArray: " + jsonLakeSurveyNode);
                String surveyDate = jsonLakeSurveyNode.get("surveyDate").toString();
                surveyDate = removeDoubleQuotesFromString(surveyDate);
                String surveySubType = jsonLakeSurveyNode.get("surveySubType").toString();
                JsonNode lengths = jsonLakeSurveyNode.get("lengths");

                Survey survey = new Survey(surveyDate, surveySubType, lengths, lake);
//                System.out.println("Survey Date: " + survey.getSurveyDate());
                surveyList.add(survey);
                updateLakeSurveyDate(survey.getLake(), surveyDate);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateLakeSurveyDate(Lake lake, String date) {
        lake.setMostRecentSurveyDate(date);
    }

    private String removeDoubleQuotesFromString(String input) {
        return input.replace("\"", "");
    }

    public List<Survey> getSurveyList() {
        return surveyList;
    }
}
