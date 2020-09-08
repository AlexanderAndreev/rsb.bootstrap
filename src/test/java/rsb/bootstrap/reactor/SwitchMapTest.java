package rsb.bootstrap.reactor;

import java.time.Duration;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class SwitchMapTest {

    @Test
    public void switchMap() {

        var source = Flux.just("re", "rea", "reac", "react", "reactive")
                .delayElements(Duration.ofMillis(100))
                .switchMap(it -> Flux.just(it + " -> reactive").delayElements(Duration.ofMillis(500)));

        StepVerifier.create(source).expectNext("reactive -> reactive").verifyComplete();
    }
}
