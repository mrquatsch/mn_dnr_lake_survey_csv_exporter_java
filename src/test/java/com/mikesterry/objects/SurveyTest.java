package com.mikesterry.objects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SurveyTest {
    Survey survey;

    @Before
    public void setUp() {
        County county = new County(5, "Benton");
        List<String> invasiveZebraMusselSpeciesList = new ArrayList<>();
        invasiveZebraMusselSpeciesList.add("zebra mussel");
        Lake lake = new Lake("05001300","Little Rock",invasiveZebraMusselSpeciesList,"Rice", county);
        String surveyDate = "2020-02-05";
        String surveySubType = "something";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode lengths = objectMapper.createObjectNode();
        survey = new Survey(surveyDate, surveySubType, lengths, lake);
    }

    @Test
    public void getSurveyDate() {
        Assert.assertEquals("2020-02-05", survey.getSurveyDate());
    }

    @Test
    public void getSurveySubType() {
        Assert.assertEquals("something", survey.getSurveySubType());
    }

    @Test
    public void getLake() {
        County county = new County(5, "Benton");
        List<String> invasiveZebraMusselSpeciesList = new ArrayList<>();
        invasiveZebraMusselSpeciesList.add("zebra mussel");
        Lake lake = new Lake("05001300","Little Rock",invasiveZebraMusselSpeciesList,"Rice", county);
        String surveyDate = "2020-02-05";
        String surveySubType = "something";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode lengths = objectMapper.createObjectNode();
        Survey expectedSurvey = new Survey(surveyDate, surveySubType, lengths, lake);
        Assert.assertEquals(lake, survey.getLake());
    }
}