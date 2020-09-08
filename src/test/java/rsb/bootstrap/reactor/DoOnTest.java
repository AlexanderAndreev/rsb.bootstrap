package rsb.bootstrap.reactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;


import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

@Log4j2
public class DoOnTest {

    @Test
    public void doOn() {

        var signal = new ArrayList<Signal<Integer>>();
        var nextValues = new ArrayList<Integer>();
        var subscriptions = new ArrayList<Subscription>();
        var exceptions = new ArrayList<Throwable>();
        var finallySignals = new ArrayList<SignalType>();

        var on = Flux.<Integer>create(fluxSink -> {
            fluxSink.next(1);
            fluxSink.next(2);
            fluxSink.next(3);
            fluxSink.error(new IllegalArgumentException());
            fluxSink.complete();
        }).doOnNext(nextValues::add)
                .doOnEach(signal::add)
                .doOnSubscribe(subscriptions::add)
                .doOnError(exceptions::add)
                .doFinally(finallySignals::add);

        StepVerifier.create(on).expectNext(1, 2, 3).expectError(IllegalArgumentException.class).verify();

        signal.forEach(log::info);
        assertEquals(4, signal.size());
        nextValues.forEach(log::info);
        assertEquals(3, nextValues.size());
        subscriptions.forEach(log::info);
        assertEquals(1, subscriptions.size());
        exceptions.forEach(log::info);
        assertEquals(1, exceptions.size());
        finallySignals.forEach(log::info);
        assertEquals(1, finallySignals.size());
    }
}
