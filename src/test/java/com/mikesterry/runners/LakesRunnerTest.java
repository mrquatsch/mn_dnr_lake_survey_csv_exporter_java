package com.mikesterry.runners;

import com.mikesterry.connectors.Website;
import com.mikesterry.objects.County;
import com.mikesterry.objects.Fish;
import com.mikesterry.objects.Lake;
import com.mikesterry.sorters.SortLakesByLakeNameCountyName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

public class LakesRunnerTest {
    LakesRunner lakesRunner;
    String mockedWebsiteContents;
    List<Lake> expectedLakeList;
    County county;
    List<Lake> lakeList;

    @Before
    public void setup() throws Exception {
        county = new County(5, "Benton");
        lakeList = new ArrayList<>();
        expectedLakeList = getExpectedLakeList();
        lakesRunner = new LakesRunner(county, lakeList);
        mockedWebsiteContents = loadResourceTestFileAsString("TestLakeLookup.json");
        Website website = mock(Website.class);
        lenient().when(website.getUrlContents()).thenReturn(mockedWebsiteContents);
        lakesRunner.run();
    }

    @Test
    public void getLakeList() {
        expectedLakeList.sort(new SortLakesByLakeNameCountyName());
        List<Lake> lakeList = lakesRunner.getLakeList();
        lakeList.sort(new SortLakesByLakeNameCountyName());
        Assert.assertEquals(expectedLakeList.toString(), lakeList.toString());
    }

    @Test
    public void validateToString() {
        lakeList.sort(new SortLakesByLakeNameCountyName());
        Assert.assertEquals("05006700: Burton Marsh", lakeList.get(0).toString());
    }

    @Test
    public void validateSortByLakeNameAndCountyName() {
        lakeList.sort(new SortLakesByLakeNameCountyName());
        Assert.assertEquals("05002400: Unnamed", lakeList.get(10).toString());
    }

    @Test
    public void validateSortByLakeNameAndCountyName2() {
        lakeList.sort(new SortLakesByLakeNameCountyName());
        Assert.assertEquals("05000900: Pularskis", lakeList.get(5).toString());
    }

    @Test
    public void validateIdIsStringAndIsNotQuoteWrapped() {
        lakeList.sort(new SortLakesByLakeNameCountyName());
        Assert.assertEquals("05000900", lakeList.get(5).getId());
    }

    @Test
    public void validateNameIsNotQuoteWrapped() {
        lakeList.sort(new SortLakesByLakeNameCountyName());
        Assert.assertEquals("Pularskis", lakeList.get(5).getName());
    }

    @Test
    public void validateTotalFishCount() {
        List<String> envasiveSpeciesList = new ArrayList<>();
        Lake lake = new Lake("05006700","Burton Marsh",envasiveSpeciesList,"", county);
        lake.addFish(createFish("Bullhead", 12, 6));
        lake.addFish(createFish("Walleye", 6, 14));
        lake.addFish(createFish("Minnow", 45, 980));
        Assert.assertEquals(1000, lake.getTotalFishCount());
    }

    @Test
    public void validateFishCountGreaterThan() {
        List<String> envasiveSpeciesList = new ArrayList<>();
        Lake lake = new Lake("05006700","Burton Marsh",envasiveSpeciesList,"", county);
        lake.addFish(createFish("Bullhead", 12, 6));
        lake.addFish(createFish("Walleye", 6, 14));
        lake.addFish(createFish("Minnow", 45, 980));
        Assert.assertEquals(980, lake.getFishCountGreaterThan(40));
    }

    @Test
    public void validateFishCountWithinRange() {
        List<String> envasiveSpeciesList = new ArrayList<>();
        Lake lake = new Lake("05006700","Burton Marsh",envasiveSpeciesList,"", county);
        lake.addFish(createFish("Bullhead", 12, 6));
        lake.addFish(createFish("Walleye", 6, 14));
        lake.addFish(createFish("Minnow", 45, 980));
        Assert.assertEquals(20, lake.getFishCountWithinRange(1, 15));
    }

    @Test
    public void validateSurveyDate() {
        List<String> envasiveSpeciesList = new ArrayList<>();
        Lake lake = new Lake("05006700","Burton Marsh",envasiveSpeciesList,"", county);
        String dateString = "2020-02-05";
        lake.setMostRecentSurveyDate(dateString);
        Assert.assertEquals(dateString, lake.getMostRecentSurveyDate());
    }

    private String loadResourceTestFileAsString(String filename) throws IOException {
//        return new String(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)).readAllBytes());
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(filename).getPath())));
    }

    private Fish createFish(String name, int length, int count) {
        Fish fish = new Fish(name);
        fish.addLengthAndCount(length, count);
        return fish;
    }

    private List<Lake> getExpectedLakeList() {
        List<String> invasiveSpeciesList = new ArrayList<>();
        List<String> invasiveZebraMusselSpeciesList = new ArrayList<>();
        invasiveZebraMusselSpeciesList.add("zebra mussel");
        List<Lake> expectedLakeList = new ArrayList<>();
        expectedLakeList.add(new Lake("05006700","Burton Marsh",invasiveSpeciesList,"", county));
        expectedLakeList.add(new Lake("05000400","Donovan",invasiveSpeciesList,"Cable", county));
        expectedLakeList.add(new Lake("05000402","Donovan (main bay)",invasiveSpeciesList,"", county));
        expectedLakeList.add(new Lake("05001300","Little Rock",invasiveZebraMusselSpeciesList,"Rice", county));
        expectedLakeList.add(new Lake("05000700","Mayhew",invasiveSpeciesList,"Mayhew", county));
        expectedLakeList.add(new Lake("05000900","Pularskis",invasiveSpeciesList,"Mayhew", county));
        expectedLakeList.add(new Lake("05000200","Unnamed",invasiveSpeciesList,"Duelm", county));
        expectedLakeList.add(new Lake("05001100","Unnamed",invasiveSpeciesList,"", county));
        expectedLakeList.add(new Lake("05001200","Unnamed",invasiveZebraMusselSpeciesList,"Watab", county));
        expectedLakeList.add(new Lake("05002100","Unnamed",invasiveSpeciesList,"", county));
        expectedLakeList.add(new Lake("05002400","Unnamed",invasiveSpeciesList,"", county));
        expectedLakeList.add(new Lake("05002500","Unnamed",invasiveSpeciesList,"Saint Cloud", county));
        return expectedLakeList;
    }
}