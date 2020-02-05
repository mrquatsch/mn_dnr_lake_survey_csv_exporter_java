package com.mikesterry.connectors;

import com.mikesterry.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by mike on 2019-05-20
 */
public class Website {
    private URL url;
    private final Logger LOGGER = Logger.getLogger(Website.class.getName());

    public Website(String urlString) {
        try {
            this.url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getUrlContents() throws Exception {
        HttpURLConnection httpURLConnection = createHttpUrlConnection();
        return getContents(httpURLConnection);
    }

    private HttpURLConnection createHttpUrlConnection() throws IOException {
        int retryCounter = Constants.WEBSITE_RETRY_ATTEMPTS;
        HttpURLConnection httpURLConnection = null;

        while(retryCounter >= 0) {
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod(Constants.API_GET);
                httpURLConnection.setRequestProperty(Constants.API_CONTENT_TYPE, Constants.API_APPLICATION_JSON);
                break;
            } catch (IOException e) {
                LOGGER.warning("Failed to create website connection. Retries remaining: " + retryCounter);
                retryCounter -= 1;
            }
        }

        return httpURLConnection;
    }

    private String getContents(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while((inputLine = in.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        in.close();

        return stringBuilder.toString();
    }
}