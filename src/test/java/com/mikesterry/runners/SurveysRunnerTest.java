package com.mikesterry.runners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.connectors.Website;
import com.mikesterry.objects.County;
import com.mikesterry.objects.Lake;
import com.mikesterry.objects.Survey;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

public class SurveysRunnerTest {
    private Website website;
    private String mockedWebsiteContents;
    private SurveysRunner surveysRunner;
    private Lake lake;

    @Before
    public void setup() throws Exception {
        mockedWebsiteContents = loadResourceTestFileAsString("TestLakeSurvey.json");
        website = mock(Website.class);
        County county = new County(18, "Crow Wing");
        List<String> envasiveSpeciesList = new ArrayList<>();
        lake = new Lake("18024300","Lower Mission",envasiveSpeciesList,"Merrifield", county);
        List<Survey> surveyList = new ArrayList<>();
        surveysRunner = new SurveysRunner(lake, surveyList);
        lenient().when(website.getUrlContents()).thenReturn(mockedWebsiteContents);
    }

    @Test
    public void getSurveyList() {
        surveysRunner.run();
        Assert.assertTrue(surveysRunner.getSurveyList().size() > 0);
    }

    @Test
    public void getSurveyList_ValidateIsCloned() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode lengths = objectMapper.createObjectNode();
        surveysRunner.run();
        List<Survey> surveyList = surveysRunner.getSurveyList();
        int initialSize = surveyList.size();
        surveyList.add(new Survey("2020-02-05", "SurveyType", lengths, lake));
        Assert.assertEquals(initialSize, surveysRunner.getSurveyList().size());
    }

    // write some tests to vaidate we're stipping double quotes

    private String loadResourceTestFileAsString(String filename) throws IOException {
        return new String(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)).readAllBytes());
    }
}