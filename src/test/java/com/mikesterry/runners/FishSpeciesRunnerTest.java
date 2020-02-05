package com.mikesterry.runners;

import com.mikesterry.connectors.Website;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class FishSpeciesRunnerTest {
    private String mockedWebsiteContents;
    private String expectedResults;
    private Website website;
    private FishSpeciesRunner fishSpeciesRunner;

    @Before
    public void setup() throws Exception {
        mockedWebsiteContents = loadResourceTestFileAsString("TestFishSpeciesLookup.json");
        expectedResults = loadResourceTestFileAsString("ExpectedFishSpeciesLookupResults.txt");
        website = mock(Website.class);
        fishSpeciesRunner = new FishSpeciesRunner();
    }

    @Test
    public void getFishSpeciesShouldNotThrowAnException() throws Exception {
        lenient().when(website.getUrlContents()).thenReturn(mockedWebsiteContents);
        fishSpeciesRunner.getFishSpecies();
    }

    @Test
    public void getSpeciesByAbbreviatedName() throws Exception {
        lenient().when(website.getUrlContents()).thenReturn(mockedWebsiteContents);
        fishSpeciesRunner.getFishSpecies();
        String commonName = fishSpeciesRunner.getSpeciesByAbbreviatedName("CCF");
        Assert.assertEquals("channel catfish", commonName);
    }

    @Test
    public void getSpeciesMap() throws Exception {
        lenient().when(website.getUrlContents()).thenReturn(mockedWebsiteContents);
        fishSpeciesRunner.getFishSpecies();
        String results = fishSpeciesRunner.getSpeciesMap().toString();
        Assert.assertEquals(expectedResults, results);
    }

    private String loadResourceTestFileAsString(String filename) throws IOException {
//        return new String(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filename)).readAllBytes());
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource(filename).getPath())));
    }
}