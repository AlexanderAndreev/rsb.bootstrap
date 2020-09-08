package rsb.bootstrap.reactor;

import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class TransformTest {

    @Test
    public void transform() {

        var finished = new AtomicBoolean();
        var letters = Flux.just("A", "B", "C")
                .transform(stringFlux -> stringFlux.doFinally(signalType -> finished.set(true)));

        StepVerifier.create(letters).expectNextCount(3).verifyComplete();

        assertTrue("is true", finished.get());
    }
}
