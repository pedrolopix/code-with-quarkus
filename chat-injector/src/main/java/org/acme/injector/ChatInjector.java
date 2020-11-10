package org.acme.injector;

import com.github.javafaker.Faker;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class ChatInjector {

    public static final int TIMEOUT_MILISECONDS = 1000;
    private static final Faker faker = new Faker();
    private String userName;
    private String url;
    private int nMessages;

    public ChatInjector(final String url, final int nMessages) {
        this.userName = faker.name().firstName();
        this.url = url + "/" + userName;
        this.nMessages = nMessages;
    }

    public void injectUser() throws IOException, InterruptedException {

        final WebSocket webSocketClient = HttpClient.newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(URI.create(url),
                        new Listener() {
                        }).join();

        injectUser(webSocketClient);
    }

    private void injectUser(final WebSocket webSocketClient) {
        log.info("User {} start",  userName);
        sendMessages(webSocketClient);
        webSocketClient.sendClose(WebSocket.NORMAL_CLOSURE, "Normal closure").join();
    }

    @SneakyThrows
    private void sendMessages(final WebSocket webSocketClient) {
        for (int i = 0; i < nMessages; i++) {
            log.info("Send message from user {} - {}", userName, i);
            webSocketClient.sendText(faker.chuckNorris().fact(), true).join();
            Thread.sleep(200);
        }
    }

}
