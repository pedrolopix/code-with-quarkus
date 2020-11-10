package org.acme.websockets;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

@ServerEndpoint("/chat/{username}")
@ApplicationScoped
@Slf4j
public class ChatSocketResource {

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessions.put(username, session);
        log.debug("Session open from user {}", username);
    }

    @OnClose
    public void onClose(Session session, final CloseReason reason, @PathParam("username") String username) {
        sessions.remove(username);
        broadcast("User " + username + " left");
        log.debug("Session closed from user {}. Reason {} - {}", username, reason.getCloseCode(), reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
        log.error("onError", throwable);
        broadcast("User " + username + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        if (message.equalsIgnoreCase("_ready_")) {
            broadcast("User " + username + " joined");
        } else {
            broadcast(">> " + username + ": " + message);
        }
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    log.trace("Unable to send message: " + result.getException());
                }
            });
        });
    }

    public long getActiveConnections() {
        return sessions.size();
    }
}