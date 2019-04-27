package dev.otaviokr.qotd.server;

import dev.otaviokr.qotd.server.exception.QOTDException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuoteFetcherImpl implements IQuoteFetcher {
    private boolean fetchedFromWeb;

    public QuoteFetcherImpl() {
        // TODO Check database first to check if quotes has not been fetched previously.
        fetchedFromWeb = false;
    }

    @Override
    public String getQuoteOfTheDay() {
        String quote = "";

        if(fetchedFromWeb) {
            // TODO Because it has already fetched from Web, go get it from the database.
            return quote;
        } else {
            try {
                quote = this.fetchQuoteOnWeb();
            } catch (QOTDException e) {
                e.printStackTrace();
            }
        }

        return quote;
    }

    @Override
    public String getQuoteOfTheDayVia(QuoteSources source) {
        return null;
    }

    private String fetchQuoteOnWeb() throws QOTDException {
        try {
            URL url = new URL("http://quotes.rest/qod");
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
            throw new QOTDException(e);
        }
    }
}
