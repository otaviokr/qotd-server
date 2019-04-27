package dev.otaviokr.qotd.server;

public interface IQuoteFetcher {
    public String getQuoteOfTheDay();

    public String getQuoteOfTheDayVia(QuoteSources source);
}
