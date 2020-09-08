package rsb.bootstrap.reactor;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class OnErrorReturnTest {

    private final Flux<Integer> resultsInError = Flux.just(1, 2, 3).flatMap(it -> {
        if (it == 2) {
            return Flux.error(new IllegalArgumentException());
        } else {
            return Flux.just(it);
        }
    });

    @Test
    public void onErrorReturn() {

        var data = resultsInError.onErrorReturn(IllegalArgumentException.class, 4);

        StepVerifier.create(data).expectNext(1, 4).verifyComplete();
    }
}
