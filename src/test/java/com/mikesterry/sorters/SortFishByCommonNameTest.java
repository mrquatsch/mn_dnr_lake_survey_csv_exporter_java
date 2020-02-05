package com.mikesterry.sorters;

import com.mikesterry.objects.Fish;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SortFishByCommonNameTest {
    List<Fish> fishList;

    @Before
    public void setUp() {
        fishList = new ArrayList<>();
        fishList.add(new Fish("Minnow"));
        fishList.add(new Fish("Guppie"));
        fishList.add(new Fish("Walleye"));
        fishList.add(new Fish("Bullhead"));
    }

    @Test
    public void compare() {
        String expectedFishListString = "[Bullhead,{}, Guppie,{}, Minnow,{}, Walleye,{}]";
        fishList.sort(new SortFishByCommonName());
        Assert.assertEquals(expectedFishListString, fishList.toString());
    }
}