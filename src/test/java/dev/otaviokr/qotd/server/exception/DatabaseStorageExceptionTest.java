package dev.otaviokr.qotd.server.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseStorageExceptionTest {

    @Test
    public void testMessageAndThrowable() {
        String errorMsg = "This is a test error message";
        String causeMsg = "This is the cause error message";

        Throwable exception = assertThrows(DatabaseStorageException.class, () -> {
            throw new DatabaseStorageException(errorMsg, new UnsupportedOperationException(causeMsg));
        });

        assertEquals(exception.getMessage(), errorMsg);
        assertEquals(exception.getCause().getMessage(), causeMsg);
    }
}
