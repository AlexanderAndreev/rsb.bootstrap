package rsb.bootstrap.reactor;

import java.time.Duration;

import org.junit.jupiter.api.Test;


import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FlatMapTest {

    @AllArgsConstructor
    private static class Pair {
        private int id;
        private long delay;
    }

    @Test
    public void flatMap() {

        var data = Flux.just(
                new Pair(1, 300),
                new Pair(2, 200),
                new Pair(3, 100)
        ).flatMap(it -> Flux.just(it.id).delayElements(Duration.ofMillis(it.delay)));

        StepVerifier.create(data).expectNext(3).expectNext(2).expectNext(1).verifyComplete();
    }
}
