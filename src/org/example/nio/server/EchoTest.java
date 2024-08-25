package org.example.nio.server;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EchoTest {

    Process server;
    NIOClient client;

    @Before
    public void setup() throws IOException, InterruptedException {
        System.out.println("Пуск");
        server = NIOServer.start();
        TimeUnit.SECONDS.sleep(1); // БЕЗ ЗАДЕРЖКИ НА МОЁМ ОЧЕНЬ ШУСТРОМ КОМПЕ НЕ РАБОТАЕТ
        client = NIOClient.start();
    }

    @Test
    public void givenServerClient__whenServerEchosMessage__thenCorrect() {
        String resp1 = client.sendMessage("halloween");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("POISON__PILL");
        String resp4 = client.sendMessage("POISON__PILL");
        String resp5 = client.sendMessage("hello");

        assertThat(resp1, CoreMatchers.containsString("halloween"));
        assertThat(resp2, CoreMatchers.containsString("world"));
        assertEquals("Not accepting client messages anymore", resp3);
        assertEquals("", resp4);
        assertEquals("", resp5);
//        assertEquals("hello", resp1);
//        assertEquals("world", resp2);
    }

    @After
    public void teardown() throws IOException {
        server.destroy();
        NIOClient.stop();
    }
}