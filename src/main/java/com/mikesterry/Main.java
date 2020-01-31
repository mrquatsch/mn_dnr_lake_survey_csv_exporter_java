package com.mikesterry;

import com.mikesterry.objects.County;
import com.mikesterry.objects.Lake;
import com.mikesterry.objects.Survey;
import com.mikesterry.runners.GetCounties;
import com.mikesterry.runners.GetLakes;
import com.mikesterry.runners.GetSurveys;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
//	    Website fishIDSite = new Website("http://maps2.dnr.state.mn.us/lakefinder/lib/lakefinder.js");

        // Get our list of counties
	    GetCounties counties = new GetCounties();
	    counties.run();
        counties.getCountyList();
        List<County> countyList = counties.getCountyList();

        System.out.println("Gathering lakes for " + countyList.size() + " counties");

        // Create thread pool
        int poolTimeout = 5;

        // Run pool against our list of counties to gather lakes
        List<Lake> lakeList = new ArrayList<>();
        County county = countyList.get(2);
        System.out.println("Gathering lakes for county: " + county.getName());
        new GetLakes(county, lakeList).run();


        ExecutorService pool = Executors.newFixedThreadPool(50);
//        try {
//            for(County county : countyList) {
//                pool.submit(new GetLakes(county, lakeList));
//            }
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//
//        try {
//            pool.shutdown();
//            pool.awaitTermination(poolTimeout, TimeUnit.MINUTES);
//        } catch (InterruptedException ie) {
//            System.out.println(ie);
//        }
//
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
//
//        CreateXlsx xlsx = new CreateXlsx();
//        xlsx.createHeader();
//        xlsx.createRow();
//        xlsx.createOutputFile();

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
