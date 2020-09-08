package rsb.bootstrap.reactor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;


import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Log4j2
public class ControlFlowTimeoutTest {

    @Test
    public void timeout() {

        var data = Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1)).timeout(Duration.ofMillis(500))
                .onErrorResume(this::given);

        StepVerifier.create(data).expectNext(0).verifyComplete();
    }

    private Flux<Integer> given(Throwable t) {

        assertTrue(t instanceof TimeoutException);
        return Flux.just(0);
    }
}
