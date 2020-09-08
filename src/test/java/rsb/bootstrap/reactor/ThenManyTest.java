package rsb.bootstrap.reactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ThenManyTest {

    @Test
    public void thanMany() {
        var letters = new AtomicInteger();
        var numbers = new AtomicInteger();
        var lettersPublisher = Flux.just("a", "b", "c").doOnNext(it -> letters.incrementAndGet());
        var numbersPublisher = Flux.just(1, 2, 3).doOnNext(it -> numbers.incrementAndGet());
        var thisBeforeThat = lettersPublisher.thenMany(numbersPublisher);

        StepVerifier.create(thisBeforeThat).expectNext(1).expectNext(2).expectNext(3).verifyComplete();
        assertEquals(letters.get(), 3);
        assertEquals(numbers.get(), 3);
    }
}
