package com.mikesterry.runners;

import com.mikesterry.connectors.Website;
import com.mikesterry.objects.County;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

public class CountiesRunnerTest {
    private String mockedWebsiteContents;
    private String expectedResults;
    private CountiesRunner countiesRunner;

    @Before
    public void setup() throws Exception {
        countiesRunner = new CountiesRunner();
        mockedWebsiteContents = loadResourceTestFileAsString("TestCountyLookup.json");
        expectedResults = loadResourceTestFileAsString("ExpectedFishSpeciesLookupResults.txt");
        Website website = mock(Website.class);
        lenient().when(website.getUrlContents()).thenReturn(mockedWebsiteContents);
        countiesRunner.run();
    }

    @Test
    public void countyListContainsEightySevenResults() {
        Assert.assertEquals(countiesRunner.getCountyList().size(), 87);
    }

    @Test
    public void countyNameIsNotDoubleQuoteWrapped() {
        String countyName = "Dakota";
        County county = countiesRunner.getCountyByName(countyName);
        Assert.assertEquals(countyName, county.getName());
    }

    @Test
    public void countyIdIsNotDoubleQuoteWrappedAndIsAnInteger() {
        int countyId = 1;
        County county = countiesRunner.getCountyById(countyId);
        Assert.assertEquals(countyId, county.getId());
    }

    @Test
    public void countyIdReturnsNullIfDoesNotExist() {
        int countyId = 100;
        County county = countiesRunner.getCountyById(countyId);
        Assert.assertNull(county);
    }

    private String loadResourceTestFileAsString(String filename) throws IOException {
//        return new String(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)).readAllBytes());
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(filename).getPath())));
    }
}