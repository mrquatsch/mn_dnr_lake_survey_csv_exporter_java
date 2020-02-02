package com.mikesterry.sorters;

import com.mikesterry.objects.Lake;

import java.util.Comparator;

public class SortLakesByCountyNameAndLakeName implements Comparator<Lake> {
    @Override
    public int compare(Lake o1, Lake o2) {
        String o1CountyName = o1.getCounty().getName();
        String o2CountyName = o2.getCounty().getName();
        String o1LakeName = o1.getName();
        String o2LakeName = o2.getName();

        if(o1CountyName.equals(o2CountyName)) {
            if(o1LakeName.equals(o2LakeName)) {
                return 0;
            } else {
                return o1LakeName.compareTo(o2LakeName);
            }
        }
        return o1CountyName.compareTo(o2CountyName);
    }
}
