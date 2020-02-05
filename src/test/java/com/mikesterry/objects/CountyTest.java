package com.mikesterry.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CountyTest {
    private County county;
    private String countyName = "Benton";
    private int countyId = 5;

    @Before
    public void setUp() {
        county = new County(countyId, countyName);
    }

    @Test
    public void getName() {
        Assert.assertEquals(countyName, county.getName());
    }

    @Test
    public void getId() {
        Assert.assertEquals(countyId, county.getId());
    }

    @Test
    public void testEquals() {
        County expectedCounty = new County(countyId, countyName);
        Assert.assertEquals(expectedCounty, county);
    }
}