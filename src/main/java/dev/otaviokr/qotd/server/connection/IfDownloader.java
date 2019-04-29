package dev.otaviokr.qotd.server.connection;

import dev.otaviokr.qotd.server.exception.QotdServerException;

public interface IfDownloader {
    public String getDailyQuote() throws QotdServerException;
}
