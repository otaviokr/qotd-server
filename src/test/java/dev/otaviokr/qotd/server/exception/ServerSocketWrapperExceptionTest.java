package dev.otaviokr.qotd.server.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServerSocketWrapperExceptionTest {
    @Test
    public void testMessageAndThrowable() {
        String errorMsg = "This is a test error message";
        String causeMsg = "This is the cause error message";

        Throwable exception = assertThrows(ServerSocketWrapperException.class, () -> {
            throw new ServerSocketWrapperException(errorMsg, new UnsupportedOperationException(causeMsg));
        });

        assertEquals(exception.getMessage(), errorMsg);
        assertEquals(exception.getCause().getMessage(), causeMsg);
    }
}
