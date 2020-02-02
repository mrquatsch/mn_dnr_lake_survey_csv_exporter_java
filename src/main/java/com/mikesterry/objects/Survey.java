package com.mikesterry.objects;

import com.fasterxml.jackson.databind.JsonNode;

public class Survey {
    private final String surveyDate;
    private final String surveySubType;
    private final JsonNode lengths;
    private Lake lake;

    public Survey(String surveyDate, String surveySubType, JsonNode lengths, Lake lake) {
        this.lake = lake;
        this.surveyDate = surveyDate;
        this.surveySubType = surveySubType;
        this.lengths = lengths;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public String getSurveySubType() {
        return surveySubType;
    }

    public JsonNode getLengths() {
        return lengths;
    }

    public Lake getLake() {
        return lake;
    }
}
