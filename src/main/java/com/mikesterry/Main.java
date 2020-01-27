package com.mikesterry;

import com.mikesterry.objects.County;
import com.mikesterry.objects.Fish;
import com.mikesterry.objects.Lake;
import com.mikesterry.objects.Survey;
import com.mikesterry.runners.GetCounties;
import com.mikesterry.runners.GetFish;
import com.mikesterry.runners.GetLakes;
import com.mikesterry.runners.GetSurveys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        // Get our fish species map
        GetFish getFish = new GetFish();
        getFish.run();
        Map<String, Fish> fishSpeciesMap = getFish.getFishMap();

        // Get our list of counties
	    GetCounties counties = new GetCounties();
	    counties.run();
        List<County> countyList = counties.getCountyList();

        System.out.println("Gathering lakes for " + countyList.size() + " counties");

        // Create thread pool
        int poolTimeout = 5;

        // Run pool against our list of counties to gather lakes
        List<Lake> lakeList = new ArrayList<>();
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

        System.out.println("Gathering surveys for " + lakeList.size() + " lakes");

        // Run pool against our list of lakes to gather surveys
        List<Survey> surveyList = new ArrayList<>();
        pool = Executors.newFixedThreadPool(50);
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

        System.out.println("Gathered " + countyList.size() + " counties");
        System.out.println("... " + lakeList.size() + " lakes");
        System.out.println("... and " + surveyList.size() + " surveys");

//        CreateXlsx xlsx = new CreateXlsx();
//        xlsx.createHeader();
//        xlsx.createRow();
//        xlsx.createOutputFile();

        for(Survey survey : surveyList) {
            Lake lake = survey.getLake();
            County county = lake.getCounty();

            System.out.println(lake.getName() + "," + lake.getId() + "," +
                    county.getName() + "," + lake.getNearestTown() + "," +
                    survey.getSurveyDate() + "," + survey.getSurveySubType() + "," +
                    survey.getLengths());
        }

    }
//
//    public static Map<String, County> returnMap(String jsonInput) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Map<String, County> map = new HashMap<>();
//
//        try {
//            map = objectMapper.readValue(jsonInput, new TypeReference<Map<String, County>>(){});
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return map;
//    }
}
