package com.mikesterry.connectors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by mike on 2019-05-20
 */
public class Website {
    private String url;

    public Website(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlContents() throws Exception {
        URL url = new URL(this.url);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String inputLine;
        while((inputLine = in.readLine()) != null) {
            return inputLine;
        }
        in.close();

        return inputLine;
    }
}