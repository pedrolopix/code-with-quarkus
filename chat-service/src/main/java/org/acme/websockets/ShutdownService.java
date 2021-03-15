package org.acme.websockets;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import sun.misc.Signal;
import sun.misc.SignalHandler;

@ApplicationScoped
@Slf4j
public class ShutdownService implements SignalHandler {

    private ChatSocketResource chatSocketResource;
    private long waitForEnd;
    private final SignalHandler handler;

    @Inject
    public ShutdownService(final ChatSocketResource chatSocketResource,
        @ConfigProperty(name = "shutdown.waitforend", defaultValue = "1") final long waitForEnd) {
        this.chatSocketResource = chatSocketResource;
        this.waitForEnd = waitForEnd;
        handler = Signal.handle(new Signal("TERM"), this);
    }

    private void waitForConnectionsTermination() {
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
        log.debug("[shutdown] OnStop. The application is stopping...");
        waitForConnectionsTermination();
    }

    @Override
    public void handle(final Signal signal) {
        log.info("[shutdown] Detected signal {} {}", signal.getName(), signal.getNumber());
        waitForConnectionsTermination();
        handler.handle(signal);
    }


}
