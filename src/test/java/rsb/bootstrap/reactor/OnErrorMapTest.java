package rsb.bootstrap.reactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class OnErrorMapTest {

    private class GenericException extends Exception {

    }

    @Test
    public void onError() throws Exception {

        var counter = new AtomicInteger();
        var resultInError = Flux.error(new IllegalArgumentException());
        var errorHandlingStream = resultInError
                .onErrorMap(IllegalArgumentException.class, e -> new GenericException())
                .doOnError(GenericException.class, e -> counter.incrementAndGet());

        StepVerifier.create(errorHandlingStream).expectError().verify();
        assertEquals(1, counter.get());
    }
}
