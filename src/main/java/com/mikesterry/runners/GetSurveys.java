package com.mikesterry.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.connectors.Website;
import com.mikesterry.objects.Lake;
import com.mikesterry.objects.Survey;
import com.mikesterry.util.Constants;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class GetSurveys implements Runnable {
    private List<Survey> surveyList;
    private Lake lake;
    private ObjectMapper mapper;
    private final java.util.logging.Logger LOGGER = Logger.getLogger(GetSurveys.class.getName());

    public GetSurveys(Lake lake, List<Survey> surveyList) {
        this.lake = lake;
        this.surveyList = surveyList;
        this.mapper = new ObjectMapper();
    }

    public void run() {
        LOGGER.fine("Gathering surveys for lake: " + lake.getId() + ": " + lake.getName());
        Website lakeSurveyLookup = new Website(Constants.SURVEY_INFO_LOOKUP_URL + lake.getId());

        try {
            String jsonString = lakeSurveyLookup.getUrlContents();
            JsonNode jsonNode = getJsonTreeFromString(jsonString);
            String status = getResultsStatus(jsonNode);

            if(status.equals("SUCCESS")) {
                JsonNode jsonLakeSurveyNodes = jsonNode.get("result").get("surveys");
                JsonNode jsonLakeSurveyNode = getMostRecentSurveyFromNodes(jsonLakeSurveyNodes);
                addToSurveyList(jsonLakeSurveyNode);
            } else {
                // should return a catchable "survey not found" exception
                LOGGER.warning("No survey's found for lake " + lake);
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

    private JsonNode getJsonTreeFromString(String jsonString) throws IOException {
        return mapper.readTree(jsonString);
    }

    private String getResultsStatus(JsonNode jsonNode) {
        String status = jsonNode.get("status").toString();
        return removeDoubleQuotesFromString(status);
    }

    private JsonNode getMostRecentSurveyFromNodes(JsonNode jsonLakeSurveyNodes) throws ParseException {
        long mostRecent = 0;
        int mostRecentIndex = 0;
        for (int i = 0; i < jsonLakeSurveyNodes.size(); i++) {
            // Only capture surveys that have something set in the length section
            JsonNode lengths = jsonLakeSurveyNodes.get(i).get("lengths");
            if(lengths.size() > 0) {
                String surveyDate = jsonLakeSurveyNodes.get(i).get("surveyDate").toString();
                surveyDate = removeDoubleQuotesFromString(surveyDate);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                Date date = df.parse(surveyDate);
                long epoch = date.getTime();
                if (epoch > mostRecent) {
                    mostRecentIndex = i;
                    mostRecent = epoch;
                }
            }
        }

        return jsonLakeSurveyNodes.get(mostRecentIndex);
    }

    private void addToSurveyList(JsonNode jsonLakeSurveyNode) {
        String surveyDate = jsonLakeSurveyNode.get("surveyDate").toString();
        surveyDate = removeDoubleQuotesFromString(surveyDate);
        String surveySubType = jsonLakeSurveyNode.get("surveySubType").toString();
        JsonNode lengths = jsonLakeSurveyNode.get("lengths");

        Survey survey = new Survey(surveyDate, surveySubType, lengths, lake);
        surveyList.add(survey);
        updateLakeSurveyDate(survey.getLake(), surveyDate);
    }
}
