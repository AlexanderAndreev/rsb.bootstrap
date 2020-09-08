package rsb.bootstrap.reactor;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ControlFlowMergeTest {

    @Test
    public void merge() {

        var data = Flux.merge(Mono.just(1), Mono.just(2), Mono.just(3));

        StepVerifier.create(data).expectNext(1, 2, 3).verifyComplete();
    }
}
