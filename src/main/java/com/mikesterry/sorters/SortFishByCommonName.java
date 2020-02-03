package com.mikesterry.sorters;

import com.mikesterry.objects.Fish;

import java.util.Comparator;

public class SortFishByCommonName implements Comparator<Fish> {
    @Override
    public int compare(Fish o1, Fish o2) {
        String o1FishName = o1.getCommonName();
        String o2FishName = o2.getCommonName();

        return o1FishName.compareTo(o2FishName);
    }
}
