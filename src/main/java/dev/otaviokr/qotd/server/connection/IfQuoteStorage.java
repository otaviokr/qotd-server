package dev.otaviokr.qotd.server.connection;

import dev.otaviokr.qotd.server.exception.DatabaseStorageException;

import java.util.List;

public interface IfQuoteStorage {
    public boolean isConnected();
    public List<String> getAllQuotes() throws DatabaseStorageException;
    public String getRandomQuote() throws DatabaseStorageException;
    public void insertQuote(String quote) throws DatabaseStorageException;
    public void close() throws DatabaseStorageException;
}
