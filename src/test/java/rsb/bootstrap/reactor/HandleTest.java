package rsb.bootstrap.reactor;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;


import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Log4j2
public class HandleTest {

    @Test
    public void handle() {

        StepVerifier.create(handle(5, 4)).expectNext(0, 1, 2, 3).expectError(IllegalArgumentException.class).verify();

        StepVerifier.create(handle(3,3)).expectNext(0, 1, 2).verifyComplete();
    }

    public Flux<Integer> handle(int max, int numberToError) {

        return Flux.range(0, max)
                .handle((it, sink) -> {
                    if (it == numberToError) {
                        sink.error(new IllegalArgumentException());
                    } else {
                        Stream.iterate(0, i -> i < numberToError, i -> i++)
                                .filter(value -> it == value)
                                .findAny().ifPresentOrElse(sink::next, sink::complete);
                    }
                });
    }
}
