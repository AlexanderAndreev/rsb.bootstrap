package rsb.bootstrap.reactor;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class OnErrorResumeTest {

    private final Flux<Integer> resultsInError = Flux.just(1, 2, 3).flatMap(it -> {
        if (it == 2) {
            return Flux.error(new IllegalArgumentException());
        } else {
            return Flux.just(it);
        }
    });

    @Test
    public void onErrorResume() {

        var integerFlux = resultsInError.onErrorResume(IllegalArgumentException.class, e -> Flux.just(3, 2, 1));

        StepVerifier.create(integerFlux).expectNext(1, 3, 2, 1).verifyComplete();
    }
}
