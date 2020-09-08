package rsb.bootstrap.reactor;

import java.time.Duration;

import org.junit.jupiter.api.Test;


import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ConcatMapTest {

    @AllArgsConstructor
    private static class Pair {
        private int id;
        private long delay;
    }

    @Test
    public void concatMap() {

        var data = Flux.just(
                new ConcatMapTest.Pair(1, 300),
                new ConcatMapTest.Pair(2, 200),
                new ConcatMapTest.Pair(3, 100)
        ).concatMap(it -> Flux.just(it.id).delayElements(Duration.ofMillis(it.delay)));

        StepVerifier.create(data).expectNext(1, 2, 3).verifyComplete();
    }
}
