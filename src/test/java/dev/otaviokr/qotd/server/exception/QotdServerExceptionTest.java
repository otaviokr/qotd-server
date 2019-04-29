package dev.otaviokr.qotd.server.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QotdServerExceptionTest {
    @Test
    public void testMessageAndThrowable() {
        String errorMsg = "This is a test error message";
        String causeMsg = "This is the cause error message";

        Throwable exception = assertThrows(QotdServerException.class, () -> {
            throw new QotdServerException(errorMsg, new UnsupportedOperationException(causeMsg));
        });

        assertEquals(exception.getMessage(), errorMsg);
        assertEquals(exception.getCause().getMessage(), causeMsg);
    }

    @Test
    public void testMessageOnly() {
        String errorMsg = "This is a test error message";

        Throwable exception = assertThrows(QotdServerException.class, () -> {
            throw new QotdServerException(errorMsg);
        });

        assertEquals(exception.getMessage(), errorMsg);
    }

}
