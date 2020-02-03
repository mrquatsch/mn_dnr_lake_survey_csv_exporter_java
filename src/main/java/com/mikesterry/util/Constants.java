package com.mikesterry.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String API_GET = "GET";
    public static final String API_CONTENT_TYPE = "Content-Type";
    public static final String API_APPLICATION_JSON = "application/json";

    public static final int WEBSITE_RETRY_ATTEMPTS = 3;
    public static final String COUNTY_INFO_LOOKUP_URL = "http://maps1.dnr.state.mn.us/cgi-bin/gazetteer2.cgi?type=county&_=1517509749935";
    public static final String LAKE_INFO_LOOKUP_URL = "http://maps2.dnr.state.mn.us/cgi-bin/lakefinder_json.cgi?context=desktop&name=&county=";
    public static final String SURVEY_INFO_LOOKUP_URL = "http://maps2.dnr.state.mn.us/cgi-bin/lakefinder/detail.cgi?type=lake_survey&id=";
    public static final String FISH_SPECIES_INFO_LOOKUP_URL = "http://maps2.dnr.state.mn.us/lakefinder/lib/lakefinder.js";

    public static final String SPREADSHEET_HEADER_LAKE_NAME  = "Lake Name";
    public static final String SPREADSHEET_HEADER_LAKE_ID  = "Lake ID";
    public static final String SPREADSHEET_HEADER_COUNTY_NAME  = "County Name";
    public static final String SPREADSHEET_HEADER_NEAREST_TOWN  = "Nearest Town";
    public static final String SPREADSHEET_HEADER_SURVEY_DATE  = "Survey Date";
    public static final String SPREADSHEET_HEADER_SPECIES  = "Species";
    public static final String SPREADSHEET_HEADER_TOTAL_COUNT  = "Total Count";
    public static final String SPREADSHEET_HEADER_ZERO_TO_FIVE  = "0-5 in";
    public static final String SPREADSHEET_HEADER_SIX_TO_SEVEN  = "6-7 in";
    public static final String SPREADSHEET_HEADER_EIGHT_TO_NINE  = "8-9 in";
    public static final String SPREADSHEET_HEADER_TEN_TO_ELEVEN  = "10-11 in";
    public static final String SPREADSHEET_HEADER_TWELVE_TO_FOURTEEN  = "12-14 in";
    public static final String SPREADSHEET_HEADER_FIFTEEN_TO_NINETEEN  = "15-19 in";
    public static final String SPREADSHEET_HEADER_TWENTY_TO_TWENTYFOUR  = "20-24 in";
    public static final String SPREADSHEET_HEADER_TWENTYFIVE_TO_TWENTYNINE  = "25-29 in";
    public static final String SPREADSHEET_HEADER_THIRTY_TO_THIRTYFOUR  = "30-34 in";
    public static final String SPREADSHEET_HEADER_THIRTYFIVE_TO_THIRTYNINE  = "35-39 in";
    public static final String SPREADSHEET_HEADER_FOURTY_TO_FOURTYFOUR  = "40-44 in";
    public static final String SPREADSHEET_HEADER_FOURTYFIVE_TO_FOURTYNINE  = "45-49 in";
    public static final String SPREADSHEET_HEADER_FIFTY_PLUS  = "50+ in";
    public static final List<String> SPREADSHEET_HEADERS = new ArrayList<>(Arrays.asList(
            SPREADSHEET_HEADER_LAKE_NAME, SPREADSHEET_HEADER_LAKE_ID, SPREADSHEET_HEADER_NEAREST_TOWN, SPREADSHEET_HEADER_COUNTY_NAME,
            SPREADSHEET_HEADER_SURVEY_DATE, SPREADSHEET_HEADER_SPECIES, SPREADSHEET_HEADER_TOTAL_COUNT, SPREADSHEET_HEADER_ZERO_TO_FIVE,
            SPREADSHEET_HEADER_SIX_TO_SEVEN, SPREADSHEET_HEADER_EIGHT_TO_NINE, SPREADSHEET_HEADER_TEN_TO_ELEVEN,
            SPREADSHEET_HEADER_TWELVE_TO_FOURTEEN, SPREADSHEET_HEADER_FIFTEEN_TO_NINETEEN, SPREADSHEET_HEADER_TWENTY_TO_TWENTYFOUR,
            SPREADSHEET_HEADER_TWENTYFIVE_TO_TWENTYNINE, SPREADSHEET_HEADER_THIRTY_TO_THIRTYFOUR, SPREADSHEET_HEADER_THIRTYFIVE_TO_THIRTYNINE,
            SPREADSHEET_HEADER_FOURTY_TO_FOURTYFOUR, SPREADSHEET_HEADER_FOURTYFIVE_TO_FOURTYNINE, SPREADSHEET_HEADER_FIFTY_PLUS));
}
