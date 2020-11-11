# code-websockts-example project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## See all avaiable commands
```shell script
make
```

## Running the chat-service in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
make dev
```

## Running the chat-inject in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
make inject-dev
```

## Packaging the application

The application can be packaged using:
```shell script
make build
```

## Start the docker

The application can be packaged using:
```shell script
make start
```

## Show docker logs

The application can be packaged using:
```shell script
make logs
```

## To test the close gracefully
execute this in 3 separate terminal:
  1. `make build start logs`
  2. `make inject`
  3. `make stop-chat`

log example with GOING_AWAY problem :
```
chat-ervice    | 2020-11-10 23:32:55,557 INFO  [io.und.web.jsr] (main) UT026003: Adding annotated server endpoint class org.acme.websockets.ChatSocketResource for path /chat/{username}
chat-service    | 2020-11-10 23:32:56,533 DEBUG [org.acm.web.ShutdownService] (main) [startup] The application is starting...
chat-service    | 2020-11-10 23:32:56,819 INFO  [io.quarkus] (main) chat-service 1.0.0-SNAPSHOT on JVM (powered by Quarkus 1.9.2.Final) started in 2.317s. Listening on: http://0.0.0.0:8080
chat-service    | 2020-11-10 23:32:56,820 INFO  [io.quarkus] (main) Profile prod activated.
chat-service    | 2020-11-10 23:32:56,820 INFO  [io.quarkus] (main) Installed features: [cdi, resteasy, servlet, smallrye-health, undertow-websockets]
chat-service    | 2020-11-10 23:33:04,106 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-0) Session open from user Jim
chat-service    | 2020-11-10 23:33:04,112 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-3) Session open from user Moises
chat-service    | 2020-11-10 23:33:04,106 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-2) Session open from user Johnson
chat-service    | 2020-11-10 23:33:04,113 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-1) Session open from user Antwan
chat-service    | 2020-11-10 23:33:04,136 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-1) Session open from user Kizzie
chat-service    | 2020-11-10 23:33:15,774 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-1) Session closed from user Antwan. Reason GOING_AWAY -
chat-service    | 2020-11-10 23:33:15,786 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-1) Session closed from user Kizzie. Reason GOING_AWAY -
chat-service    | 2020-11-10 23:33:15,805 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Received a SIGTERM. The application is stopping...
chat-service    | 2020-11-10 23:33:15,805 INFO  [org.acm.web.ShutdownService] (main) [shutdown] Connections active: 3
chat-service    | 2020-11-10 23:33:15,810 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Wait for connections to end. Active connections: 3
chat-service    | 2020-11-10 23:33:16,814 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Wait for connections to end. Active connections: 3
chat-service    | 2020-11-10 23:33:17,815 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Wait for connections to end. Active connections: 3
chat-service    | 2020-11-10 23:33:18,816 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Wait for connections to end. Active connections: 3
chat-service    | 2020-11-10 23:33:19,817 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Wait for connections to end. Active connections: 3
chat-service    | 2020-11-10 23:33:20,818 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Wait for connections to end. Active connections: 3
chat-service    | 2020-11-10 23:33:21,819 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Wait for connections to end. Active connections: 3
chat-service    | 2020-11-10 23:33:22,820 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Wait for connections to end. Active connections: 3
chat-service    | 2020-11-10 23:33:23,823 DEBUG [org.acm.web.ShutdownService] (main) [shutdown] Wait for connections to end. Active connections: 3
chat-service    | 2020-11-10 23:33:24,476 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-2) Session closed from user Johnson. Reason NORMAL_CLOSURE - Normal closure
chat-service    | 2020-11-10 23:33:24,476 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-0) Session closed from user Jim. Reason NORMAL_CLOSURE - Normal closure
chat-service    | 2020-11-10 23:33:24,479 DEBUG [org.acm.web.ChatSocketResource] (vert.x-eventloop-thread-3) Session closed from user Moises. Reason NORMAL_CLOSURE - Normal closure
chat-service    | 2020-11-10 23:33:24,824 INFO  [org.acm.web.ShutdownService] (main) [shutdown] All connections are ended
chat-service    | 2020-11-10 23:33:24,868 INFO  [io.quarkus] (main) chat-service stopped in 9.173s
```




