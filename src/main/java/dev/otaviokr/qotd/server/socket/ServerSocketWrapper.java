package dev.otaviokr.qotd.server.socket;

import dev.otaviokr.qotd.server.exception.QotdServerException;
import dev.otaviokr.qotd.server.exception.ServerSocketWrapperException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWrapper implements IfSocketWrapper {
    private int port;
    private ServerSocket serverSocket = null;
    private Socket socket = null;

    public ServerSocketWrapper(int port) throws QotdServerException {
        try {
            serverSocket = new ServerSocket(port);
            this.port = port;
        } catch (IOException e) {
            throw new QotdServerException("Failed to bind to port " + port, e);
        }
    }

    @Override
    public void listen() throws ServerSocketWrapperException {
        try {
            this.socket = serverSocket.accept();
        } catch (IOException e) {
            throw new ServerSocketWrapperException("Error while accepting connection", e);
        }
    }

    @Override
    public void write(String text) throws ServerSocketWrapperException {
        DataOutputStream outToClient;
        try {
            outToClient = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            throw new ServerSocketWrapperException("Error while getting socket output stream", e);
        }

        String sanitized = text.replace("\n", "").replaceAll("\r", "").
                replaceAll("\t", "").replaceAll("  +", "").concat("\n");

        System.out.println("Sending: " + sanitized);
        try {
            outToClient.writeBytes(sanitized);
        } catch (IOException e) {
            throw new ServerSocketWrapperException("Error while sending message: " + sanitized, e);
        }
    }
}
