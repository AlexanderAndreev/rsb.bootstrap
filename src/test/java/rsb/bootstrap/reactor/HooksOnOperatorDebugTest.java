package rsb.bootstrap.reactor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;


import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Log4j2
public class HooksOnOperatorDebugTest {

    @Test
    public void onOperatorDebug() {

        Hooks.onOperatorDebug();
        var stackTrace = new AtomicReference<String>();

        var data = Flux.just("A", "B", "C", "D")
                .map(String::toLowerCase)
                .flatMapSequential(it -> {
                    if (it.equals("c")) {
                        return Mono.error(new IllegalArgumentException());
                    }
                    return Mono.just(it);
                })
                .checkpoint()
                .delayElements(Duration.ofMillis(1));

        StepVerifier.create(data).expectNext("a", "b").expectErrorMatches(e -> {
            stackTrace.set(stackTraceToString(e));
            return e instanceof IllegalArgumentException;
        }).verify();

        log.info(stackTrace);
    }

    private String stackTraceToString(Throwable t) {
        try (var sw = new StringWriter(); var pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
            return pw.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
