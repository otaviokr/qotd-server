package dev.otaviokr.qotd.server;

import dev.otaviokr.qotd.server.socket.ServerSocketWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServerMainTest {

    @InjectMocks
    private ServerMain serverMain;

    @Mock
    private ServerSocketWrapper serverSocketWrapper;

    @Test
    public void listen() {
        // TODO to do
    }

    @Test
    public void loopToServer() {
        // TODO to do
    }
}
