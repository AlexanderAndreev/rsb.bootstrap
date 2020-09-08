package rsb.bootstrap.reactor;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;


import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Log4j2
public class ControlFlowRetryTest {

    @Test
    public void retry() {

        var error = new AtomicBoolean();
        var producer = Flux.create(fluxSink -> {
            if (!error.get()) {
                error.set(true);
                fluxSink.error(new IllegalArgumentException());
            } else {
                fluxSink.next("hello");
            }
            fluxSink.complete();
        });

        var retryOnError = producer.retry();

        StepVerifier.create(retryOnError).expectNext("hello").verifyComplete();
    }
}
