package dev.otaviokr.qotd.server.exception;

public class QotdServerException extends Exception {
    public QotdServerException(String message, Throwable e) {
        super(message, e);
    }

    public QotdServerException(String exception) { super(exception); }
}
