package dev.otaviokr.qotd.server.socket;

import dev.otaviokr.qotd.server.exception.ServerSocketWrapperException;

public interface IfSocketWrapper {
    public void listen() throws ServerSocketWrapperException;
    public void write(String text) throws ServerSocketWrapperException;
}
