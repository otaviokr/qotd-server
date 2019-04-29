package dev.otaviokr.qotd.server.exception;

public class DatabaseStorageException extends Throwable {
    public DatabaseStorageException(String message, Throwable e) {
        super(message, e);
    }
}
