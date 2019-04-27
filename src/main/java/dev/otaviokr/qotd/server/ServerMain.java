package dev.otaviokr.qotd.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class orchestrates the QOTD Server that provides quotes via the QOTD Protocol.
 */
public class ServerMain {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(17111);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                // Setup socket connection.
                Socket connectionSocket = serverSocket.accept();
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                IQuoteFetcher fetcher = new QuoteFetcherImpl();
                String sendQuote = fetcher.getQuoteOfTheDay();

                System.out.println("Sending: " + sendQuote);
                outToClient.writeBytes(sendQuote + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
