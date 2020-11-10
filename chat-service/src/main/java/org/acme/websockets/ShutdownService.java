package org.acme.websockets;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Slf4j
public class ShutdownService {

    private ChatSocketResource chatSocketResource;
    private long waitForEnd;

    @Inject
    public ShutdownService(final ChatSocketResource chatSocketResource,
                           @ConfigProperty(name = "shutdown.waitforend", defaultValue = "1") final long waitForEnd) {
        this.chatSocketResource = chatSocketResource;
        this.waitForEnd = waitForEnd;
    }

    private void waitForCallsTermination() {
        long activeConnections = getActiveConnections();
        log.info("[shutdown] Connections active: {}", activeConnections);
        while (activeConnections > 0) {
            log.debug("[shutdown] Wait for connections to end. Active connections: {}", activeConnections);
            try {
                TimeUnit.SECONDS.sleep(waitForEnd);
            } catch (InterruptedException e) {
                log.warn("[shutdown] Wait for connections to end interrupted. Active connections: {}", activeConnections);
                return;
            }
            activeConnections = getActiveConnections();
        }
        log.info("[shutdown] All connections are ended");
    }

    private long getActiveConnections() {
        return chatSocketResource == null ? 0 : chatSocketResource.getActiveConnections();
    }

    void onStart(@Observes final StartupEvent ev) {
        log.debug("[startup] The application is starting...");
    }

    void onStop(@Observes final ShutdownEvent ev) {
        log.debug("[shutdown] Received a SIGTERM. The application is stopping...");
        waitForCallsTermination();
    }

}
