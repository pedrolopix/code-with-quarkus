package org.acme.injector;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@QuarkusMain
@Slf4j
@CommandLine.Command(name = "inject", mixinStandardHelpOptions = true)
public class InjectorMain implements Runnable, QuarkusApplication {

    @Inject
    CommandLine.IFactory factory;

    @CommandLine.Option(names = {"-u", "--url"}, description = "websocket endpoint", defaultValue = "ws://localhost:8080/chat")
    private String url;

    @CommandLine.Option(names = {"-m", "--messages"}, description = "Number of messages to send", defaultValue = "10")
    private int nMessages;

    @CommandLine.Option(names = {"-c", "--users"}, description = "Number of simultaneous users to make", defaultValue = "1")
    private int nCalls;

    public static void main(final String... args) {
        Quarkus.run(InjectorMain.class, args);
    }

    @Override
    public int run(final String... args) throws Exception {
        return new CommandLine(this, factory).execute(args);
    }

    private Thread createCall() {
        return new Thread(() -> {
            final ChatInjector injector = new ChatInjector(url, nMessages);
            try {
                injector.injectUser();
            } catch (IOException | InterruptedException e) {
                log.error("Error ", e);
            }
        });
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("Start inject to {}, {} calls and {} messages", url, nCalls, nMessages);


        final List<Thread> threads = IntStream.
            range(0, nCalls)
            .mapToObj(i -> createCall())
            .collect(Collectors.toList());

        for (Thread thread1 : threads) {
            Thread.sleep(100);
            thread1.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

    }

}
