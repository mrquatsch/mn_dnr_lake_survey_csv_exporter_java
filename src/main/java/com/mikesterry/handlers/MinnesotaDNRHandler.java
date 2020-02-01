package com.mikesterry.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikesterry.excel.CreateXlsx;
import com.mikesterry.objects.County;
import com.mikesterry.objects.Lake;
import com.mikesterry.objects.Survey;
import com.mikesterry.runners.GetCounties;
import com.mikesterry.runners.GetFishSpecies;
import com.mikesterry.runners.GetLakes;
import com.mikesterry.runners.GetSurveys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MinnesotaDNRHandler {

    public MinnesotaDNRHandler() {
    }

    public void run() throws Exception {
        Map<String, String> fishSpeciesMap = getFishSpecies();
        List<County> countyList = getCounties();
        List<Lake> lakeList = getLakes(countyList);
        List<Survey> surveyList = getSurveys(lakeList);

        System.out.println("Gathered " + countyList.size() + " counties");
        System.out.println("... " + lakeList.size() + " lakes");
        System.out.println("... " + surveyList.size() + " surveys");
        System.out.println("... and mapped " + fishSpeciesMap.size() + " types of fish");
    }

    private Map<String, String> getFishSpecies() throws Exception {
        GetFishSpecies getFishSpecies = new GetFishSpecies();
        getFishSpecies.getFishSpecies();
        return getFishSpecies.getSpeciesMap();
    }

    private List<County> getCounties() {
        // Get our list of counties
        GetCounties counties = new GetCounties();
        counties.run();
        return counties.getCountyList();
    }

    private List<Lake>  getLakes(List<County> countyList) {
        System.out.println("Gathering lakes for " + countyList.size() + " counties");
        // Run pool against our list of counties to gather lakes
        List<Lake> lakeList = new ArrayList<>();
//        County county = countyList.get(2);
//        System.out.println("Gathering lakes for county: " + county.getName());
//        new GetLakes(county, lakeList).run();

        // Create thread pool
        int poolTimeout = 5;
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try {
            for(County county : countyList) {
                pool.submit(new GetLakes(county, lakeList));
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        try {
            pool.shutdown();
            pool.awaitTermination(poolTimeout, TimeUnit.MINUTES);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }

        return lakeList;
    }

    private List<Survey> getSurveys(List<Lake> lakeList) {
        System.out.println("Gathering surveys for " + lakeList.size() + " lakes");
        List<Survey> surveyList = new ArrayList<>();
        // Run pool against our list of lakes to gather surveys
        // Create thread pool
        int poolTimeout = 5;
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try {
            for(Lake lake : lakeList) {
                pool.submit(new GetSurveys(lake, surveyList));
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        // Shutdown our threads
        try {
            pool.shutdown();
            pool.awaitTermination(poolTimeout, TimeUnit.MINUTES);
        } catch(InterruptedException ie) {
            System.out.println(ie);
        }

        return surveyList;
    }

    private void mapFish(List<Survey> surveyList) {
            for(Survey survey : surveyList) {
                System.out.println("Survey: " + survey.getLengths().toString());

                for (Iterator<String> it = survey.getLengths().fieldNames(); it.hasNext(); ) {
                    String abbreviatedName = it.next();
                    System.out.println("Fish name: " + abbreviatedName);

//                    System.out.println("Field element: " + survey.getLengths().get(name));
                    JsonNode jsonNode = survey.getLengths().get(abbreviatedName).get("fishCount");
//                    System.out.println("json node: " + jsonNode);
                    ArrayList objectMapper = new ObjectMapper().convertValue(jsonNode, ArrayList.class);
                    for(Object countArray : objectMapper) {
                        ArrayList objectMapperNew = new ObjectMapper().convertValue(countArray, ArrayList.class);
                        System.out.println("Length: " + objectMapperNew.get(0).toString());
                        System.out.println("Count: " + objectMapperNew.get(1).toString());
                    }
                }
            }
    }

    private void createXlsxFile() {
        CreateXlsx xlsx = new CreateXlsx();
        xlsx.createHeader();
        xlsx.createRow();
        xlsx.createOutputFile();
    }
}