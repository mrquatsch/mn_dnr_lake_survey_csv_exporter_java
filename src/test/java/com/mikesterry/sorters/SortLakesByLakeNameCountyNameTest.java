package com.mikesterry.sorters;

import com.mikesterry.objects.County;
import com.mikesterry.objects.Lake;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SortLakesByLakeNameCountyNameTest {
    private List<Lake> lakeList;

    @Before
    public void setUp() {
        lakeList = new ArrayList<>();
        List<String> envasiveSpeciesList = new ArrayList<>();
        lakeList.add(new Lake("1","Lower Cullen",envasiveSpeciesList,"Merrifield", new County(1, "Crow Wing")));
        lakeList.add(new Lake("2","Lower Cullen",envasiveSpeciesList,"Nisswa", new County(1, "Crow Wing")));
        lakeList.add(new Lake("3","Barrett",envasiveSpeciesList,"Barrett", new County(2, "Grant")));
        lakeList.add(new Lake("4","Snelling",envasiveSpeciesList,"Saint Paul", new County(3, "Hennepin")));
    }

    @Test
    public void compare() {
        String expectedLakeListString = "[3: Barrett, 1: Lower Cullen, 2: Lower Cullen, 4: Snelling]";
        lakeList.sort(new SortLakesByLakeNameCountyName());
        System.out.println(lakeList.toString());
        Assert.assertEquals(expectedLakeListString, lakeList.toString());
    }
}