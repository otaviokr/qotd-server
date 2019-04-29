package dev.otaviokr.qotd.server.connection;

import dev.otaviokr.qotd.server.exception.QotdServerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestDownloader implements IfDownloader {
    private String uri;

    public RestDownloader(String uri) {
        this.uri = uri;
    }

    @Override
    public String getDailyQuote() throws QotdServerException {
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String incomingQuote;
            String sendQuote = "";
            while ((incomingQuote = reader.readLine()) != null) {
                sendQuote = sendQuote.concat(incomingQuote + '\n');
            }

            return sendQuote;
        } catch (IOException e) {
            System.out.println("Error! " + e.getLocalizedMessage());
            throw new QotdServerException("Failed to get daily quote", e);
        }
    }
}
