package dev.otaviokr.qotd.server;

import dev.otaviokr.qotd.server.connection.DatabaseStorage;
import dev.otaviokr.qotd.server.connection.IfDownloader;
import dev.otaviokr.qotd.server.connection.IfQuoteStorage;
import dev.otaviokr.qotd.server.connection.RestDownloader;
import dev.otaviokr.qotd.server.exception.DatabaseStorageException;
import dev.otaviokr.qotd.server.exception.QotdServerException;
import dev.otaviokr.qotd.server.exception.ServerSocketWrapperException;
import dev.otaviokr.qotd.server.socket.IfSocketWrapper;
import dev.otaviokr.qotd.server.socket.ServerSocketWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class orchestrates the QOTD Server that provides quotes via the QOTD Protocol.
 */
public class ServerMain {

    private IfSocketWrapper socket = null;
    private IfQuoteStorage storage = null;
    private IfDownloader downloader = null;

    private boolean dailyQuoteDownloaded = false;

    private static final Logger log = LogManager.getLogger(ServerMain.class);

    public ServerMain() throws QotdServerException {
        ClassLoader classLoader = this.getClass().getClassLoader();

        InputStream inputFile = classLoader.getResourceAsStream("qotd-server-test.properties");
        if (inputFile == null) { inputFile = classLoader.getResourceAsStream("qotd-server.properties"); }
        if (inputFile == null) { inputFile = classLoader.getResourceAsStream("qotd-test.properties"); }
        if (inputFile == null) { inputFile = classLoader.getResourceAsStream("qotd.properties"); }

        if (null != inputFile) {
            try {
                Properties props = new Properties();
                props.load(inputFile);

                this.socket = new ServerSocketWrapper(Integer.parseInt(props.getProperty("server.port", "-1")));
                this.downloader = new RestDownloader(props.getProperty("server.rest_url", "UNDEFINED"));
                this.storage = new DatabaseStorage("", "", "jdbc:hsqldb:file:sampledb");
            } catch (IOException | DatabaseStorageException e) {
                throw new QotdServerException("Error while loading properties from file", e);
            }
        } else {
            String exception = "Unable to find qotd-client.properties in classpath!";
            System.out.println(exception);
            throw new QotdServerException(exception);
        }
    }

    public void listen() throws QotdServerException, ServerSocketWrapperException {
        if (this.socket == null) {
            throw new QotdServerException("Socket is not yet defined.");
        }
        this.socket.listen();
    }

    public void loopToServer() throws ServerSocketWrapperException, QotdServerException {
        this.listen();

        String quote = "This is the default quote of the day";

        if (storage.isConnected()) {
            try {
                if (!dailyQuoteDownloaded) {
                    quote = downloader.getDailyQuote();

                    // Could not download query for some reason.
                    if (quote.isEmpty()) {
                        quote = storage.getRandomQuote();
                    }
                    dailyQuoteDownloaded = true;

                } else {
                    quote = storage.getRandomQuote();
                }
            } catch (DatabaseStorageException e) {
                throw new QotdServerException("Error while getting random quote from database", e);
            }
        }

        this.socket.write(quote);
    }

    public static void main(String[] args) throws QotdServerException, ServerSocketWrapperException {

        log.info("Starting QOTD server...");
        ServerMain server = new ServerMain();

        while (true) {
            server.loopToServer();
        }
    }
}
