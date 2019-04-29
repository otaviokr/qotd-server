package dev.otaviokr.qotd.server.connection;

import dev.otaviokr.qotd.server.exception.DatabaseStorageException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseStorage implements IfQuoteStorage {

    private Connection connection = null;
    private Random random = null;

    // private static String connectionString = "jdbc:hsqldb:<protocol identifier>:<path to database>";
    //private static String connectionString = "jdbc:hsqldb:file:sampledb";
    //private static String connectionString = "jdbc:hsqldb:file:/temp/db/sampledb";
    //private static String connectionString = "jdbc:hsqldb:mem:sampledb";
    //private static String connectionString = "jdbc:hsqldb:res:org.jcg.sampledb";

    public DatabaseStorage(String username, String password, String connectionString) throws DatabaseStorageException {
        random = new Random(System.currentTimeMillis());

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = username.isEmpty() ?
                    DriverManager.getConnection(connectionString) :
                    DriverManager.getConnection(connectionString, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseStorageException("Error while creating database connection...", e);
        }
    }

    @Override
    public boolean isConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getAllQuotes() throws DatabaseStorageException {
        List<String> result = new ArrayList<>();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT QUOTE from QUOTES");
            while(rs.next()) {
                result.add(rs.getString("QUOTE"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseStorageException("Error while retrieving quotes from database...", e);
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new DatabaseStorageException("Error while closing statement from database...", e);
            }
        }
        return result;
    }

    @Override
    public String getRandomQuote() throws DatabaseStorageException {
        int index = -1;
        String result = "";
        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) as total from QUOTES");
            rs.next();
            index = rs.getInt("total");
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseStorageException("Error while retrieving total of quotes from database...", e);
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new DatabaseStorageException("Error while closing statement from database...", e);
            }
        }

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("SELECT quote FROM quotes WHERE ID = ?");
            pstmt.setInt(1, index);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = rs.getString("quote");
            rs.close();
        } catch (SQLException e) {
            throw new DatabaseStorageException("Error while retrieving quote from database...", e);
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new DatabaseStorageException("Error while closing statement from database...", e);
            }
        }

        return result;
    }

    @Override
    public void insertQuote(String quote) throws DatabaseStorageException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO quotes(quote) VALUES(?)");
            pstmt.setString(1, quote);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseStorageException("Error while insert quote into database...", e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw new DatabaseStorageException("Error while closing prepared statement from database...", e);
            }
        }
    }

    @Override
    public void close() throws DatabaseStorageException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DatabaseStorageException("Error while closing database connection...", e);
        }
    }
}
