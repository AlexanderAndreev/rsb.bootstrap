package rsb.bootstrap.reactor;

import org.junit.jupiter.api.Test;


import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ControlFlowZipTest {

    @Test
    public void zip() {

        var first = Flux.just(1, 2, 3);
        var second = Flux.just("A", "B", "C");

        var zipped = Flux.zip(first, second).map(objects -> objects.getT1() + ":" + objects.getT2());

        StepVerifier.create(zipped).expectNext("1:A", "2:B", "3:C").verifyComplete();
    }
}
