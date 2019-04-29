package dev.otaviokr.qotd.server.exception;

import java.io.IOException;

public class ServerSocketWrapperException extends Throwable {
    public ServerSocketWrapperException(String message, Throwable e) {
        super(message, e);
    }
}
